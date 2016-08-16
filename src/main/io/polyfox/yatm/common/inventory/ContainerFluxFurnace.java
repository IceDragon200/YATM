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
package io.polyfox.yatm.common.inventory;

import io.polyfox.yatm.common.tileentity.TileEntityFluxFurnace;
import io.polyfox.yatm.common.inventory.slot.SlotInput;
import io.polyfox.yatm.common.inventory.slot.SlotOutput;
import io.polyfox.yatm.common.inventory.slot.SlotProcessing;

import net.minecraft.inventory.IInventory;

public class ContainerFluxFurnace extends YATMTileContainer
{
	public ContainerFluxFurnace(IInventory playerInventory, TileEntityFluxFurnace fluxFurnace)
	{
		super(fluxFurnace);
		// input
		addSlotToContainer(new SlotInput(fluxFurnace, 0, 17, 31));
		addSlotToContainer(new SlotInput(fluxFurnace, 1, 35, 31));
		addSlotToContainer(new SlotInput(fluxFurnace, 2, 17, 49));
		addSlotToContainer(new SlotInput(fluxFurnace, 3, 35, 49));

		// output
		addSlotToContainer(new SlotOutput(fluxFurnace, 4, 123, 31));
		addSlotToContainer(new SlotOutput(fluxFurnace, 5, 141, 31));
		addSlotToContainer(new SlotOutput(fluxFurnace, 6, 123, 49));
		addSlotToContainer(new SlotOutput(fluxFurnace, 7, 141, 49));

		// processing
		addSlotToContainer(new SlotProcessing(fluxFurnace, 8, 71, 32));
		addSlotToContainer(new SlotProcessing(fluxFurnace, 9, 87, 32));
		addSlotToContainer(new SlotProcessing(fluxFurnace, 10, 71, 48));
		addSlotToContainer(new SlotProcessing(fluxFurnace, 11, 87, 48));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
