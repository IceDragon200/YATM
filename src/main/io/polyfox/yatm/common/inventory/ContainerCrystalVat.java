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
package io.polyfox.yatm.common.inventory;

import io.polyfox.yatm.common.inventory.slot.SlotInput;
import io.polyfox.yatm.common.inventory.slot.SlotInputModule;
import io.polyfox.yatm.common.tileentity.TileEntityCrystalVat;

import net.minecraft.inventory.IInventory;

public class ContainerCrystalVat extends YATMTileContainer
{
	public ContainerCrystalVat(IInventory playerInventory, TileEntityCrystalVat vat)
	{
		super(vat);
		// Crystal Slots
		for (int i = 0; i < 9; ++i)
		{
			final int x = 26 + (i % 3) * 18;
			final int y = 26 + (i / 3) * 18;
			addSlotToContainer(new SlotInput(vat, i, x, y));
		}

		// Addon Modules
		for (int i = 0; i < 3; ++i)
		{
			final int x = 88 + i * 18;
			final int y = 62;
			addSlotToContainer(new SlotInputModule(vat, 9 + i, x, y));
		}

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
