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

public class ItemPlate extends AbstractItemMaterial
{
	public ItemPlate()
	{
		super();
		setUnlocalizedName("yatm.ItemPlate");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[6];
		icons[YATM.items.plateIron.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.Iron");
		icons[YATM.items.plateGold.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.Gold");
		icons[YATM.items.plateCarbonSteel.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.CarbonSteel");
		icons[YATM.items.plateCrystalSteel.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.CrystalSteel");
		icons[YATM.items.plateEnergized.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.Energized");
		icons[YATM.items.platePhotovoltaic.meta] = ir.registerIcon("yatm:ItemMaterial.Plate.Photovoltaic");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks)
	{
		itemStacks.add(YATM.items.plateIron.asStack());
		itemStacks.add(YATM.items.plateGold.asStack());
		itemStacks.add(YATM.items.plateCarbonSteel.asStack());
		itemStacks.add(YATM.items.plateCrystalSteel.asStack());
		itemStacks.add(YATM.items.plateEnergized.asStack());
		itemStacks.add(YATM.items.platePhotovoltaic.asStack());
	}
}
