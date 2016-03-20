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
package id2h.yatm.client.gui;

import growthcraft.core.client.gui.GrcGuiContainer;
import growthcraft.core.util.RenderUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public abstract class YATMGuiContainer extends GrcGuiContainer
{
	protected static final ResourceLocation yatmCommonResource = new ResourceLocation("yatm", "textures/gui/Gui_Common.png");
	protected ResourceLocation guiResource;

	public YATMGuiContainer(Container container, TileEntity te)
	{
		super(container, te);
	}

	protected void drawRFBar(int x, int y, float rate)
	{
		final int w = 4;
		final int h = (int)(72 * rate);
		final int gaugeY = 72 - h;
		RenderUtils.resetColor();
		bindTexture(yatmCommonResource);
		drawTexturedModalRect(x, y + gaugeY, 0, gaugeY, w, h);
	}

	protected void drawGuiContainerBackgroundPanel(float _f, int x, int y)
	{
		RenderUtils.resetColor();
		bindTexture(guiResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawTexturedModalRect(x1, y1, 0, 0, xSize, ySize);
	}

	protected void drawGuiContainerBackgroundElements(float _f, int x, int y)
	{
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float _f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(_f, x, y);
		drawGuiContainerBackgroundPanel(_f, x, y);
		drawGuiContainerBackgroundElements(_f, x, y);
	}
}
