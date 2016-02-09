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
import id2h.yatm.common.tileentity.feature.IInventoryWatcher;
import growthcraft.api.core.util.NumUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.ItemStack;

public class MachineFluxFurnace extends AbstractProgressiveMachine implements IInventoryWatcher
{
	@Override
	public void onInventoryChanged(IInventory inventory, int index)
	{
		if (index < 0 || NumUtils.between(index, 0, 3))
		{
			awake();
		}
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

			// set each processing slot
			for (int i = 0; i < 4; ++i)
			{
				// each input slot
				for (int j = 0; j < 4; ++j)
				{
					final ItemStack inp = state.inventory.getStackInSlot(j);
					if (inp != null)
					{
						final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inp);

						if (itemstack != null)
						{
							state.inventory.setInventorySlotContents(8 + i, state.inventory.decrStackSize(j, 1));
							this.progressMax += 80;
							break;
						}
					}
				}
			}
			if (progressMax <= 0)
			{
				gotoSleep();
			}
			else
			{
				this.progressMax *= 0.8f;
			}
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
				final InventorySlice outputInv = new InventorySlice(state.inventory, new int[] { 4, 5, 6, 7 });
				for (int i = 0; i < 4; ++i)
				{
					final ItemStack inp = state.inventory.getStackInSlot(8 + i);
					if (inp != null)
					{
						final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inp);
						final ItemStack rem = outputInv.mergeStack(itemstack);

						if (rem != null) discardItemStack(rem);
					}
					state.inventory.setInventorySlotContents(8 + i, null);
				}
			}
			else
			{
				this.progress += 1;
			}
		}
	}
}
