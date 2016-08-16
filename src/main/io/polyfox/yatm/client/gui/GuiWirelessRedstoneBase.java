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
package io.polyfox.yatm.client.gui;

import io.polyfox.yatm.common.tileentity.TileEntityWirelessRedstoneBase;
import io.polyfox.yatm.network.messages.UpdateStringTilePropertyMessage;
import io.polyfox.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiWirelessRedstoneBase<C extends Container, T extends TileEntityWirelessRedstoneBase> extends YATMGuiContainer<C, T>
{
	protected GuiTextField inputAddress;

	public GuiWirelessRedstoneBase(ResourceLocation resloc, C con, T te)
	{
		super(resloc, con, te);
		this.ySize = 176;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.inputAddress = new GuiTextField(getFontRenderer(), guiLeft + 8, guiTop + 8, xSize - 24, 12);
		inputAddress.setText(tileEntity.getAddress());
		inputAddress.setFocused(true);
		inputAddress.setEnabled(true);
	}

	@Override
	protected void drawGuiContainerBackgroundElements(float scale, int x, int y)
	{
		super.drawGuiContainerBackgroundElements(scale, x, y);
		inputAddress.drawTextBox();
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawRFBar(x1 + 164, y1 + 16, tileEntity.getPowerValue() / 15.0f);
	}

	@Override
	public void keyTyped(char eventChar, int eventKey)
	{
		super.keyTyped(eventChar, eventKey);
		inputAddress.textboxKeyTyped(eventChar, eventKey);
	}

	@Override
	public void mouseClicked(int x, int y, int code)
	{
		super.mouseClicked(x, y, code);
		inputAddress.mouseClicked(x, y, code);
	}

	@Override
	public void onGuiClosed()
	{
		final String address = inputAddress.getText().trim();
		YATM.network.sendToServer(
			new UpdateStringTilePropertyMessage(
				tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord,
				0,
				address
			)
		);
		super.onGuiClosed();
	}
}
