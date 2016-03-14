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

import id2h.yatm.api.roller.RollingResult;
import id2h.yatm.api.YATMApi;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.api.core.util.TickUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MachineRoller extends AbstractProgressiveMachine implements IInventoryWatcher
{
	protected static int[] inputSlotIds = new int[] { 0 };
	protected static int[] outputSlotIds = new int[] { 1 };
	protected static int[] processingSlotIds = new int[] { 2 };

	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index <= 0 || index == 0)
		{
			awake();
		}
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
	}

	public RollingResult getSlotResult(MachineUpdateState state, int slot)
	{
		return YATMApi.instance().rolling().getRolling(state.inventory.getStackInSlot(slot));
	}

	public RollingResult getInputResult(MachineUpdateState state)
	{
		return getSlotResult(state, inputSlotIds[0]);
	}

	public RollingResult getProcessingResult(MachineUpdateState state)
	{
		return getSlotResult(state, processingSlotIds[0]);
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 10;
	}

	@Override
	public void updateAwakeMachine(MachineUpdateState state)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			final RollingResult result = getInputResult(state);

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
				resetProgress();

				final RollingResult result = getProcessingResult(state);

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
