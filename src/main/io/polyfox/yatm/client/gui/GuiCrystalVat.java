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
package io.polyfox.yatm.client.gui;

import growthcraft.core.client.gui.widget.WidgetFluidTank;
import io.polyfox.yatm.common.inventory.ContainerCrystalVat;
import io.polyfox.yatm.common.tileentity.TileEntityCrystalVat;
import io.polyfox.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiCrystalVat extends YATMGuiContainer<ContainerCrystalVat, TileEntityCrystalVat>
{
	protected static final ResourceLocation resloc = YATM.resources.create("textures/gui/crystal_vat.png");

	public GuiCrystalVat(IInventory playerInventory, TileEntityCrystalVat crystalVat)
	{
		super(resloc, new ContainerCrystalVat(playerInventory, crystalVat), crystalVat);
		this.ySize = 176;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		widgets.add(new WidgetFluidTank(widgets, 0, 152, 16, 16, 72));
	}
}
