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
package io.polyfox.yatm.common.block;

import java.util.ArrayList;

import io.polyfox.yatm.client.renderer.RenderEnergyCell;
import io.polyfox.yatm.client.util.StateIconLoader;
import io.polyfox.yatm.common.tileentity.TileEntityEnergyCell;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockEnergyCell extends YATMBlockBaseTile
{
	public BlockEnergyCell(String basename, Class<? extends TileEntityEnergyCell> tileentity)
	{
		super(Material.iron, tileentity);
		setBlockName(String.format("yatm.energy_cell_%s", basename));
		setBlockTextureName(String.format("yatm:energy_cell_%s", basename));
	}

	@Override
	public boolean isRotatable(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		final ArrayList<ItemStack> result = new ArrayList<ItemStack>();

		final TileEntityEnergyCell te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			final ItemStack itemStack = new ItemStack(this);
			final NBTTagCompound teTag = new NBTTagCompound();
			final NBTTagCompound itemTag = new NBTTagCompound();
			te.writeToNBTForItem(teTag);
			itemTag.setTag("tiledata", teTag);
			itemTag.setInteger("energy", te.getEnergyStored(ForgeDirection.UNKNOWN));
			itemTag.setInteger("energy_max", te.getMaxEnergyStored(ForgeDirection.UNKNOWN));
			itemStack.setTagCompound(itemTag);
			result.add(itemStack);
		}
		else
		{
			result.add(new ItemStack(this));
		}
		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return RenderEnergyCell.id;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[8];
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = StateIconLoader.instance.optionalIcon(reg, getTextureName() + "/stage" + i, null);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= 0 && meta < icons.length)
		{
			return icons[meta];
		}
		return icons[icons.length - 1];
	}
}
