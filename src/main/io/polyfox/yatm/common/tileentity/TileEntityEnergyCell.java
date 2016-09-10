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
package io.polyfox.yatm.common.tileentity;

import io.polyfox.yatm.api.power.IPowerGridSync;
import growthcraft.api.core.util.BlockFlags;

import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergyCell extends TilePowerProviderBase
{
	protected int lastMeta = -1;

	public TileEntityEnergyCell()
	{
		super();
		setPowerSyncLevel(100);
	}

	@Override
	public long getPowerSyncAmount(ForgeDirection _dir, IPowerGridSync _other)
	{
		// Energy cells sync at twice their rate
		return powerThrottle.getMaxConsume() * 2;
	}

	public int calculateEnergyMeta()
	{
		final long stored = getPowerStoredFrom(ForgeDirection.UNKNOWN);
		final long max = getPowerCapacityFrom(ForgeDirection.UNKNOWN);
		if (max <= 0) return 0;
		return (int)(stored * 8 / max);
	}

	@Override
	protected boolean updateBlockMeta()
	{
		final int newMeta = calculateEnergyMeta();
		if (lastMeta != newMeta)
		{
			lastMeta = newMeta;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lastMeta, BlockFlags.SYNC);
			markDirty();
		}
		return true;
	}
}
