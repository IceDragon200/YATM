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

import growthcraft.core.common.inventory.GrcInternalInventory;
import growthcraft.core.common.inventory.InventoryProcessor;
import growthcraft.core.common.tileentity.GrcTileEntityInventoryBase;
import io.polyfox.yatm.common.inventory.ContainerCrate;
import io.polyfox.yatm.common.inventory.YATMInternalInventory;
import growthcraft.core.common.tileentity.feature.IInteractionObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class TileEntityCrate extends GrcTileEntityInventoryBase implements IInteractionObject
{
	private static final int[] primarySlots = new int[] { 0 };

	@Override
	protected GrcInternalInventory createInventory()
	{
		/**
		 * Crates hold 2 chests worth of 1 item type
		 */
		return new YATMInternalInventory(this, 1, 54 * 64).setInventoryName("yatm.inventory.crate");
	}

	@Override
	public String getGuiID()
	{
		return "yatm:crate";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerCrate(playerInventory, this);
	}

	@Override
	public String getDefaultInventoryName()
	{
		return inventory.getInventoryName();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return primarySlots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		if (index != 0) return false;
		return InventoryProcessor.instance().canInsertItem(this, stack, index);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		if (index != 0) return false;
		return InventoryProcessor.instance().canExtractItem(this, stack, index);
	}
}
