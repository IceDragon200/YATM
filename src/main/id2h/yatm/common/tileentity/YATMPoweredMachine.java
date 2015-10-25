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
package id2h.yatm.common.tileentity;

import id2h.yatm.common.tileentity.machine.IMachineLogic;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

public abstract class YATMPoweredMachine extends YATMPoweredTile implements ISidedInventory
{
	protected IInventory inventory;
	protected IMachineLogic machine;

	public YATMPoweredMachine()
	{
		super();
		this.inventory = createInventory();
		this.machine = createMachine();
	}

	public abstract IInventory createInventory();
	public abstract IMachineLogic createMachine();

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		return inventory.decrStackSize(index, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		return inventory.getStackInSlotOnClosing(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inventory.setInventorySlotContents(index, stack);
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return inventory.hasCustomInventoryName();
	}

	@Override
	public String getInventoryName()
	{
		return inventory.getInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory()
	{
		inventory.openInventory();
	}

	@Override
	public void closeInventory()
	{
		inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return inventory.isItemValidForSlot(index, stack);
	}

	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] {0};
	}

	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	public int getRunningPowerCost()
	{
		return machine.getRunningPowerCost(energyStorage, inventory);
	}

	public int getWorkingPowerCost()
	{
		return machine.getWorkingPowerCost(energyStorage, inventory);
	}

	public boolean canWork()
	{
		return machine.canWork(energyStorage, inventory);
	}

	public int doWork()
	{
		return machine.doWork(energyStorage, inventory);
	}

	public void updateMachine()
	{
		int consumed = getRunningPowerCost();
		if (canWork())
		{
			consumed += getWorkingPowerCost();
			if (energyStorage.getEnergyStored() >= consumed)
			{
				consumed += doWork();
			}
		}
		if (consumed != 0)
		{
			energyStorage.extractEnergy(consumed, false);
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		updateMachine();
	}
}
