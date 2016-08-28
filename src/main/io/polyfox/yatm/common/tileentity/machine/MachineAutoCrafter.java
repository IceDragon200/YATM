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

import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.core.common.inventory.InventorySlice;
import growthcraft.api.core.util.NumUtils;

import growthcraft.core.util.ItemUtils;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.ItemStack;

public class MachineAutoCrafter extends AbstractProgressiveMachine implements IInventoryWatcher
{
	protected static class FakeContainer extends Container
	{
		public boolean canInteractWith(EntityPlayer _player)
		{
			return false;
		}
	}

	protected static Container fakeContainer = new FakeContainer();
	protected static int[] inputSlotIds = NumUtils.newIntRangeArray(0, 8);
	protected static int[] processingSlotIds = NumUtils.newIntRangeArray(25, 9);
	protected static int[] craftingSlotIds = NumUtils.newIntRangeArray(16, 9);

	protected boolean recipeNeedsRefresh = true;

	protected void clearProcessing(IInventory inventory)
	{
		inventoryProcessor.clearSlots(inventory, processingSlotIds);
	}

	protected void refundProcessing(IInventory inventory)
	{
		final InventorySlice slice = new InventorySlice(inventory, inputSlotIds);
		for (int slot : processingSlotIds)
		{
			ItemStack stack = inventory.getStackInSlotOnClosing(slot);
			if (stack != null)
			{
				stack = slice.mergeStackBang(stack);
				if (stack != null) discardItemStack(stack);
			}
		}
	}

	protected void writeCraftingSlotsTo(IInventory src, IInventory dest)
	{
		final InventorySlice slice = new InventorySlice(src, craftingSlotIds);
		for (int i = 0; i < 9; ++i)
		{
			dest.setInventorySlotContents(i, slice.getStackInSlot(i));
		}
	}

	protected int countIngredients(IInventory inventory)
	{
		int result = 0;
		for (int slot : craftingSlotIds)
		{
			final ItemStack ind = inventory.getStackInSlot(slot);
			if (ind != null) result += 1;
		}
		return result;
	}

	protected int whereIsIngredient(IInventory inventory, ItemStack ind)
	{
		for (int slot : inputSlotIds)
		{
			final ItemStack slotItem = inventory.getStackInSlot(slot);
			if (slotItem != null)
			{
				if (slotItem.isItemEqual(ind)) return slot;
			}
		}
		return -1;
	}

	protected boolean startProcessing(IInventory inventory)
	{
		for (int i = 0; i < 9; ++i)
		{
			final ItemStack expected = inventory.getStackInSlot(16 + i);
			if (expected != null)
			{
				final int validSlot = whereIsIngredient(inventory, expected);
				if (validSlot != -1)
				{
					inventory.setInventorySlotContents(25 + i, inventory.decrStackSize(validSlot, 1));
				}
				else
				{
					refundProcessing(inventory);
					return false;
				}
			}
		}
		return true;
	}

	private void refreshRecipe(IInventory inventory)
	{
		// recipe has changed
		// DAMN YOU MINECRAFT, WHY THE HELL DO I NEED A CRAFTING INVENTORY D8<
		final InventoryCrafting crafting = new InventoryCrafting(fakeContainer, 3, 3);
		writeCraftingSlotsTo(inventory, crafting);
		final ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafting, tileEntity.getWorldObj());

		inventory.setInventorySlotContents(9, result);
		if (progressMax <= 0)
		{
			refundProcessing(inventory);
			resetProgress();
		}
	}

	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (NumUtils.between(index, 0, 7))
		{
			// input has changed
			awake();
		}
		else if (index < 0 || NumUtils.between(index, 16, 24))
		{
			this.recipeNeedsRefresh = true;
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	protected void outputResult(IInventory inventory)
	{
		final ItemStack result = inventory.getStackInSlot(9);
		inventory.setInventorySlotContents(8, ItemUtils.mergeStacks(inventory.getStackInSlot(8), result));
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (recipeNeedsRefresh)
		{
			this.recipeNeedsRefresh = false;
			refreshRecipe(state.inventory);
		}

		if (progressMax <= 0)
		{
			resetProgress();

			final ItemStack result = state.inventory.getStackInSlot(9);
			if (result != null)
			{
				if (startProcessing(state.inventory))
				{
					this.progressMax = 10 + countIngredients(state.inventory) * 20;
				}
			}

			if (progressMax <= 0) gotoSleep();
		}

		super.updateAwakeMachine(state);
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 160;
	}

	@Override
	public void doWork(MachineUpdateState state)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				resetProgress();
				outputResult(state.inventory);
				clearProcessing(state.inventory);
			}
			else
			{
				this.progress += 1;
			}
		}
	}
}
