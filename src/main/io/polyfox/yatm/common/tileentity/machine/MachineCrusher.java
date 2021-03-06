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

import growthcraft.api.core.definition.IMultiItemStacks;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.core.common.inventory.InventorySlice;
import io.polyfox.yatm.api.core.util.PossibleItem;
import io.polyfox.yatm.api.crusher.CrushingRecipe;
import io.polyfox.yatm.api.YATMApi;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineCrusher extends AbstractProgressiveMachine implements IInventoryWatcher
{
	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index < 0 || index == 0)
		{
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	protected CrushingRecipe getCrushingRecipeFromInput(IInventory inventory)
	{
		return YATMApi.instance().crushing().getRecipe(inventory.getStackInSlot(0));
	}

	protected CrushingRecipe getCurshingResultFromProcessing(IInventory inventory)
	{
		return YATMApi.instance().crushing().getRecipe(inventory.getStackInSlot(6));
	}

	protected void addOutputItem(IInventory inventory, ItemStack stack)
	{
		final ItemStack result = new InventorySlice(inventory, new int[] { 1, 2, 3, 4 }).mergeStackBang(stack);
		if (result != null) discardItemStack(result);
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final CrushingRecipe result = getCrushingRecipeFromInput(state.inventory);

			if (result != null)
			{
				final IMultiItemStacks inputStack = result.getInput();
				final ItemStack srcStack = state.inventory.getStackInSlot(0);
				if (inputStack.getStackSize() <= srcStack.stackSize)
				{
					state.inventory.setInventorySlotContents(6, state.inventory.decrStackSize(0, inputStack.getStackSize()));
					this.progress = 0.0f;
					this.progressMax = (float)result.time;
				}
			}

			if (progressMax <= 0) gotoSleep();
		}
		super.updateAwakeMachine(state);
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 40;
	}

	@Override
	public void doWork(MachineUpdateState state)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				final CrushingRecipe result = getCurshingResultFromProcessing(state.inventory);
				resetProgress();

				if (result != null)
				{
					for (PossibleItem item : result.items.randomResults(rand))
					{
						final ItemStack stack = item.asStack();
						addOutputItem(state.inventory, stack);
					}
				}

				state.inventory.setInventorySlotContents(6, null);
			}
			else
			{
				this.progress += 1;
			}
		}
	}
}
