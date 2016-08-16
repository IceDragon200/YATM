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
package io.polyfox.yatm.common.tileentity;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

import cofh.api.energy.IEnergyReceiver;
import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.core.common.tileentity.event.EventHandler;
import io.polyfox.yatm.common.tileentity.energy.YATMEnergyStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class YATMPoweredTile extends YATMBaseTile implements IEnergyReceiver, INBTItemSerializable
{
	protected YATMEnergyStorage energyStorage;

	public YATMPoweredTile()
	{
		super();
		this.energyStorage = createEnergyStorage();
	}

	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(4000, 10);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulated)
	{
		final int result = energyStorage.receiveEnergy(maxReceive, simulated);
		if (!simulated && result != 0)
		{
			markForBlockUpdate();
		}
		return result;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return energyStorage.getMaxEnergyStored();
	}

	public float getPowerStorageRate(ForgeDirection from)
	{
		final int max = getMaxEnergyStored(from);
		if (max != 0) return (float)getEnergyStored(from) / (float)max;
		return 0.0f;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Energy(ByteBuf stream) throws IOException
	{
		final int energy = stream.readInt();
		energyStorage.setEnergyStored(energy);
		return false;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Energy(ByteBuf stream) throws IOException
	{
		final int energy = getEnergyStored(ForgeDirection.UNKNOWN);
		stream.writeInt(energy);
		return false;
	}

	private void readEnergyFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("energy"))
			energyStorage.readFromNBT(nbt, "energy");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readEnergyFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readEnergyFromNBT(nbt);
	}

	private void writeEnergyToNBT(NBTTagCompound nbt)
	{
		energyStorage.writeToNBT(nbt, "energy");
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeEnergyToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeEnergyToNBT(nbt);
	}
}
