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

public class ItemIngot extends AbstractItemMaterial
{
	public ItemIngot()
	{
		super();
		setUnlocalizedName("yatm.ingot");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[4];
		icons[YATM.items.ingotUranium.meta] = ir.registerIcon("yatm:materials/ingots/uranium");
		icons[YATM.items.ingotPureUranium.meta] = ir.registerIcon("yatm:materials/ingots/pure_uranium");
		icons[YATM.items.ingotCarbonSteel.meta] = ir.registerIcon("yatm:materials/ingots/carbon_steel");
		icons[YATM.items.ingotCrystalSteel.meta] = ir.registerIcon("yatm:materials/ingots/crystal_steel");
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks)
	{
		itemStacks.add(YATM.items.ingotUranium.asStack());
		itemStacks.add(YATM.items.ingotPureUranium.asStack());
		itemStacks.add(YATM.items.ingotCarbonSteel.asStack());
		itemStacks.add(YATM.items.ingotCrystalSteel.asStack());
	}
}
