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
package id2h.yatm.common.tileentity.machine;

import id2h.yatm.api.YATMApi;
import id2h.yatm.api.crusher.CrushingResult;
import id2h.yatm.api.core.util.PossibleItem;
import id2h.yatm.common.tileentity.inventory.InventorySlice;

import cofh.api.energy.EnergyStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineCrusher extends AbstractProgressiveMachine
{
	protected CrushingResult getCrushingResultFromInput(IInventory inventory)
	{
		return YATMApi.instance().crushing().getCrushingResult(inventory.getStackInSlot(0));
	}

	protected CrushingResult getCrushingResultFromtProcessing(IInventory inventory)
	{
		return YATMApi.instance().crushing().getCrushingResult(inventory.getStackInSlot(6));
	}

	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return getCrushingResultFromInput(inventory) != null ||
			getCrushingResultFromtProcessing(inventory) != null;
	}

	protected void addOutputItem(IInventory inventory, ItemStack stack)
	{
		new InventorySlice(inventory, new int[] { 1, 2, 3, 4 }).mergeStackBang(stack);
		if (stack.stackSize > 0)
		{
			// EJECT stack
			System.err.println("Item was ejected stack=" + stack);
		}
	}

	@Override
	public int doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (inventory.getStackInSlot(6) == null)
		{
			final CrushingResult result = getCrushingResultFromInput(inventory);
			if (result != null)
			{
				final ItemStack inputStack = result.getInput();
				final ItemStack srcStack = inventory.getStackInSlot(0);
				if (inputStack.stackSize <= srcStack.stackSize)
				{
					inventory.setInventorySlotContents(6, inventory.decrStackSize(0, inputStack.stackSize));
					this.progress = 0.0f;
					this.progressMax = (float)result.time;
				}
			}
		}

		if (inventory.getStackInSlot(6) != null)
		{
			final CrushingResult result = getCrushingResultFromtProcessing(inventory);

			if (result == null)
			{
				// EJECT stack
				inventory.setInventorySlotContents(6, null);
				resetProgress();
			}
			else
			{
				this.progressMax = (float)result.time;
				if (progress >= progressMax)
				{
					resetProgress();

					for (PossibleItem item : result.items.randomResults(rand))
					{
						final ItemStack stack = item.asStack();
						System.out.println("Finding available slot for stack=" + stack);
						addOutputItem(inventory, stack);
					}

					inventory.setInventorySlotContents(6, null);
				}
				else
				{
					this.progress += 1;
				}
			}
		}
		return 0;
	}
}
