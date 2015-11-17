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

import id2h.yatm.common.inventory.ContainerCompactor;
import id2h.yatm.common.tileentity.TileEntityCompactor;
import growthcraft.core.util.RenderUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class GuiCompactor extends YATMGuiContainer
{
	protected static final ResourceLocation compactorResource = new ResourceLocation("yatm", "textures/gui/GuiCompactor.png");
	protected TileEntityCompactor tileEntity;

	public GuiCompactor(IInventory playerInventory, TileEntityCompactor compactor)
	{
		super(new ContainerCompactor(playerInventory, compactor));
		this.tileEntity = compactor;
		this.ySize = 176;
	}

	public void drawGuiContainerBackgroundLayer(float _f, int x, int y)
	{
		RenderUtils.resetColor();
		bindTexture(compactorResource);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawTexturedModalRect(x1, y1, 0, 0, xSize, ySize);

		final int w = (int)(tileEntity.getMachineProgressRate() * 14);
		drawTexturedModalRect(x1 + 106, y1 + 43, 176, 0, w, 15);

		drawRFBar(x1 + 164, y1 + 16, tileEntity.getPowerStorageRate(ForgeDirection.UNKNOWN));
	}
}
