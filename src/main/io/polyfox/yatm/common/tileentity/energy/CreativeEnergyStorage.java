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
package io.polyfox.yatm.common.tileentity.energy;

import net.minecraft.nbt.NBTTagCompound;

public class CreativeEnergyStorage extends YATMEnergyStorage
{
	public CreativeEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		super(capacity, maxReceive, maxExtract);
	}

	public CreativeEnergyStorage(int capacity, int maxTransfer)
	{
		this(capacity, maxTransfer, maxTransfer);
	}

	public CreativeEnergyStorage(int capacity)
	{
		this(capacity, capacity, capacity);
	}

	@Override
	public CreativeEnergyStorage readFromNBT(NBTTagCompound nbt)
	{
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		return nbt;
	}

	@Override
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}

	/**
	 * This function is included to allow for server -&gt; client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers
	 * are guaranteed to have it.
	 *
	 * @param energy - amount of energy that should be stored now
	 */
	@Override
	public void setEnergyStored(int energy)
	{

	}

	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the CreativeEnergyStorage.
	 * Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
	 *
	 * @param energy -
	 */
	@Override
	public void modifyEnergyStored(int energy)
	{

	}

	/* ICreativeEnergyStorage */
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		final int energyExtracted = Math.min(capacity, Math.min(this.maxExtract, maxExtract));
		return energyExtracted;
	}

	@Override
	public int getEnergyStored()
	{
		return capacity;
	}

	@Override
	public int getMaxEnergyStored() {

		return capacity;
	}
}
