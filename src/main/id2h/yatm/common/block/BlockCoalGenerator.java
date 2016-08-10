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
package id2h.yatm.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import id2h.yatm.common.tileentity.TileEntityCoalGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

public class BlockCoalGenerator extends YATMBlockBaseGenerator
{
	public BlockCoalGenerator()
	{
		super(Material.iron, TileEntityCoalGenerator.class);
		setBlockName("yatm.coal_generator");
		setBlockTextureName("yatm:coal_generator");
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		final int l = world.getBlockMetadata(x, y, z);
		if ((l & 4) == 4)
		{
			final float px = (float)x + 0.2f + 0.6f * random.nextFloat();
			final float py = (float)y + 1.0F;
			final float pz = (float)z + 0.2f + 0.6f * random.nextFloat();

			world.spawnParticle("smoke", (double)px, (double)py, (double)pz, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", (double)px, (double)py, (double)pz, 0.0D, 0.0D, 0.0D);
		}
	}
}
