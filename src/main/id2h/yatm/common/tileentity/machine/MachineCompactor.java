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

import id2h.yatm.api.compactor.CompactingResult;
import id2h.yatm.api.YATMApi;
import id2h.yatm.common.inventory.InventorySlice;
import id2h.yatm.common.tileentity.feature.IInventoryWatcher;

import cofh.api.energy.EnergyStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineCompactor extends AbstractProgressiveMachine implements IInventoryWatcher
{
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
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 200;
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState _state, EnergyStorage _energyStorage, IInventory inventory)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final ItemStack stack = inventory.getStackInSlot(0);
			if (stack != null)
			{
				final CompactingResult result = YATMApi.instance().compacting().getCompacting(stack);
				if (result != null)
				{
					if (result.getInput().stackSize <= stack.stackSize)
					{
						inventory.setInventorySlotContents(2, inventory.decrStackSize(0, result.getInput().stackSize));
						this.progressMax = result.time;
					}
				}
			}

			if (progressMax <= 0) gotoSleep();
		}
		super.updateAwakeMachine(_state, _energyStorage, inventory);
	}

	@Override
	public void doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				resetProgress();
				final ItemStack stack = inventory.getStackInSlotOnClosing(2);
				if (stack != null)
				{
					final CompactingResult result = YATMApi.instance().compacting().getCompacting(stack);
					if (result != null)
					{
						final ItemStack discarded = new InventorySlice(inventory, new int[] { 1 }).mergeStackBang(result.asStack());
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

	protected void updateMachineNotEnoughPower(MachineUpdateState state, EnergyStorage energyStorage, IInventory inventory)
	{
		// Compactors lose progress if they are unpowered, or have insufficient power
		if (this.progress > 0)
		{
			this.progress--;
		}
	}
}
