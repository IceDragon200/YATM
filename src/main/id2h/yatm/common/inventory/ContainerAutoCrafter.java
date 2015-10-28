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

import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.inventory.slot.SlotInput;
import id2h.yatm.common.inventory.slot.SlotOutput;
import id2h.yatm.common.inventory.slot.SlotProcessing;
import id2h.yatm.util.NumUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerAutoCrafter extends YATMTileContainer
{
	public ContainerAutoCrafter(InventoryPlayer playerInventory, TileEntityAutoCrafter autoCrafter)
	{
		super(autoCrafter);

		// Input Slots
		for (int row = 0; row < 4; ++row)
		{
			for (int col = 0; col < 2; ++col)
			{
				final int slotIndex = col + row * 2;
				addSlotToContainer(new SlotInput(autoCrafter, slotIndex, 17 + col * SLOT_W, 18 + row * SLOT_H));
			}
		}

		final InventorySlice craftingGrid = new InventoryCraftingSlice(autoCrafter, NumUtils.newIntRangeArray(16, 9));

		// Crafting Slots
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 3; ++col)
			{
				final int slotIndex = col + row * 3;
				addSlotToContainer(new Slot(craftingGrid, slotIndex, 62 + col * SLOT_W, 18 + row * SLOT_H));
			}
		}

		addSlotToContainer(new SlotProcessing(autoCrafter, 9, 133, 28));
		addSlotToContainer(new SlotOutput(autoCrafter, 8, 133, 63));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
