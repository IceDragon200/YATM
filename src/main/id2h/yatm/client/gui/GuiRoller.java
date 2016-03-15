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

import id2h.yatm.common.inventory.ContainerRoller;
import id2h.yatm.common.tileentity.TileEntityRoller;
import growthcraft.core.util.RenderUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiRoller extends YATMMachineGuiContainer
{
	protected static final ResourceLocation rollerResource = new ResourceLocation("yatm", "textures/gui/GuiRoller.png");
	protected TileEntityRoller tileEntity;

	public GuiRoller(IInventory playerInventory, TileEntityRoller roller)
	{
		super(new ContainerRoller(playerInventory, roller), roller);
		this.tileEntity = roller;
		this.ySize = 176;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float _f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(_f, x, y);
		RenderUtils.resetColor();
		bindTexture(rollerResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawTexturedModalRect(x1, y1, 0, 0, xSize, ySize);

		final int h = (int)(tileEntity.getMachineProgressRate() * 19);
		final int gaugeY = 19 - h;
		drawTexturedModalRect(x1 + 97, y1 + 40 + gaugeY, 176, gaugeY, 18, h);
	}
}
