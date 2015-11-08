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
package id2h.yatm.common.tileentity.energy;

import cofh.api.energy.EnergyStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class YATMEnergyStorage extends EnergyStorage
{
	public YATMEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		super(capacity, maxReceive, maxExtract);
	}

	public YATMEnergyStorage(int capacity, int maxTransfer)
	{
		this(capacity, maxTransfer, maxTransfer);
	}

	public YATMEnergyStorage(int capacity)
	{
		this(capacity, capacity, capacity);
	}

	public int modifyEnergyStoredDiff(int en)
	{
		this.energy += en;

		if (this.energy > capacity)
		{
			final int newEnergy = MathHelper.clamp_int(energy, 0, capacity);
			final int diff = newEnergy - energy;
			this.energy = newEnergy;
			return diff;
		}
		else if (this.energy < 0)
		{
			this.energy = 0;
			return 0;
		}
		return en;
	}

	public void readFromNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound tag = data.getCompoundTag(name);
		if (tag != null)
		{
			readFromNBT(tag);
		}
		else
		{
			// LOG error
		}
	}

	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		data.setTag(name, tag);
	}
}
