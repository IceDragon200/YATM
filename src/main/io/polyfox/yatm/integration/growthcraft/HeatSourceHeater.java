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
package io.polyfox.yatm.integration.growthcraft;

import io.polyfox.yatm.common.block.BlockHeater;
import io.polyfox.yatm.common.tileentity.TileEntityHeater;
import io.polyfox.yatm.YATM;

import growthcraft.api.cellar.heatsource.IHeatSourceBlock;

import net.minecraft.world.World;

public class HeatSourceHeater implements IHeatSourceBlock
{
	public float getHeat(World world, int x, int y, int z)
	{
		final BlockHeater heaterBlock = (BlockHeater)YATM.blocks.heater.getBlock();
		final TileEntityHeater te = heaterBlock.getTileEntity(world, x, y, z);
		if (te != null)
		{
			return te.getHeatValue();
		}
		return 0.0f;
	}
}