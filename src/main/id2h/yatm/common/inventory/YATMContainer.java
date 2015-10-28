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

import id2h.yatm.common.inventory.slot.SlotInput;
import id2h.yatm.common.inventory.slot.SlotPlayer;
import id2h.yatm.common.inventory.slot.SlotPlayerHotbar;
import id2h.yatm.common.inventory.slot.SlotPlayerBackpack;

import appeng.util.Platform;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class YATMContainer extends Container
{
	protected static final int SLOT_W = 18;
	protected static final int SLOT_H = 18;

	public boolean mergeWithSlot(Slot slot, ItemStack stack)
	{
		if (slot.isItemValid(stack))
		{
			if (mergeItemStack(stack, slot.slotNumber, slot.slotNumber + 1, false))
			{
				return true;
			}
		}
		return false;
	}

	public boolean mergeWithPlayer(ItemStack stack)
	{
		boolean wasMerged = false;
		for (Object sub : inventorySlots)
		{
			if (sub instanceof SlotPlayer)
			{
				final SlotPlayer subSlot = (SlotPlayer)sub;
				wasMerged |= mergeWithSlot(subSlot, stack);
			}
			if (stack.stackSize <= 0) break;
		}
		return wasMerged;
	}

	public boolean mergeWithInput(ItemStack stack)
	{
		boolean wasMerged = false;
		for (Object sub : inventorySlots)
		{
			if (sub instanceof SlotInput)
			{
				final SlotInput subSlot = (SlotInput)sub;
				wasMerged |= mergeWithSlot(subSlot, stack);
			}
			if (stack.stackSize <= 0) break;
		}
		return wasMerged;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		if (Platform.isClient())
		{
			return null;
		}

		final Slot s = getSlot(index);
		ItemStack itemstack = null;

		System.out.println("Slot shift clicked slot=" + s + " index=" + index);

		if (s != null && s.getHasStack())
		{
			final ItemStack stack = s.getStack();
			itemstack = stack.copy();

			boolean wasMerged = false;

			if (s instanceof SlotPlayer)
			{
				wasMerged |= mergeWithInput(stack);
			}
			else
			{
				wasMerged |= mergeWithPlayer(stack);
			}

			if (wasMerged)
			{
				s.onSlotChange(stack, itemstack);
			}
			else
			{
				return null;
			}

			if (stack.stackSize <= 0)
			{
				s.putStack((ItemStack)null);
			}
			else
			{
				s.onSlotChanged();
			}

			if (stack.stackSize == itemstack.stackSize)
			{
				return null;
			}

			s.onPickupFromSlot(player, stack);
		}
		return itemstack;
	}

	public void bindPlayerHotbar(IInventory playerInventory, int x, int y)
	{
		for (int i = 0; i < 9; ++i)
		{
			addSlotToContainer(new SlotPlayerHotbar(playerInventory, i, x + i * SLOT_W, y));
		}
	}

	public void bindPlayerBackpack(IInventory playerInventory, int x, int y)
	{
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				final int slotIndex = 9 + col + row * 9;
				addSlotToContainer(new SlotPlayerBackpack(playerInventory, slotIndex, x + col * SLOT_W, y + row * SLOT_H));
			}
		}
	}

	public void bindPlayerInventory(IInventory playerInventory, int x, int y)
	{
		bindPlayerBackpack(playerInventory, x, y);
		bindPlayerHotbar(playerInventory, x, y + 58);
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}
