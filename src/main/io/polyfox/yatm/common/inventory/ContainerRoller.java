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

import io.polyfox.yatm.common.tileentity.TileEntityRoller;
import io.polyfox.yatm.common.inventory.slot.SlotInputRoller;
import io.polyfox.yatm.common.inventory.slot.SlotOutput;
import io.polyfox.yatm.common.inventory.slot.SlotProcessing;

import net.minecraft.inventory.IInventory;

public class ContainerRoller extends YATMTileContainer
{
	public ContainerRoller(IInventory playerInventory, TileEntityRoller roller)
	{
		super(roller);

		addSlotToContainer(new SlotInputRoller(roller, 0, 30, 43));
		addSlotToContainer(new SlotProcessing(roller, 2, 71, 43));
		addSlotToContainer(new SlotOutput(roller, 1, 124, 43));

		bindPlayerInventory(playerInventory, 8, 94);
	}
}
