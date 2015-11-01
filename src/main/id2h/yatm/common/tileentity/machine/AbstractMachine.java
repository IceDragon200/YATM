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

import id2h.yatm.util.YATMDebug;

import cofh.api.energy.EnergyStorage;

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
	public int getRunningPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 0;
	}

	@Override
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 30;
	}

	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return true;
	}

	@Override
	public void doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		gotoSleep();
	}

	protected void goIdle()
	{
		idleTime = 60;
		YATMDebug.writeMachineState("Machine has gone idle machine=" + this);
	}

	protected void awake()
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

	protected void updateMachineNotEnoughPower(MachineUpdateState state, EnergyStorage energyStorage, IInventory inventory)
	{

	}

	protected void updateMachineForWork(MachineUpdateState state, EnergyStorage energyStorage, IInventory inventory)
	{
		final int workingCost = getWorkingPowerCost(energyStorage, inventory);
		if (energyStorage.getEnergyStored() >= (state.energyConsumed + workingCost))
		{
			if (canWork(energyStorage, inventory))
			{
				state.energyConsumed += workingCost;
				doWork(energyStorage, inventory);
				state.didWork |= true;
			}
		}
		else
		{
			updateMachineNotEnoughPower(state, energyStorage, inventory);
		}
	}

	protected void updateAwakeMachine(MachineUpdateState state, EnergyStorage energyStorage, IInventory inventory)
	{
		if (idleTime <= 0)
		{
			updateMachineForWork(state, energyStorage, inventory);
		}
		else
		{
			idleTime--;
		}
	}

	@Override
	public void updateMachine(MachineUpdateState state, EnergyStorage energyStorage, IInventory inventory)
	{
		state.energyConsumed += getRunningPowerCost(energyStorage, inventory);
		if (!sleeping)
		{
			updateAwakeMachine(state, energyStorage, inventory);
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
}
