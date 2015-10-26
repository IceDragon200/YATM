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

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerAutoCrafter extends YATMTileContainer
{
	public ContainerAutoCrafter(IInventory playerInventory, TileEntityAutoCrafter autoCrafter)
	{
		super(autoCrafter);
		for (int i = 0; i < 3; ++i)
		{
			addSlotToContainer(new Slot(autoCrafter, i, 62 + i * SLOT_W, 8));
		}

		for (int i = 0; i < 3; ++i)
		{
			addSlotToContainer(new Slot(autoCrafter, 3 + i, 62 + i * SLOT_W, 72));
		}
		addSlotToContainer(new Slot(autoCrafter, 6, 80, 40));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
