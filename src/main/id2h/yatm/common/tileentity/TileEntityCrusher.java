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
import id2h.yatm.common.tileentity.energy.MachineEnergyStorage;
import id2h.yatm.common.inventory.IYATMInventory;
import id2h.yatm.common.inventory.YATMInternalInventory;
import id2h.yatm.common.tileentity.machine.IMachineLogic;
import id2h.yatm.common.tileentity.machine.MachineCrusher;
import growthcraft.api.core.util.NumUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;

/*
 * A Crusher applies pressure to a crack, smashing it into smaller pieces,
 * it can be used to shard certain blocks, or reduce stone to cobble, cobble
 * to gravel and gravel to sand.
 */
public class TileEntityCrusher extends YATMPoweredMachine
{
	protected static final int[][] slotTable = {
		{ 0 },
		{ 0 },
		{ 1, 2, 3, 4 },
		{ 3, 4, 1, 2 },
		{ 4, 1, 2, 3 },
		{ 2, 3, 4, 1 },
	};

	@SideOnly(Side.CLIENT)
	public ItemStack getCrushingBlock()
	{
		final ItemStack stack = inventory.getStackInSlot(6);
		if (stack != null)
		{
			if (stack.getItem() instanceof ItemBlock)
			{
				return stack;
			}
		}
		return null;
	}

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new MachineEnergyStorage(16000, 50);
	}

	@Override
	protected IYATMInventory createInventory()
	{
		/*
		 * Slots are reserved as such:
		 * 0 - :Input
		 * 1 - :Output 0
		 * 2 - :Output 1
		 * 3 - :Output 2
		 * 4 - :Output 3
		 * 5 - RESERVED
		 * 6 - :Processing
		 */
		return new YATMInternalInventory(this, 7);
	}

	@Override
	protected IMachineLogic createMachine()
	{
		return new MachineCrusher();
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
		if (NumUtils.between(index, 1, 4))
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
