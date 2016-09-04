/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package io.polyfox.yatm.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileUtils
{
	private static final TileUtils inst = new TileUtils();

	public TileEntity[] createTileCache()
	{
		return new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];
	}

	public void populateTileCache(TileEntity[] cache, World world, int x, int y, int z)
	{
		for (int i = 0; i < cache.length; ++i)
		{
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			final int fx = x + dir.offsetX;
			final int fy = y + dir.offsetY;
			final int fz = z + dir.offsetZ;
			cache[i] = world.getTileEntity(fx, fy, fz);
		}
	}

	public static TileUtils instance()
	{
		return inst;
	}
}
