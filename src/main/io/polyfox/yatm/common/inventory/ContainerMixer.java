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

import io.polyfox.yatm.common.tileentity.TileEntityMixer;
import io.polyfox.yatm.common.inventory.slot.SlotInput;
import io.polyfox.yatm.common.inventory.slot.SlotOutput;

import net.minecraft.inventory.IInventory;

public class ContainerMixer extends YATMTileContainer
{
	public ContainerMixer(IInventory playerInventory, TileEntityMixer mixer)
	{
		super(mixer);
		addSlotToContainer(new SlotOutput(mixer, 0, 62, 42));

		addSlotToContainer(new SlotInput(mixer, 1, 81, 42));
		addSlotToContainer(new SlotInput(mixer, 2, 62, 23));
		addSlotToContainer(new SlotInput(mixer, 3, 62, 61));
		addSlotToContainer(new SlotInput(mixer, 4, 43, 42));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
