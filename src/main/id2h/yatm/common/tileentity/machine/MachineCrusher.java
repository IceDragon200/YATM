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

import cofh.api.energy.EnergyStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class MachineCrusher extends AbstractProgressiveMachine
{
	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return inventory.getStackInSlot(0) != null || inventory.getStackInSlot(6) != null;
	}

	private void finishProcessing(IInventory inventory)
	{
		inventory.decrStackSize(6, 1);
		resetProgress();
	}

	@Override
	public int doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (inventory.getStackInSlot(6) == null)
		{
			final CrushingResult result = YATMApi.instance.crusher.getCrushingResult(inventory.getStackInSlot(0));
			if (result != null)
			{
				inventory.setInventorySlotContents(6, inventory.decrStackSize(0, 1));
				this.progress = 0.0f;
				this.progressMax = (float)result.time;
			}
		}

		final ItemStack processing = inventory.getStackInSlot(6);
		if (processing != null)
		{
			final CrushingResult result = YATMApi.instance.crusher.getCrushingResult(processing);
			if (result == null)
			{
				inventory.setInventorySlotContents(6, null);
				resetProgress();
			}
			else
			{
				this.progressMax = (float)result.time;
				if ((int)progress >= progressMax)
				{
					finishProcessing(inventory);

					for (PossibleItem item : result.items.randomResults(rand))
					{
						final ItemStack stack = item.asStack();
						System.out.println("Finding available slot for stack=" + stack);
						for (int i = 1; i < 5; ++i)
						{
							final ItemStack target = inventory.getStackInSlot(i);
							if (target == null)
							{
								inventory.setInventorySlotContents(i, stack);
								break;
							}
							else if (target.isItemEqual(stack))
							{
								final int newSize = MathHelper.clamp_int(target.stackSize + stack.stackSize, 0, target.getMaxStackSize());
								final int consumed = newSize - target.stackSize;
								stack.stackSize -= consumed;
								target.stackSize = newSize;
								inventory.setInventorySlotContents(i, target);
							}
							if (stack.stackSize <= 0) break;
						}
						if (stack.stackSize > 0)
						{
							// EJECT
							System.err.println("Item was ejected stack=" + stack);
						}
					}
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
