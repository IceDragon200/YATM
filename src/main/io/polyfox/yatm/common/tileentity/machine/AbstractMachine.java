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
package io.polyfox.yatm.common.tileentity.machine;

import java.util.Random;

import io.netty.buffer.ByteBuf;

import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.core.common.inventory.InventoryProcessor;
import growthcraft.core.util.ItemUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class AbstractMachine implements IMachineLogic
{
	protected Random rand = new Random();
	protected TileEntity tileEntity;
	protected int idleTime;
	protected boolean sleeping;
	protected InventoryProcessor inventoryProcessor = InventoryProcessor.instance();

	// Serialization
	protected void readFromNBT(NBTTagCompound data)
	{
		idleTime = data.getInteger("idle");
		sleeping = data.getBoolean("sleeping");
	}

	@Override
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

	protected void writeToNBT(NBTTagCompound data)
	{
		data.setInteger("idle", idleTime);
		data.setBoolean("sleeping", sleeping);
	}

	@Override
	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound invData = new NBTTagCompound();
		writeToNBT(invData);
		data.setTag(name, invData);
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		return false;
	}

	@Override
	public boolean writeToStream(ByteBuf stream)
	{
		return false;
	}

	// Machine
	@Override
	public int getRunningPowerCost(MachineUpdateState state)
	{
		return 0;
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 30;
	}

	@Override
	public int getWorkingThreshold(MachineUpdateState state)
	{
		return getWorkingPowerCost(state);
	}

	@Override
	public boolean canWork(MachineUpdateState state)
	{
		return true;
	}

	@Override
	public void doWork(MachineUpdateState state)
	{
		gotoSleep();
	}

	protected void goIdle(int duration)
	{
		idleTime = duration;
	}

	@Override
	public void awake()
	{
		this.sleeping = false;
	}

	protected void gotoSleep()
	{
		this.sleeping = true;
	}

	protected void updateMachineNotEnoughPower(MachineUpdateState state)
	{

	}

	protected void updateMachineForWork(MachineUpdateState state)
	{
		final int workingCost = getWorkingPowerCost(state);
		final int workingThreshold = getWorkingThreshold(state);
		if (state.powerStorage.getAmount() >= (state.powerConsumed + workingThreshold))
		{
			if (canWork(state))
			{
				state.powerConsumed += workingCost;
				doWork(state);
				state.didWork |= true;
			}
		}
		else
		{
			updateMachineNotEnoughPower(state);
		}
	}

	protected void updateAwakeMachine(MachineUpdateState state)
	{
		if (idleTime <= 0)
		{
			updateMachineForWork(state);
		}
		else
		{
			idleTime--;
		}
	}

	@Override
	public void updateMachine(MachineUpdateState state)
	{
		state.powerConsumed += getRunningPowerCost(state);
		if (!sleeping)
		{
			updateAwakeMachine(state);
		}
	}

	@Override
	public void setTileEntity(TileEntity te)
	{
		this.tileEntity = te;
	}

	public void discardItemStack(ItemStack stack)
	{
		if (tileEntity instanceof IInventoryWatcher)
		{
			if (tileEntity instanceof IInventory)
			{
				final IInventoryWatcher watcher = (IInventoryWatcher)tileEntity;
				watcher.onItemDiscarded((IInventory)tileEntity, stack, -1, stack.stackSize);
			}
			else
			{
				final ItemStack discarded = stack.copy();
				ItemUtils.spawnItemStack(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, discarded, tileEntity.getWorldObj().rand);
			}
		}
	}

	public void discardInventorySlots(MachineUpdateState state, int[] slots)
	{
		for (int slot : slots)
		{
			final ItemStack result = state.inventory.getStackInSlotOnClosing(slot);
			if (result != null)
			{
				discardItemStack(result);
			}
		}
	}
}
