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
package id2h.yatm.common.inventory;

import javax.annotation.Nonnull;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

// Utility class for moving item stacks from their input to their processing
// slots
public class InventoryProcessor
{
	private static final InventoryProcessor	inst = new InventoryProcessor();

	private InventoryProcessor() {}

	public boolean slotsAreEmpty(@Nonnull IInventory inv, int[] slots)
	{
		for (int slot : slots)
		{
			if (inv.getStackInSlot(slot) != null) return false;
		}
		return true;
	}

	public boolean mergeWithSlot(@Nonnull IInventory inv, ItemStack item, int slot)
	{
		if (item == null) return false;
		if (item.stackSize <= 0) return false;

		final ItemStack existing = inv.getStackInSlot(slot);
		if (existing == null || existing.stackSize <= 0)
		{
			inv.setInventorySlotContents(slot, item.copy());
			item.stackSize = 0;
		}
		else
		{
			if (existing.isItemEqual(item))
			{
				final int newSize = MathHelper.clamp_int(existing.stackSize + item.stackSize, 0, existing.getMaxStackSize());
				if (newSize == existing.stackSize)
				{
					return false;
				}
				else
				{
					final int consumed = newSize - existing.stackSize;
					item.stackSize -= consumed;
					existing.stackSize = newSize;
					inv.setInventorySlotContents(slot, existing);
				}
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	public boolean mergeWithSlots(@Nonnull IInventory inv, ItemStack stack, int[] slots)
	{
		if (stack == null) return false;

		boolean anythingMerged = false;
		for (int slot : slots)
		{
			if (stack.stackSize <= 0) break;
			anythingMerged |= mergeWithSlot(inv, stack, slot);
		}
		return anythingMerged;
	}

	public ItemStack mergeWithSlots(@Nonnull IInventory inv, ItemStack stack)
	{
		if (stack == null) return null;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			if (stack.stackSize <= 0) break;
			mergeWithSlot(inv, stack, i);
		}
		return stack.stackSize <= 0 ? null : stack;
	}

	public boolean clearSlots(@Nonnull IInventory inv, int[] src)
	{
		boolean clearedAnything = false;
		for (int slot : src)
		{
			clearedAnything |= inv.getStackInSlotOnClosing(slot) != null;
		}
		return clearedAnything;
	}

	public boolean checkSlot(@Nonnull IInventory inv, ItemStack expected, int src)
	{
		final ItemStack actual = inv.getStackInSlot(src);

		if (expected == null)
		{
			// if the item is not needed, and is not available
			if (actual != null) return false;
		}
		else
		{
			if (actual == null) return false;
			if (!expected.isItemEqual(actual)) return false;
			if (actual.stackSize < expected.stackSize) return false;
		}
		return true;
	}

	public boolean checkSlots(@Nonnull IInventory inv, @Nonnull ItemStack[] filter, int[] src)
	{
		assert filter.length == src.length;

		for (int i = 0; i < filter.length; ++i)
		{
			if (!checkSlot(inv, filter[i], src[i])) return false;
		}
		return true;
	}

	/**
	 * @return true if moved, false otherwise
	 */
	public boolean moveToSlots(@Nonnull IInventory inv, @Nonnull ItemStack[] filter, int[] src, int[] dest)
	{
		assert filter.length == src.length;
		assert filter.length == dest.length;

		// first ensure that each stack in the input has the item and enough of them
		if (!checkSlots(inv, filter, src)) return false;

		for (int i = 0; i < filter.length; ++i)
		{
			if (filter[i] != null)
			{
				final ItemStack stack = inv.decrStackSize(src[i], filter[i].stackSize);
				inv.setInventorySlotContents(dest[i], stack);
			}
		}
		return true;
	}

	public boolean moveToSlot(@Nonnull IInventory inv, @Nonnull ItemStack filter, int src, int dest)
	{
		// first ensure that each stack in the input has the item and enough of them
		if (!checkSlot(inv, filter, src)) return false;

		final ItemStack stack = inv.decrStackSize(src, filter.stackSize);
		inv.setInventorySlotContents(dest, stack);

		return true;
	}

	public static InventoryProcessor instance()
	{
		return inst;
	}
}
