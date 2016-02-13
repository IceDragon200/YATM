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

import id2h.yatm.common.tileentity.feature.IEnergyGridSync;
import growthcraft.api.core.util.BlockFlags;

import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergyCell extends YATMEnergyProviderTile
{
	protected int lastMeta = -1;

	public TileEntityEnergyCell()
	{
		super();
		setEnergySyncPriority(100);
	}

	@Override
	public int getEnergySyncAmount(ForgeDirection _dir, IEnergyGridSync _other)
	{
		// Energy cells sync at twice their rate
		return energyStorage.getMaxExtract() * 2;
	}

	public int calculateEnergyMeta()
	{
		final int stored = getEnergyStored(ForgeDirection.UNKNOWN);
		final int max = getMaxEnergyStored(ForgeDirection.UNKNOWN);
		if (max <= 0) return 0;
		return stored * 8 / max;
	}

	@Override
	protected boolean updateBlockMeta()
	{
		final int newMeta = calculateEnergyMeta();
		if (lastMeta != newMeta)
		{
			lastMeta = newMeta;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lastMeta, BlockFlags.SYNC);
		}
		return true;
	}
}
