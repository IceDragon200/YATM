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

import io.polyfox.yatm.api.compactor.CompactingRecipe;
import io.polyfox.yatm.api.YATMApi;
import growthcraft.core.common.inventory.InventorySlice;
import growthcraft.core.common.inventory.IInventoryWatcher;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineCompactor extends AbstractProgressiveMachine implements IInventoryWatcher
{
	protected static int[] inputSlotIds = new int[] { 0 };
	protected static int[] outputSlotIds = new int[] { 1 };
	protected static int[] processingSlotIds = new int[] { 2 };

	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index < 0 || index == 0)
		{
			// input has changed
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 400;
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final ItemStack stack = state.inventory.getStackInSlot(0);
			if (stack != null)
			{
				final CompactingRecipe result = YATMApi.instance().compacting().getRecipe(stack);
				if (result != null)
				{
					if (inventoryProcessor.moveToSlots(state.inventory, result.getInputs(), inputSlotIds, processingSlotIds))
					{
						this.progressMax = result.time;
					}
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
				resetProgress();
				final ItemStack stack = state.inventory.getStackInSlotOnClosing(2);
				if (stack != null)
				{
					final CompactingRecipe result = YATMApi.instance().compacting().getRecipe(stack);
					if (result != null)
					{
						final ItemStack discarded = new InventorySlice(state.inventory, new int[] { 1 }).mergeStackBang(result.asStack());
						if (discarded != null) discardItemStack(discarded);
					}
				}
			}
			else
			{
				this.progress += 1;
			}
		}
	}

	@Override
	protected void updateMachineNotEnoughPower(MachineUpdateState state)
	{
		super.updateMachineNotEnoughPower(state);
		// Compactors lose progress if they are unpowered, or have insufficient power
		if (this.progress > 0)
		{
			this.progress--;
		}
	}
}
