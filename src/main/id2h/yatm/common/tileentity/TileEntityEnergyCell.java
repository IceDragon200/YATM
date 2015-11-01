/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package id2h.yatm.common.tileentity;

import id2h.yatm.common.tileentity.energy.YATMEnergyStorage;
import id2h.yatm.common.tileentity.feature.IEnergyGridSync;
import id2h.yatm.util.BlockFlags;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergyCell extends YATMPoweredTile implements IEnergyHandler, IEnergyGridSync
{
	protected boolean needCacheRebuild = true;
	protected boolean needUpdate = true;
	protected int lastMeta = -1;
	protected TileEntity[] tileCache = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];

	@Override
	protected abstract YATMEnergyStorage createEnergyStorage();

	public int getMaxReceive()
	{
		return energyStorage.getMaxReceive();
	}

	public int getMaxExtract()
	{
		return energyStorage.getMaxExtract();
	}

	private void rebuildTileCache()
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
		needCacheRebuild = true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	public int calculateEnergyMeta()
	{
		final int stored = getEnergyStored(ForgeDirection.UNKNOWN);
		final int max = getMaxEnergyStored(ForgeDirection.UNKNOWN);
		if (max <= 0) return 0;
		return stored * 8 / max;
	}

	@Override
	public boolean canEnergyGridSync(ForgeDirection dir)
	{
		return true;
	}

	private void updateEnergyCell()
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
					if (cell.canEnergyGridSync(dir.getOpposite()))
					{
						if (cell.getEnergyStored(dir.getOpposite()) < getEnergyStored(dir))
						{
							final int diff = cell.receiveEnergy(dir.getOpposite(), extractEnergy(dir, energyStorage.getMaxExtract() / 2, true), false);
							if (diff > 0)
							{
								extractEnergy(dir, diff, false);
							}
						}
					}
				}
				else if (te instanceof IEnergyReceiver)
				{
					final IEnergyReceiver receiver = (IEnergyReceiver)te;
					final int diff = receiver.receiveEnergy(dir.getOpposite(), extractEnergy(dir, energyStorage.getMaxExtract(), true), false);
					if (diff > 0)
					{
						extractEnergy(dir, diff, false);
					}
				}
			}
		}
	}

	private void updateBlockMeta()
	{
		final int newMeta = calculateEnergyMeta();
		if (lastMeta != newMeta)
		{
			lastMeta = newMeta;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lastMeta, BlockFlags.UPDATE_CLIENT);
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!worldObj.isRemote)
		{
			if (needCacheRebuild)
			{
				needCacheRebuild = false;
				rebuildTileCache();
			}

			if (needUpdate)
			{
				needUpdate = false;
				updateBlockMeta();
				this.markDirty();
			}

			updateEnergyCell();
			needUpdate = true;
		}
	}
}
