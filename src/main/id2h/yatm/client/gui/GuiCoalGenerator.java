/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package id2h.yatm.client.gui;

import growthcraft.core.util.RenderUtils;
import id2h.yatm.common.inventory.ContainerCoalGenerator;
import id2h.yatm.common.tileentity.TileEntityCoalGenerator;
import id2h.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends YATMPoweredGuiContainer<ContainerCoalGenerator, TileEntityCoalGenerator>
{
	protected static final ResourceLocation resloc = YATM.resources.create("textures/gui/coal_generator.png");

	public GuiCoalGenerator(IInventory playerInventory, TileEntityCoalGenerator generator)
	{
		super(resloc, new ContainerCoalGenerator(playerInventory, generator), generator);
		this.ySize = 176;
	}

	@Override
	protected void drawGuiContainerBackgroundElements(float _f, int x, int y)
	{
		super.drawGuiContainerBackgroundElements(_f, x, y);
		RenderUtils.resetColor();
		bindTexture(guiResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		final int mh = 14;
		final int h = (int)(tileEntity.getBurnTimeRate() * mh);
		drawTexturedModalRect(x1 + 81, y1 + 41 + mh - h, 176, mh - h, 14, h);
	}
}
