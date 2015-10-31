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

import java.util.Iterator;

import id2h.yatm.common.tileentity.feature.IInventoryWatcher;
import id2h.yatm.util.YATMDebug;

import appeng.util.iterators.InvIterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class YATMInternalInventory implements IYATMInventory, Iterable<ItemStack>
{
	protected ItemStack[] items;
	protected int maxSize;
	protected int maxStackSize;
	protected IInventory parent;

	public YATMInternalInventory(IInventory par, int size)
	{
		this.parent = par;
		this.maxStackSize = 64;
		this.maxSize = size;
		this.items = new ItemStack[maxSize];
	}

	@Override
	public Iterator<ItemStack> iterator()
	{
		return new InvIterator(this);
	}

	public int getMaxSize()
	{
		return maxSize;
	}

	@Override
	public void markDirty()
	{
		YATMDebug.write("Inventory marked as dirty inv=" + this);
		onSlotChanged(-1);
	}

	protected void onSlotChanged(int index)
	{
		if (parent instanceof IInventoryWatcher)
		{
			((IInventoryWatcher)parent).onInventoryChanged(this, index);
		}
		else
		{
			parent.markDirty();
		}
	}

	public void clearInventory()
	{
		for (int i = 0; i < getMaxSize(); ++i)
		{
			items[i] = null;
		}
		onSlotChanged(-1);
	}

	public void readFromNBT(NBTTagCompound data)
	{
		for (int i = 0; i < getMaxSize(); ++i)
		{
			final NBTTagCompound itemTag = data.getCompoundTag("Slot" + i);

			if (itemTag != null)
			{
				items[i] = ItemStack.loadItemStackFromNBT(itemTag);
			}
		}
		onSlotChanged(-1);
	}

	public void readFromNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound invData = data.getCompoundTag(name);
		if (invData != null)
		{
			readFromNBT(invData);
		}
		else
		{
			// LOG error
		}
	}

	public void writeToNBT(NBTTagCompound data)
	{
		for (int i = 0; i < getMaxSize(); ++i)
		{
			if (items[i] != null)
			{
				final NBTTagCompound itemTag = new NBTTagCompound();
				items[i].writeToNBT(itemTag);
				data.setTag("Slot" + i, itemTag);
			}
		}
	}

	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound invData = new NBTTagCompound();
		writeToNBT(invData);
		data.setTag(name, invData);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer _player)
	{
		return true;
	}

	@Override
	public int getSizeInventory()
	{
		return maxSize;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return maxStackSize;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public String getInventoryName()
	{
		return "yatm.inventory.internal";
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return items[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		items[index] = stack;
		if (stack != null)
		{
			if (stack.stackSize > getInventoryStackLimit())
			{
				final int discarded = stack.stackSize - getInventoryStackLimit();
				items[index].stackSize = getInventoryStackLimit();
				if (discarded > 0)
				{
					// TODO: do something with the discarded stack
					System.out.println("Some items have been discarded, item=" + stack.toString() + " discarded=" + discarded);
				}
			}
		}
		onSlotChanged(index);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		final ItemStack stack = items[index];
		items[index] = null;
		onSlotChanged(index);
		return stack;
	}

	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		if (items[index] != null)
		{
			ItemStack itemstack;

			if (items[index].stackSize <= amount)
			{
				itemstack = items[index];
				items[index] = null;
			}
			else
			{
				itemstack = items[index].splitStack(amount);

				if (items[index].stackSize <= 0)
				{
					items[index] = null;
				}
			}
			onSlotChanged(index);
			return itemstack;
		}
		return null;
	}
}
