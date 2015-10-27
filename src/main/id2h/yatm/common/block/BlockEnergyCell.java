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

import id2h.yatm.client.renderer.RenderEnergyCell;
import id2h.yatm.common.tileentity.TileEntityEnergyCell;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyCell extends YATMBlockBaseTile
{
	public BlockEnergyCell(String basename, Class<? extends TileEntityEnergyCell> tileentity)
	{
		super(Material.rock, tileentity);
		setBlockName("yatm.BlockEnergyCell" + basename);
		setBlockTextureName("yatm:BlockEnergyCell." + basename);
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isRemote) return;

		final TileEntityEnergyCell te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			te.onNeighborBlockChange(world, x, y, z, block);
		}
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
			icons[i] = optionalIcon(reg, getTextureName() + "/Stage" + i, null);
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