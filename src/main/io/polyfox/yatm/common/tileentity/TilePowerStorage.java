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

import java.io.IOException;

import io.netty.buffer.ByteBuf;

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.core.common.tileentity.event.TileEventHandler;
import io.polyfox.yatm.api.power.IPowerStorageTile;
import io.polyfox.yatm.api.power.PowerStorage;
import io.polyfox.yatm.api.power.PowerThrottle;
import io.polyfox.yatm.system.PowerSystem;

import cofh.api.energy.IEnergyReceiver;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

abstract class TilePowerStorage extends YATMTileBase implements IPowerStorageTile, INBTItemSerializable //, IEnergyReceiver
{
	private PowerThrottle powerThrottle;
	private PowerStorage powerStorage;

	public TilePowerStorage()
	{
		super();
		this.powerStorage = createPowerStorage();
		this.powerThrottle = createPowerThrottle(powerStorage);
	}

	public PowerStorage getPowerStorage()
	{
		return powerStorage;
	}

	public PowerThrottle getPowerThrottle()
	{
		return powerThrottle;
	}

	protected PowerStorage createPowerStorage()
	{
		return new PowerStorage(4000);
	}

	public PowerThrottle createPowerThrottle(PowerStorage storage)
	{
		return new PowerThrottle(storage, 20, 20);
	}

	protected void onInternalPowerChanged()
	{
		markForUpdate();
	}

	/**
	 * IPowerStorage
	 */
	@Override
	public long getPowerStoredFrom(ForgeDirection from)
	{
		return powerStorage.getAmount();
	}

	/**
	 * IPowerStorage
	 */
	@Override
	public long getPowerCapacityFrom(ForgeDirection from)
	{
		return powerStorage.getCapacity();
	}

	public float getPowerStorageRate(ForgeDirection from)
	{
		final long max = getPowerCapacityFrom(from);
		if (max != 0) return (float)((double)getPowerStoredFrom(from) / (double)max);
		return 0.0f;
	}

	// @todo - FIXME RF
	/*@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return (int)PowerSystem.YW_RF.toTarget(powerStorage.getAmount());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return (int)PowerSystem.YW_RF.toTarget(powerStorage.getCapacity());
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulated)
	{
		final int result = (int)powerThrottle.receive(PowerSystem.YW_RF.toBase(maxReceive), simulated);
		if (!simulated && result != 0)
		{
			onInternalPowerChanged();
		}
		return result;
	}*/

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Power(ByteBuf stream) throws IOException
	{
		final long power = stream.readLong();
		powerStorage.setAmountUnsafe(power);
		return false;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Power(ByteBuf stream) throws IOException
	{
		final long power = powerStorage.getAmount();
		stream.writeLong(power);
		return false;
	}

	private void readPowerStorageFromNBT(NBTTagCompound nbt)
	{
		// Legacy
		if (nbt.hasKey("energy"))
		{
			final NBTTagCompound powerTag = nbt.getCompoundTag("energy");
			powerStorage.setAmountUnsafe(PowerSystem.YW_RF.toBase(powerTag.getInteger("Energy")));
		}
		else if (nbt.hasKey("power"))
		{
			final NBTTagCompound powerTag = nbt.getCompoundTag("power");
			powerStorage.readFromNBT(powerTag);
		}
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readPowerStorageFromNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_Power(NBTTagCompound nbt)
	{
		readPowerStorageFromNBT(nbt);
	}

	private void writePowerStorageToNBT(NBTTagCompound nbt)
	{
		final NBTTagCompound powerTag = new NBTTagCompound();
		powerStorage.writeToNBT(powerTag);
		nbt.setTag("power", powerTag);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writePowerStorageToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_Power(NBTTagCompound nbt)
	{
		writePowerStorageToNBT(nbt);
	}
}
