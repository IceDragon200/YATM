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

import cofh.api.energy.EnergyStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractMachine implements IMachineLogic
{
	protected Random rand = new Random();

	@Override
	public int getRunningPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 0;
	}

	@Override
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 10;
	}

	@Override
	public boolean canWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return true;
	}

	@Override
	public int doWork(EnergyStorage energyStorage, IInventory inventory)
	{
		return 0;
	}

	protected abstract void readFromNBT(NBTTagCompound data);

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

	protected abstract void writeToNBT(NBTTagCompound data);

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
}
