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
package io.polyfox.yatm.common.tileentity.machine;

import io.polyfox.yatm.api.YATMApi;
import io.polyfox.yatm.api.mixer.MixingResult;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.api.core.util.NumUtils;
import growthcraft.api.core.util.TickUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineMixer extends AbstractProgressiveMachine implements IInventoryWatcher
{
	protected static int[] outputSlotIds = new int[] { 0 };
	protected static int[] inputSlotIds = new int[] { 1, 2, 3, 4 };
	protected static int[] processingSlotIds = new int[] { 5, 6, 7, 8 };

	@Override
	public boolean canWork(MachineUpdateState state)
	{
		return false;
	}

	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index <= 0 || NumUtils.between(index, 1, 4))
		{
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	public ItemStack[] getMixingRecipe(MachineUpdateState state, int[] slots)
	{
		int count = 0;
		for (int slot : slots)
		{
			if (state.inventory.getStackInSlot(slot) != null) count += 1;
		}
		if (count == 0) return null;

		final ItemStack[] result = new ItemStack[count];
		int i = 0;
		for (int slot : slots)
		{
			final ItemStack stack = state.inventory.getStackInSlot(slot);
			if (stack != null)
			{
				result[i] = stack;
				i++;
			}
		}
		return result;
	}

	public MixingResult getSlotResult(MachineUpdateState state, int[] slots)
	{
		final ItemStack[] ary = getMixingRecipe(state, slots);
		if (ary == null) return null;

		return YATMApi.instance().mixing().getMix(ary);
	}

	public MixingResult getInputResult(MachineUpdateState state)
	{
		return getSlotResult(state, inputSlotIds);
	}

	public MixingResult getProcessingResult(MachineUpdateState state)
	{
		return getSlotResult(state, processingSlotIds);
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final MixingResult result = getInputResult(state);

			if (result != null)
			{
				if (inventoryProcessor.moveToSlots(state.inventory, result.getInputs(), inputSlotIds, processingSlotIds))
				{
					progressMax = result.time;
				}
			}

			if (progressMax <= 0) gotoSleep();
		}
		super.updateAwakeMachine(state);
	}

	@Override
	public void doWork(MachineUpdateState state)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				final MixingResult result = getProcessingResult(state);

				if (result != null)
				{
					final ItemStack resultStack = result.asStack();

					if (inventoryProcessor.mergeWithSlots(state.inventory, resultStack, outputSlotIds))
					{
						inventoryProcessor.clearSlots(state.inventory, processingSlotIds);
						resetProgress();
					}
					else
					{
						// output slot is possibly jammed, we'll wait 2 seconds before trying again
						goIdle(TickUtils.seconds(2));
					}
				}
				else
				{
					discardInventorySlots(state, processingSlotIds);
					resetProgress();
				}
			}
			else
			{
				this.progress += 1;
			}
		}
	}
}
