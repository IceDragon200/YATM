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
package id2h.yatm.common.tileentity.machine;

import java.util.Random;

import io.netty.buffer.ByteBuf;

import growthcraft.core.common.inventory.InventoryProcessor;
import id2h.yatm.util.YATMDebug;

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

	public boolean readFromStream(ByteBuf stream)
	{
		return false;
	}

	public void writeToStream(ByteBuf stream)
	{

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
		YATMDebug.writeMachineState("Machine has gone idle machine=" + this);
	}

	@Override
	public void awake()
	{
		if (sleeping)
		{
			YATMDebug.writeMachineState("Machine has awoken machine=" + this);
		}
		sleeping = false;
	}

	protected void gotoSleep()
	{
		if (!sleeping)
		{
			YATMDebug.writeMachineState("Machine has gone to sleep machine=" + this);
		}
		sleeping = true;
	}

	protected void updateMachineNotEnoughPower(MachineUpdateState state)
	{

	}

	protected void updateMachineForWork(MachineUpdateState state)
	{
		final int workingCost = getWorkingPowerCost(state);
		if (state.energyStorage.getEnergyStored() >= (state.energyConsumed + workingCost))
		{
			if (canWork(state))
			{
				state.energyConsumed += workingCost;
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
		state.energyConsumed += getRunningPowerCost(state);
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
		YATMDebug.write("TODO: Discard itemstack stack=" + stack);
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
