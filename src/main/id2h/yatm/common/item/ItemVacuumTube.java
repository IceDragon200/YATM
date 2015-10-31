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
package id2h.yatm.common.item;

import java.util.List;

import id2h.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemVacuumTube extends AbstractItemMaterial
{
	public ItemVacuumTube()
	{
		super();
		setUnlocalizedName("yatm.ItemVacuumTube");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[4];
		icons[YATM.items.vacuumTubeIron.meta] = ir.registerIcon("yatm:ItemMaterial.RedstoneVacuumTube.Iron");
		icons[YATM.items.vacuumTubeGold.meta] = ir.registerIcon("yatm:ItemMaterial.RedstoneVacuumTube.Gold");
		icons[YATM.items.vacuumTubeDiamond.meta] = ir.registerIcon("yatm:ItemMaterial.RedstoneVacuumTube.Diamond");
		icons[YATM.items.vacuumTubeObsidian.meta] = ir.registerIcon("yatm:ItemMaterial.RedstoneVacuumTube.Obsidian");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks)
	{
		itemStacks.add(YATM.items.vacuumTubeIron.asStack());
		itemStacks.add(YATM.items.vacuumTubeGold.asStack());
		itemStacks.add(YATM.items.vacuumTubeDiamond.asStack());
		itemStacks.add(YATM.items.vacuumTubeObsidian.asStack());
	}
}
