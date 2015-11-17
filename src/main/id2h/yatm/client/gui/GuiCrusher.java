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

import id2h.yatm.common.inventory.ContainerCrusher;
import id2h.yatm.common.tileentity.TileEntityCrusher;
import growthcraft.core.util.RenderUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class GuiCrusher extends YATMGuiContainer
{
	protected static final ResourceLocation crusherResource = new ResourceLocation("yatm", "textures/gui/GuiCrusher.png");
	protected TileEntityCrusher tileEntity;

	public GuiCrusher(IInventory playerInventory, TileEntityCrusher crusher)
	{
		super(new ContainerCrusher(playerInventory, crusher));
		this.tileEntity = crusher;
		this.ySize = 176;
	}

	public void drawGuiContainerBackgroundLayer(float _f, int x, int y)
	{
		RenderUtils.resetColor();
		bindTexture(crusherResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawTexturedModalRect(x1, y1, 0, 0, xSize, ySize);

		final int w = (int)(tileEntity.getMachineProgressRate() * 15);
		drawTexturedModalRect(x1 + 104, y1 + 41, 176, 0, w, 16);

		drawRFBar(x1 + 164, y1 + 16, tileEntity.getPowerStorageRate(ForgeDirection.UNKNOWN));
	}
}
