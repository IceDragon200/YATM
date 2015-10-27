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

import id2h.yatm.common.tileentity.inventory.InventorySlice;

import cofh.api.energy.EnergyStorage;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineAutoGrinder extends AbstractProgressiveMachine
{
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

	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return getGrinderEntryFromInput(inventory) != null ||
			getGrinderEntryFromProcessing(inventory) != null;
	}

	protected void addOutputItem(IInventory inventory, ItemStack stack)
	{
		final ItemStack srcStack = stack.copy();
		new InventorySlice(inventory, new int[] { 3, 4, 5 }).mergeStackBang(srcStack);
		if (srcStack.stackSize > 0)
		{
			// EJECT stack
			System.err.println("Item was ejected stack=" + srcStack);
		}
	}

	@Override
	public int doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (inventory.getStackInSlot(6) == null)
		{
			for (int i = 0; i < 3; ++i)
			{
				final ItemStack stack = inventory.getStackInSlot(i);
				final IGrinderEntry entry = AEApi.instance().registries().grinder().getRecipeForInput(stack);
				if (entry != null)
				{
					if (stack.stackSize >= entry.getInput().stackSize)
					{
						inventory.setInventorySlotContents(6, inventory.decrStackSize(i, entry.getInput().stackSize));
						this.progress = 0.0f;
						this.progressMax = (float)entry.getEnergyCost() * 5;
						break;
					}
				}
			}
		}

		if (inventory.getStackInSlot(6) != null)
		{
			if (progress >= progressMax)
			{
				final IGrinderEntry entry = getGrinderEntryFromProcessing(inventory);
				resetProgress();

				addOutputItem(inventory, entry.getOutput());

				float chance = (rand.nextInt(2001)) / 2000.0f;
				if (chance <= entry.getOptionalChance())
				{
					addOutputItem(inventory, entry.getOptionalOutput());
				}

				chance = (rand.nextInt(2001)) / 2000.0f;
				if (chance <= entry.getSecondOptionalChance())
				{
					addOutputItem(inventory, entry.getSecondOptionalOutput());
				}

				inventory.setInventorySlotContents(6, null);
			}
			else
			{
				this.progress += 1;
			}
		}
		return 0;
	}
}
