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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.PulseStepper;
import growthcraft.core.common.inventory.GrcInternalInventory;
import growthcraft.core.common.tileentity.device.DeviceInventorySlot;
import growthcraft.core.common.tileentity.feature.IItemHandler;
import growthcraft.core.common.tileentity.GrcTileEntityInventoryBase;
import growthcraft.core.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityItemReplicator extends GrcTileEntityInventoryBase implements IItemHandler
{
	private DeviceInventorySlot itemSlotSource;
	private DeviceInventorySlot itemSlotDest;
	private PulseStepper workPulsar = new PulseStepper(20, 0);

	public TileEntityItemReplicator()
	{
		super();
		this.itemSlotSource = new DeviceInventorySlot(this, 0);
		this.itemSlotDest = new DeviceInventorySlot(this, 1);
	}

	@Override
	protected GrcInternalInventory createInventory()
	{
		return new GrcInternalInventory(this, 2);
	}

	protected void setState(boolean newState)
	{
		final int meta = getBlockMetadata()	& 7;
		final boolean oldState = (meta & 4) > 0;
		if (oldState != newState)
		{
			final int curMeta = (meta & 3) | (newState ? 4 : 0);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, curMeta, BlockFlags.UPDATE_AND_SYNC);
		}
		markDirty();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			if (workPulsar.update() == PulseStepper.State.PULSE)
			{
				if (itemSlotSource.hasContent())
				{
					if (!itemSlotDest.isFull())
					{
						itemSlotDest.increaseStack(itemSlotSource.get());
					}
					setState(true);
				}
				else
				{
					setState(false);
				}
			}
		}
	}

	@Override
	public boolean tryPlaceItem(@Nonnull EntityPlayer player, @Nullable ItemStack stack)
	{
		if (stack != null)
		{
			if (!itemSlotSource.hasContent())
			{
				itemSlotSource.set(stack);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean tryTakeItem(@Nonnull EntityPlayer player, @Nullable ItemStack stack)
	{
		if (stack == null)
		{
			if (itemSlotSource.hasContent())
			{
				ItemUtils.addStackToPlayer(itemSlotSource.yank(), player, worldObj, xCoord, yCoord, zCoord, false);
				return true;
			}
		}
		return false;
	}
}
