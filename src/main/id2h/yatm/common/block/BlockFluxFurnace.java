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

import id2h.yatm.common.tileentity.TileEntityFluxFurnace;
import id2h.yatm.util.GuiType;
import id2h.yatm.util.BlockFlags;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockFluxFurnace extends YATMBlockBaseMachine
{
	public BlockFluxFurnace()
	{
		super(Material.rock, TileEntityFluxFurnace.class);
		setBlockName("yatm.BlockFluxFurnace");
		setBlockTextureName("yatm:BlockFluxFurnace");
		setGuiType(GuiType.FLUX_FURNACE);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		int meta = 2;

		if (l == 0) meta = 0;
		else if (l == 1) meta = 3;
		else if (l == 2) meta = 1;
		else if (l == 3) meta = 2;

		world.setBlockMetadataWithNotify(x, y, z, meta, BlockFlags.UPDATE_CLIENT);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[6];

		icons[0] = optionalIcon(reg, getTextureName() + "/Bottom", null);
		icons[1] = optionalIcon(reg, getTextureName() + "/Top.Off", null);
		icons[2] = optionalIcon(reg, getTextureName() + "/Top.On", null);
		icons[3] = optionalIcon(reg, getTextureName() + "/Side", null);
		icons[4] = optionalIcon(reg, getTextureName() + "/Front.Off", null);
		icons[5] = optionalIcon(reg, getTextureName() + "/Front.On", null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0)
		{
			return icons[0];
		}
		else
		{
			final boolean online = (meta & 4) == 4;
			if (side == 1)
			{
				return online ? icons[2] : icons[1];
			}
			else
			{
				final int facing = meta & 3;
				if (facing == (side - 2))
				{
					return online ? icons[5] : icons[4];
				}
			}
		}
		return icons[3];
	}
}
