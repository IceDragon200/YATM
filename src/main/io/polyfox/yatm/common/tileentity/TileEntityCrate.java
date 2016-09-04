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
package io.polyfox.yatm.common.tileentity;

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.core.common.tileentity.event.EventHandler;
import io.polyfox.yatm.common.inventory.InternalInventoryCrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCrate extends YATMTileBase implements IInventory, INBTItemSerializable
{
	private static final int[] PRIMARY_SLOTS = new int[] { 0 };
	protected InternalInventoryCrate inventory = new InternalInventoryCrate(4096);

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	public int insertItem(ItemStack stack)
	{
		return inventory.insert(stack);
	}

	public ItemStack extractItem(int amount)
	{
		return inventory.extract(amount);
	}

	public ItemStack extractItem()
	{
		return inventory.extract();
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex)
	{
		if (slotIndex == 0)
		{
			return inventory.getItemStack();
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount)
	{
		if (slotIndex == 0)
		{
			ItemStack stack = inventory.getItemStack();
			if (stack != null)
			{
				final int pulled = inventory.decr(amount);
				stack = stack.copy();
				stack.stackSize = pulled;
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack stack) {}

	@Override
	public String getInventoryName()
	{
		return "yatm.inventory.crate";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		// Add Security header and check against it
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack stack)
	{
		if (slotIndex == 0)
		{
			return inventory.canInsert(stack);
		}
		return false;
	}

	protected void readInventoryFromNBT(NBTTagCompound data)
	{
		if (data.hasKey("inventory"))
		{
			inventory.readFromNBT(data.getCompoundTag("inventory"));
		}
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound data)
	{
		super.readFromNBTForItem(data);
		readInventoryFromNBT(data);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_Crate(NBTTagCompound data)
	{
		readInventoryFromNBT(data);
	}

	protected void writeInventoryToNBT(NBTTagCompound data)
	{
		final NBTTagCompound inventoryTag = new NBTTagCompound();
		inventory.writeToNBT(inventoryTag);
		data.setTag("inventory", inventoryTag);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound data)
	{
		super.writeToNBTForItem(data);
		writeInventoryToNBT(data);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_Crate(NBTTagCompound data)
	{
		writeInventoryToNBT(data);
	}
}
