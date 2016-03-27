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

import id2h.yatm.creativetab.CreativeTabsYATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class AbstractItemMaterial extends Item
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	public AbstractItemMaterial()
	{
		super();
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabsYATM.instance());
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		return icons[stack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return getIconIndex(stack);
	}
}
