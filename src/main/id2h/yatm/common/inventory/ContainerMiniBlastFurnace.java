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
package id2h.yatm.common.inventory;

import id2h.yatm.common.tileentity.TileEntityMiniBlastFurnace;
import id2h.yatm.common.inventory.slot.SlotInput;
import id2h.yatm.common.inventory.slot.SlotOutput;
import id2h.yatm.common.inventory.slot.SlotProcessing;

import net.minecraft.inventory.IInventory;

public class ContainerMiniBlastFurnace extends YATMTileContainer
{
	public ContainerMiniBlastFurnace(IInventory playerInventory, TileEntityMiniBlastFurnace blastFurnace)
	{
		super(blastFurnace);

		addSlotToContainer(new SlotInput(blastFurnace, 0, 25, 43));
		addSlotToContainer(new SlotInput(blastFurnace, 1, 46, 43));
		addSlotToContainer(new SlotOutput(blastFurnace, 2, 118, 43));
		addSlotToContainer(new SlotProcessing(blastFurnace, 3, 72, 34));
		addSlotToContainer(new SlotProcessing(blastFurnace, 4, 72, 52));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
