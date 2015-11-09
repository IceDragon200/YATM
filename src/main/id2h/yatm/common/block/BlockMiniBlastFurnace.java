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
package id2h.yatm.common.block;

import java.util.Random;

import id2h.yatm.common.tileentity.TileEntityMiniBlastFurnace;
import id2h.yatm.util.GuiType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// Also known as the poor mans blast furnace, its much slower than a regular
// blast furnace because it runs on RF.
public class BlockMiniBlastFurnace extends YATMBlockBaseMachine
{
	public BlockMiniBlastFurnace()
	{
		super(Material.rock, TileEntityMiniBlastFurnace.class);
		setBlockName("yatm.BlockMiniBlastFurnace");
		setBlockTextureName("yatm:BlockMiniBlastFurnace");
		setGuiType(GuiType.MINI_BLAST_FURNACE);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		final Block block = world.getBlock(x, y, z);
		if (block != this)
		{
			return block.getLightValue(world, x, y, z);
		}
		final int l = world.getBlockMetadata(x, y, z);
		if ((l & 4) == 4)
		{
			return 15;
		}
		return getLightValue();
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
