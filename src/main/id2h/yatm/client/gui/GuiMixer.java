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
package id2h.yatm.client.gui;

import id2h.yatm.common.inventory.ContainerMixer;
import id2h.yatm.common.tileentity.TileEntityMixer;
import id2h.yatm.client.util.RenderUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class GuiMixer extends YATMGuiContainer
{
	protected static final ResourceLocation mixerResource = new ResourceLocation("yatm", "textures/gui/GuiMixer.png");
	protected TileEntityMixer tileEntity;

	public GuiMixer(IInventory playerInventory, TileEntityMixer mixer)
	{
		super(new ContainerMixer(playerInventory, mixer));
		this.tileEntity = mixer;
		this.ySize = 176;
	}

	public void drawGuiContainerBackgroundLayer(float _f, int x, int y)
	{
		RenderUtils.resetColor();
		bindTexture(mixerResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawTexturedModalRect(x1, y1, 0, 0, xSize, ySize);

		final int h = (int)(tileEntity.getMachineProgressRate() * 23);
		final int gaugeY = 23 - h;
		drawTexturedModalRect(x1 + 100, y1 + 39 + gaugeY, 176, gaugeY, 11, h);

		drawRFBar(x1 + 164, y1 + 16, tileEntity.getPowerStorageRate(ForgeDirection.UNKNOWN));
	}
}
