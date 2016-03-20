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

import growthcraft.core.common.inventory.InventorySlice;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.api.core.util.NumUtils;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineAutoGrinder extends AbstractProgressiveMachine implements IInventoryWatcher
{
	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index < 0 || NumUtils.between(index, 0, 2))
		{
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	protected IGrinderEntry getGrinderEntryFromInput(IInventory inventory)
	{
		for (int i = 0; i < 3; ++i)
		{
			final ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null)
			{
				final IGrinderEntry entry = AEApi.instance().registries().grinder().getRecipeForInput(stack);
				if (entry != null)
				{
					return entry;
				}
			}
		}
		return null;
	}

	protected IGrinderEntry getGrinderEntryFromProcessing(IInventory inventory)
	{
		return AEApi.instance().registries().grinder().getRecipeForInput(inventory.getStackInSlot(6));
	}

	protected void addOutputItem(IInventory inventory, ItemStack stack)
	{
		final ItemStack srcStack = stack.copy();
		final ItemStack result = new InventorySlice(inventory, new int[] { 3, 4, 5 }).mergeStackBang(srcStack);
		if (result != null) discardItemStack(result);
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			for (int i = 0; i < 3; ++i)
			{
				final ItemStack stack = state.inventory.getStackInSlot(i);
				final IGrinderEntry entry = AEApi.instance().registries().grinder().getRecipeForInput(stack);
				if (entry != null)
				{
					if (stack.stackSize >= entry.getInput().stackSize)
					{
						state.inventory.setInventorySlotContents(6, state.inventory.decrStackSize(i, entry.getInput().stackSize));
						this.progress = 0.0f;
						this.progressMax = (float)entry.getEnergyCost() * 5;
						break;
					}
				}
			}

			if (progressMax <= 0) gotoSleep();
		}
		super.updateAwakeMachine(state);
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 50;
	}

	@Override
	public void doWork(MachineUpdateState state)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				final IGrinderEntry entry = getGrinderEntryFromProcessing(state.inventory);
				resetProgress();

				if (entry != null)
				{
					addOutputItem(state.inventory, entry.getOutput());

					float chance = (rand.nextInt(2001)) / 2000.0f;
					if (chance <= entry.getOptionalChance())
					{
						addOutputItem(state.inventory, entry.getOptionalOutput());
					}

					chance = (rand.nextInt(2001)) / 2000.0f;
					if (chance <= entry.getSecondOptionalChance())
					{
						addOutputItem(state.inventory, entry.getSecondOptionalOutput());
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
