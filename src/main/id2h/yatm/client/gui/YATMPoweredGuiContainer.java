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

import java.util.List;

import id2h.yatm.common.tileentity.YATMPoweredTile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public abstract class YATMPoweredGuiContainer extends YATMGuiContainer
{
	protected YATMPoweredTile poweredTile;

	public YATMPoweredGuiContainer(Container container, YATMPoweredTile te)
	{
		super(container, te);
		this.poweredTile = te;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void initGui()
	{
		super.initGui();
		addTooltipIndex("energy_storage", 164, 16, 4, 72);
	}

	@Override
	protected void drawGuiContainerBackgroundElements(float _f, int x, int y)
	{
		super.drawGuiContainerBackgroundElements(_f, x, y);
		final int x1 = (width - xSize) / 2;
		final int y1 = (height - ySize) / 2;
		drawRFBar(x1 + 164, y1 + 16, poweredTile.getPowerStorageRate(ForgeDirection.UNKNOWN));
	}

	@Override
	protected void addTooltips(String handle, List<String> tooltip)
	{
		super.addTooltips(handle, tooltip);
		switch (handle)
		{
			case "energy_storage":
				tooltip.add(String.format("Energy Stored: %dRF / %dRF",
					poweredTile.getEnergyStored(ForgeDirection.UNKNOWN),
					poweredTile.getMaxEnergyStored(ForgeDirection.UNKNOWN)));
				break;
			default:
				break;
		}
	}
}
