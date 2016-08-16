/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package io.polyfox.yatm.common.tileentity;

import io.polyfox.yatm.common.inventory.ContainerRoller;
import io.polyfox.yatm.common.inventory.IYATMInventory;
import io.polyfox.yatm.common.inventory.YATMInternalInventory;
import io.polyfox.yatm.common.tileentity.energy.MachineEnergyStorage;
import io.polyfox.yatm.common.tileentity.energy.YATMEnergyStorage;
import io.polyfox.yatm.common.tileentity.machine.IMachineLogic;
import io.polyfox.yatm.common.tileentity.machine.MachineRoller;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class TileEntityRoller extends YATMPoweredMachine
{
	protected static final int[][] slotTable = {
		{ 0 },
		{ 0 },
		{ 1 },
		{ 1 },
		{ 1 },
		{ 1 },
	};

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new MachineEnergyStorage(14000, 100);
	}

	@Override
	protected IYATMInventory createInventory()
	{
		/*
		 * 0 - :Input
		 * 1 - :Output
		 * 2 - :Processing
		 * 3 - RESERVED
		 */
		return new YATMInternalInventory(this, 4);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:roller";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerRoller(playerInventory, this);
	}

	@Override
	protected IMachineLogic createMachine()
	{
		return new MachineRoller();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return slotTable[side];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		if (index == 0)
		{
			if (side == 0 || side == 1)
			{
				final ItemStack existing = inventory.getStackInSlot(index);
				if (existing == null) return true;
				return existing.isItemEqual(stack);
			}
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		if (index == 1)
		{
			if (side > 1)
			{
				final ItemStack existing = inventory.getStackInSlot(index);
				if (existing == null) return false;
				return true;
			}
		}
		return false;
	}
}
