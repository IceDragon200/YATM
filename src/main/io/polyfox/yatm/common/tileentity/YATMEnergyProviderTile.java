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

import io.polyfox.yatm.common.tileentity.energy.YATMEnergyStorage;
import io.polyfox.yatm.common.tileentity.feature.IEnergyGridSync;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class YATMEnergyProviderTile extends YATMPoweredTile implements IEnergyHandler, IEnergyGridSync
{
	protected boolean needCacheRebuild = true;
	protected int energySyncPriority = 10;
	protected TileEntity[] tileCache = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];

	@Override
	protected abstract YATMEnergyStorage createEnergyStorage();

	public void markForCacheRefresh()
	{
		needCacheRebuild |= true;
	}

	public int getMaxReceive()
	{
		return energyStorage.getMaxReceive();
	}

	public int getMaxExtract()
	{
		return energyStorage.getMaxExtract();
	}

	protected void rebuildTileCache()
	{
		for (int i = 0; i < tileCache.length; ++i)
		{
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			final int fx = xCoord + dir.offsetX;
			final int fy = yCoord + dir.offsetY;
			final int fz = zCoord + dir.offsetZ;
			tileCache[i] = worldObj.getTileEntity(fx, fy, fz);
		}
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		markForCacheRefresh();
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulated)
	{
		final int result = energyStorage.extractEnergy(maxExtract, simulated);
		if (!simulated && result != 0)
		{
			markDirty();
		}
		return result;
	}

	protected void setEnergySyncPriority(int value)
	{
		this.energySyncPriority = value;
	}

	@Override
	public int getEnergySyncPriority(ForgeDirection dir)
	{
		return energySyncPriority;
	}

	public boolean checkEnergySyncLevels(ForgeDirection dir, IEnergyGridSync other)
	{
		if (other.getEnergyStored(dir.getOpposite()) < getEnergyStored(dir))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canEnergyGridSync(ForgeDirection dir, IEnergyGridSync other)
	{
		// tiles should only synchronize if the host has a higher or equal priority
		// than the client.
		// Therefore, lower priority clients will NEVER sync their energy with
		// higher priority clients
		final int clientPriority = other.getEnergySyncPriority(dir.getOpposite());
		if (clientPriority > getEnergySyncPriority(dir)) return false;
		return checkEnergySyncLevels(dir, other);
	}

	public int getEnergySyncAmount(ForgeDirection _dir, IEnergyGridSync _other)
	{
		return energyStorage.getMaxExtract();
	}

	@Override
	public int syncEnergy(ForgeDirection dir, int value)
	{
		final int result = energyStorage.modifyEnergyStoredDiff(value);
		if (result != 0)
		{
			markDirty();
		}
		return result;
	}

	protected void syncEnergyWith(ForgeDirection dir, IEnergyGridSync target)
	{
		if (canEnergyGridSync(dir, target))
		{
			final int supp = extractEnergy(dir, getEnergySyncAmount(dir, target), true);
			final int diff = target.syncEnergy(dir.getOpposite(), supp);
			if (diff > 0)
			{
				if (extractEnergy(dir, diff, false) != 0)
				{
					markDirty();
				}
			}
		}
	}

	protected void updateEnergyProvider()
	{
		for (int i = 0; i < tileCache.length; ++i)
		{
			final TileEntity te = tileCache[i];
			if (te != null)
			{
				final ForgeDirection dir = ForgeDirection.getOrientation(i);

				if (te instanceof IEnergyGridSync)
				{
					final IEnergyGridSync cell = (IEnergyGridSync)te;
					syncEnergyWith(dir, cell);
				}
				else if (te instanceof IEnergyReceiver)
				{
					final IEnergyReceiver receiver = (IEnergyReceiver)te;
					final int diff = receiver.receiveEnergy(dir.getOpposite(), extractEnergy(dir, energyStorage.getMaxExtract(), true), false);
					if (diff > 0)
					{
						if (extractEnergy(dir, diff, false) != 0)
						{
							markDirty();
						}
					}
				}
			}
		}
	}

	protected boolean updateBlockMeta()
	{
		return false;
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			if (needCacheRebuild)
			{
				needCacheRebuild = false;
				rebuildTileCache();
			}

			updateEnergyProvider();
		}

		super.updateEntity();
	}
}
