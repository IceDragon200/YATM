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

import id2h.yatm.common.tileentity.energy.YATMEnergyStorage;
import id2h.yatm.common.tileentity.machine.IMachineLogic;
import id2h.yatm.common.tileentity.machine.MachineMixer;
import id2h.yatm.common.inventory.IYATMInventory;
import id2h.yatm.common.inventory.YATMInternalInventory;
import id2h.yatm.util.NumUtils;

import net.minecraft.item.ItemStack;

public class TileEntityMixer extends YATMPoweredMachine
{
	protected static final int[][] slotTable = {
		{ 0 },
		{ 0 },
		{ 1, 2, 3, 4 },
		{ 3, 4, 1, 2 },
		{ 4, 1, 2, 3 },
		{ 2, 3, 4, 1 },
	};

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(4000, 10);
	}

	@Override
	protected IYATMInventory createInventory()
	{
		/*
		 * 0 - :Output
		 * 1 - :Input Top
		 * 2 - :Input Right
		 * 3 - :Input Bottom
		 * 4 - :Input Left
		 * 5 - :Processing :Input Top
		 * 6 - :Processing :Input Right
		 * 7 - :Processing :Input Bottom
		 * 8 - :Processing :Input Left
		 */
		return new YATMInternalInventory(this, 9);
	}

	@Override
	protected IMachineLogic createMachine()
	{
		return new MachineMixer();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return slotTable[side];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		if (NumUtils.between(index, 1, 4))
		{
			if (side > 1)
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
		if (index == 0)
		{
			if (side == 0 || side == 1)
			{
				final ItemStack existing = inventory.getStackInSlot(index);
				if (existing == null) return false;
				return true;
			}
		}
		return false;
	}
}
