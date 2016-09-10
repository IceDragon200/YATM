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

import growthcraft.api.core.nbt.INBTItemSerializable;
import io.polyfox.yatm.api.power.IPowerConsumer;
import io.polyfox.yatm.common.tileentity.feature.ITileNeighbourAware;
import io.polyfox.yatm.util.TileUtils;
import io.polyfox.yatm.YATM;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TilePowered extends TilePowerStorage implements IPowerConsumer, INBTItemSerializable, ITileNeighbourAware
{
	protected boolean needCacheRebuild = true;
	protected TileEntity[] tileCache = TileUtils.instance().createTileCache();

	@Override
	public long receivePowerFrom(ForgeDirection from, long amount, boolean simulated)
	{
		return powerThrottle.receive(amount, simulated);
	}

	protected void rebuildTileCache()
	{
		TileUtils.instance().populateTileCache(tileCache, worldObj, xCoord, yCoord, zCoord);
		YATM.getLogger().info("Rebuilt Tile Cache for tile=%s x=%d y=%d z=%d", this, xCoord, yCoord, zCoord);
	}

	public void markForCacheRefresh()
	{
		this.needCacheRebuild |= true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		markForCacheRefresh();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			if (needCacheRebuild)
			{
				this.needCacheRebuild = false;
				rebuildTileCache();
			}
		}
	}
}
