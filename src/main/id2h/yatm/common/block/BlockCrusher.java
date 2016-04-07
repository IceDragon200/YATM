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

import id2h.yatm.common.tileentity.TileEntityCrusher;
import id2h.yatm.util.GuiType;
import id2h.yatm.util.BlockSides;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCrusher extends YATMBlockBaseMachine
{
	public BlockCrusher()
	{
		super(Material.iron, TileEntityCrusher.class);
		setBlockName("yatm.BlockCrusher");
		setBlockTextureName("yatm:BlockCrusher");
		setGuiType(GuiType.CRUSHER);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		final int l = world.getBlockMetadata(x, y, z);
		if ((l & 4) == 4)
		{
			final TileEntityCrusher te = getTileEntity(world, x, y, z);
			if (te != null)
			{
				final ItemStack crushingStack = te.getCrushingBlock();
				if (crushingStack != null)
				{
					final Block block = ((ItemBlock)crushingStack.getItem()).field_150939_a;
					if (block != null)
					{
						final int bmeta = crushingStack.getItemDamage();
						final ForgeDirection dir = BlockSides.getOppositeForgeDirection4(l & 3);
						final float px = (float)x + 0.5F;
						final float py = (float)y + 0.5F;
						final float pz = (float)z + 0.5F;

						// All that, just to spawn a damn block particle -.-;
						final String particle = "blockcrack_" + Block.getIdFromBlock(block) + "_" + bmeta;
						world.spawnParticle(particle, (double)(px + dir.offsetX * 0.52f), (double)py, (double)(pz + dir.offsetZ * 0.52f), 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
	}
}
