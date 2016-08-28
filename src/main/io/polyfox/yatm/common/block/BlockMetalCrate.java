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
package io.polyfox.yatm.common.block;

import growthcraft.core.util.ItemUtils;
import io.polyfox.yatm.common.tileentity.TileEntityCrate;
import io.polyfox.yatm.creativetab.CreativeTabsYATM;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMetalCrate extends YATMBlockBaseMachine
{
	public BlockMetalCrate()
	{
		super(Material.iron, TileEntityCrate.class);
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.metal_crate");
		setBlockTextureName("yatm:metal_crate");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	public boolean isRotatable(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)) return true;
		final TileEntityCrate te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			if (!world.isRemote)
			{
				final ItemStack stack = player.inventory.getStackInSlot(player.inventory.currentItem);
				if (stack != null)
				{
					ItemStack inStack = stack;
					if (!player.isSneaking())
					{
						inStack = stack.copy();
						inStack.stackSize = 1;
					}
					final int amount = te.insertItem(stack);
					player.inventory.decrStackSize(player.inventory.currentItem, amount);
				}
				else
				{
					// @todo Report current inventory size to player
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			final TileEntityCrate te = getTileEntity(world, x, y, z);
			if (te != null)
			{
				final ItemStack stack = player.isSneaking() ? te.extractItem() : te.extractItem(1);
				if (stack != null)
				{
					ItemUtils.addStackToPlayer(stack, player, world, false);
				}
			}
		}
	}
}
