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

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		if (Platform.isClient())
		{
			return null;
		}

		final Slot s = getSlot(index);
		ItemStack itemstack = null;

		if (s != null && s.getHasStack())
		{
			final ItemStack stack = s.getStack();
			itemstack = stack.copy();

			boolean wasMerged = false;
			for (Object sub : inventorySlots)
			{
				if (sub instanceof SlotPlayer)
				{
					final SlotPlayer subSlot = (SlotPlayer)sub;
					if (subSlot.isItemValid(stack))
					{
						if (mergeItemStack(stack, subSlot.getSlotIndex(), subSlot.getSlotIndex() + 1, false))
						{
							wasMerged = true;
						}
						if (stack.stackSize <= 0) break;
					}
				}
			}
			if (wasMerged)
			{
				s.onSlotChange(stack, itemstack);
			}
			else
			{
				return null;
			}

			/*
			if (s instanceof SlotPlayer)
			{
				return null;
			}
			else
			{
				boolean wasMerged = false;
				for (Object sub : inventorySlots)
				{
					if (sub instanceof SlotPlayer)
					{
						final SlotPlayer subSlot = (SlotPlayer)sub;
						if (subSlot.isItemValid(stack))
						{
							if (mergeItemStack(stack, subSlot.getSlotIndex(), subSlot.getSlotIndex() + 1, false))
							{
								wasMerged = true;
							}
							if (stack.stackSize <= 0) break;
						}
					}
				}
				if (!wasMerged) return null;
			}*/

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
