/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package io.polyfox.yatm.common.inventory;

import growthcraft.api.core.nbt.INBTSerializable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class InternalInventoryCrate implements INBTSerializable
{
	protected int maxSize;
	protected int currentSize;
	protected ItemStack itemStack;

	public InternalInventoryCrate(int p_size)
	{
		this.maxSize = p_size;
		this.currentSize = 0;
	}

	public boolean isEmpty()
	{
		return currentSize <= 0;
	}

	public boolean hasContent()
	{
		return currentSize >= 0;
	}

	public boolean isFull()
	{
		return currentSize >= maxSize;
	}

	public int getSize()
	{
		return currentSize;
	}

	public int getMaxSize()
	{
		return maxSize;
	}

	public void clear()
	{
		this.itemStack = null;
		this.currentSize = 0;
	}

	public void modifyStackSize(int amount)
	{
		if (itemStack != null)
		{
			this.currentSize = MathHelper.clamp_int(amount, 0, maxSize);
			if (currentSize == 0)
			{
				this.itemStack = null;
			}
			else
			{
				itemStack.stackSize = MathHelper.clamp_int(currentSize, 0, itemStack.getMaxStackSize());
			}
		}
	}

	public boolean canInsert(ItemStack stack)
	{
		if (itemStack == null)
		{
			if (stack == null) return false;
		}
		else
		{
			if (!itemStack.isItemEqual(stack)) return false;
		}
		return true;
	}

	/**
	 * Returns the amount if the item was inserted, 0 otherwise
	 */
	public int insert(ItemStack stack)
	{
		if (stack == null) return 0;
		if (itemStack == null)
		{
			this.itemStack = stack.copy();
			this.currentSize = stack.stackSize;
			return currentSize;
		}
		else
		{
			if (itemStack.isItemEqual(stack))
			{
				final int oldSize = currentSize;
				modifyStackSize(currentSize + stack.stackSize);
				return currentSize - oldSize;
			}
		}
		return 0;
	}

	public ItemStack getItemStack(int size)
	{
		if (itemStack != null)
		{
			final ItemStack stack = itemStack.copy();
			stack.stackSize = MathHelper.clamp_int(currentSize, 0, size);
			return stack;
		}
		return null;
	}

	public ItemStack getItemStack()
	{
		return itemStack;
	}

	public ItemStack extract(int size)
	{
		final ItemStack stack = getItemStack(size);
		if (stack != null)
		{
			modifyStackSize(-size);
		}
		return stack;
	}

	public ItemStack extract()
	{
		if (itemStack != null)
		{
			return extract(itemStack.stackSize);
		}
		return null;
	}

	/**
	 * Decrease the internal stack size
	 */
	public int decr(int amount)
	{
		final int oldSize = currentSize;
		modifyStackSize(-amount);
		return oldSize - currentSize;
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		if (data.hasKey("item"))
		{
			this.itemStack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("item"));
		}
		this.currentSize = data.getInteger("size");
	}

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		data.setInteger("size", currentSize);
		if (itemStack != null)
		{
			final NBTTagCompound itemTag = new NBTTagCompound();
			itemStack.writeToNBT(itemTag);
			data.setTag("item", itemTag);
		}
	}
}
