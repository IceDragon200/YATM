/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.polyfox.yatm.common.tileentity;

import growthcraft.core.common.tileentity.event.TileEventHandler;
import io.polyfox.yatm.api.core.nbt.NBTTagLongArray;
import io.polyfox.yatm.api.core.util.MathI64;
import io.polyfox.yatm.api.power.IPowerConsumer;
import io.polyfox.yatm.api.power.IPowerGridSync;
import io.polyfox.yatm.api.power.IPowerProducer;
import io.polyfox.yatm.api.power.IPowerStorageTile;
import io.polyfox.yatm.api.power.PowerSyncDirection;
import io.polyfox.yatm.system.PowerSystem;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TilePowerProviderBase extends TilePowered implements IPowerGridSync, IPowerProducer, IEnergyHandler
{
	protected int powerSyncLevel = 10;
	protected long[] powerDemand = new long[6];

	public long getMaxReceive()
	{
		return getPowerThrottle().getMaxReceive();
	}

	public long getMaxExtract()
	{
		return getPowerThrottle().getMaxConsume();
	}

	@Override
	public void demandPowerFrom(ForgeDirection from, long amount)
	{
		if (from != null && from != ForgeDirection.UNKNOWN)
		{
			powerDemand[from.ordinal()] += amount;
		}
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulated)
	{
		final long result = getPowerThrottle().consume(PowerSystem.YW_RF.toBase(maxExtract), simulated);
		if (!simulated && result != 0)
		{
			onInternalPowerChanged();
		}
		return (int)result;
	}

	protected void setPowerSyncLevel(int value)
	{
		this.powerSyncLevel = value;
	}

	@Override
	public int getPowerSyncLevelFrom(ForgeDirection dir)
	{
		return powerSyncLevel;
	}

	public boolean checkEnergySyncLevels(ForgeDirection dir, IPowerGridSync other)
	{
		if (other instanceof IPowerStorageTile)
		{
			if (((IPowerStorageTile)other).getPowerStoredFrom(dir.getOpposite()) < getPowerStoredFrom(dir))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public PowerSyncDirection getPowerSyncDirectionFor(ForgeDirection dir, IPowerGridSync other)
	{
		final int otherLevel = other.getPowerSyncLevelFrom(dir.getOpposite());
		if (otherLevel > getPowerSyncLevelFrom(dir)) return PowerSyncDirection.RECEIVE;
		return checkEnergySyncLevels(dir, other) ? PowerSyncDirection.SEND : PowerSyncDirection.NONE;
	}

	public long getPowerSyncAmount(ForgeDirection _dir, IPowerGridSync _other)
	{
		return getPowerThrottle().getMaxConsume();
	}

	@Override
	public long syncPowerFrom(ForgeDirection dir, long value)
	{
		final long result = getPowerStorage().receive(value, false);
		if (result != 0)
		{
			onInternalPowerChanged();
		}
		return result;
	}

	protected void syncEnergyWith(ForgeDirection dir, IPowerGridSync target)
	{
		switch (getPowerSyncDirectionFor(dir, target))
		{
			case NONE:
			case RECEIVE:
				//YATM.getLogger().info("Not syncing with target=%s target from tile=%s x=%d y=%d z=%d", target, this, xCoord, yCoord, zCoord);
				break;
			case SEND:
				final long supp = getPowerStorage().consume(getPowerSyncAmount(dir, target), true);
				final long diff = target.syncPowerFrom(dir.getOpposite(), supp);
				if (diff > 0)
				{
					if (getPowerStorage().consume(diff, false) != 0)
					{
						onInternalPowerChanged();
					}
				}
				break;
			default:
		}
	}

	protected void handlePowerDemand(IPowerConsumer consumer, ForgeDirection dir)
	{
		final int i = dir.ordinal();
		if (powerDemand[i] > 0)
		{
			final long maxConsumable = getPowerStorage().consume(powerDemand[i], true);
			if (maxConsumable > 0)
			{
				final long consumed = MathI64.clamp(consumer.receivePowerFrom(dir.getOpposite(), maxConsumable, false), 0, maxConsumable);
				if (consumed > 0)
				{
					getPowerStorage().consume(consumed, false);
					onInternalPowerChanged();
				}
			}
		}
	}

	protected void updatePowerProvider()
	{
		final TileEntity[] tc = getTileCache();
		for (int i = 0; i < tc.length; ++i)
		{
			final TileEntity te = tc[i];
			if (te != null)
			{
				final ForgeDirection dir = ForgeDirection.getOrientation(i);

				if (te instanceof IPowerGridSync)
				{
					final IPowerGridSync cell = (IPowerGridSync)te;
					syncEnergyWith(dir, cell);
				}
				else if (te instanceof IPowerConsumer)
				{
					final IPowerConsumer consumer = (IPowerConsumer)te;
					handlePowerDemand(consumer, dir);
				}
				else if (te instanceof IEnergyReceiver)
				{
					final IEnergyReceiver receiver = (IEnergyReceiver)te;
					final int feed = extractEnergy(dir, (int)PowerSystem.YW_RF.toTarget(getPowerThrottle().getMaxConsume()), true);
					final int diff = receiver.receiveEnergy(dir.getOpposite(), feed, false);
					if (diff > 0)
					{
						if (extractEnergy(dir, diff, false) != 0)
						{
							onInternalPowerChanged();
						}
					}
				}
			}
			powerDemand[i] = 0;
		}
	}

	protected boolean updateBlockMeta()
	{
		return false;
	}

	@Override
	protected void onInternalPowerChanged()
	{
		super.onInternalPowerChanged();
		updateBlockMeta();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			updatePowerProvider();
		}
	}

	public void readPowerProviderFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("power_demand"))
		{
			final NBTBase tag = nbt.getTag("power_demand");
			if (tag instanceof NBTTagIntArray)
			{
				final NBTTagIntArray ia = (NBTTagIntArray)tag;
				final NBTTagLongArray la = new NBTTagLongArray(ia);
				la.loadI64Array(powerDemand);
			}
		}
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_PowerProvider(NBTTagCompound nbt)
	{
		readPowerProviderFromNBT(nbt);
	}

	public void writePowerProviderToNBT(NBTTagCompound nbt)
	{
		final NBTTagLongArray la = new NBTTagLongArray(powerDemand);
		nbt.setTag("power_demand", la.getIntArrayTag());
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_PowerProvider(NBTTagCompound nbt)
	{
		writePowerProviderToNBT(nbt);
	}
}
