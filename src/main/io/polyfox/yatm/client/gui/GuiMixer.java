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

import growthcraft.core.util.RenderUtils;
import io.polyfox.yatm.common.inventory.ContainerMixer;
import io.polyfox.yatm.common.tileentity.TileEntityMixer;
import io.polyfox.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiMixer extends YATMMachineGuiContainer<ContainerMixer, TileEntityMixer>
{
	protected static final ResourceLocation resloc = YATM.resources.create("textures/gui/mixer.png");

	public GuiMixer(IInventory playerInventory, TileEntityMixer mixer)
	{
		super(resloc, new ContainerMixer(playerInventory, mixer), mixer);
		this.ySize = 176;
	}

	@Override
	protected void drawGuiContainerBackgroundElements(float _f, int x, int y)
	{
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		RenderUtils.resetColor();
		bindTexture(guiResource);
		final int h = (int)(tileEntity.getMachineProgressRate() * 23);
		final int gaugeY = 23 - h;
		drawTexturedModalRect(x1 + 100, y1 + 39 + gaugeY, 176, gaugeY, 11, h);
	}
}
