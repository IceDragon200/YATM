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

import cofh.api.energy.EnergyStorage;
import id2h.yatm.common.tileentity.inventory.InventorySlice;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.ItemStack;

public class MachineFluxFurnace extends AbstractProgressiveMachine
{
	@Override
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 50;
	}

	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (progressMax > 0) return true;
		for (int i = 0; i < 4; ++i)
		{
			if (inventory.getStackInSlot(i) != null) return true;
			if (inventory.getStackInSlot(8 + i) != null) return true;
		}
		return false;
	}

	@Override
	public int doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		if (progressMax <= 0)
		{
			resetProgress();

			for (int i = 0; i < 4; ++i)
			{
				final ItemStack inp = inventory.getStackInSlot(i);
				if (inp != null)
				{
					final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inp);

					if (itemstack != null)
					{
						inventory.setInventorySlotContents(8 + i, inventory.decrStackSize(i, 1));
						this.progressMax += 100;
					}
				}
			}
			this.progressMax *= 0.9f;
		}

		if (progressMax > 0)
		{
			if (progress >= progressMax)
			{
				final InventorySlice outputInv = new InventorySlice(inventory, new int[] { 4, 5, 6, 7 });
				for (int i = 0; i < 4; ++i)
				{
					final ItemStack inp = inventory.getStackInSlot(8 + i);
					if (inp != null)
					{
						final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inp);
						final ItemStack rem = outputInv.mergeStack(itemstack);

						if (rem != null)
						{
							// EJECT
							System.out.println("Ejecting remaining stack=" + rem);
						}
					}
					inventory.setInventorySlotContents(8 + i, null);
				}
				resetProgress();
			}
			else
			{
				this.progress += 1;
			}
		}
		return 0;
	}
}
