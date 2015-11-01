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

import id2h.yatm.common.tileentity.feature.IInventoryWatcher;
import id2h.yatm.common.inventory.InventorySlice;
import id2h.yatm.util.NumUtils;
import id2h.yatm.util.YATMDebug;

import growthcraft.core.util.ItemUtils;

import cofh.api.energy.EnergyStorage;

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

	protected void clearProcessing(IInventory inventory)
	{
		for (int i = 0; i < 9; ++i)
		{
			inventory.setInventorySlotContents(25 + i, null);
		}
	}

	protected void refundProcessing(IInventory inventory)
	{
		final InventorySlice slice = new InventorySlice(inventory, NumUtils.newIntRangeArray(0, 8));
		for (int i = 0; i < 9; ++i)
		{
			ItemStack stack = inventory.getStackInSlotOnClosing(25 + i);
			stack = slice.mergeStackBang(stack);
			if (stack != null) discardItemStack(stack);
		}
	}

	protected void writeCraftingSlotsTo(IInventory src, IInventory dest)
	{
		final InventorySlice slice = new InventorySlice(src, NumUtils.newIntRangeArray(16, 9));
		for (int i = 0; i < 9; ++i)
		{
			dest.setInventorySlotContents(i, slice.getStackInSlot(i));
		}
	}

	protected int countIngredients(IInventory inventory)
	{
		int result = 0;
		for (int i = 0; i < 9; ++i)
		{
			final ItemStack ind = inventory.getStackInSlot(16 + i);
			if (ind != null) result += 1;
		}
		return result;
	}

	protected int whereIsIngredient(IInventory inventory, ItemStack ind)
	{
		for (int i = 0; i < 8; ++i)
		{
			final ItemStack slotItem = inventory.getStackInSlot(i);
			if (slotItem != null)
			{
				if (slotItem.isItemEqual(ind)) return i;
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
		refundProcessing(inventory);
		resetProgress();
		YATMDebug.writeMachineState("Recipe has changed machine=" + this + " inv=" + inventory);
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
			refreshRecipe(inventory);
			awake();
		}
		else
		{
			YATMDebug.write("Inventory has changed inv=" + inventory + " index=" + index);
		}
	}

	protected void outputResult(IInventory inventory)
	{
		final ItemStack result = inventory.getStackInSlot(9);
		inventory.setInventorySlotContents(8, ItemUtils.mergeStacks(inventory.getStackInSlot(8), result));
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState _state, EnergyStorage _energyStorage, IInventory inventory)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final ItemStack result = inventory.getStackInSlot(9);
			if (result != null)
			{
				if (startProcessing(inventory))
				{
					this.progressMax = 10 + countIngredients(inventory) * 20;
				}
			}

			if (progressMax <= 0) gotoSleep();
		}
		super.updateAwakeMachine(_state, _energyStorage, inventory);
	}

	@Override
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 80;
	}

	@Override
	public void doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				resetProgress();
				outputResult(inventory);
				clearProcessing(inventory);
			}
			else
			{
				this.progress += 1;
			}
		}
	}
}
