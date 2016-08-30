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

import java.io.IOException;

import growthcraft.api.core.fluids.FluidTest;
import growthcraft.core.common.tileentity.device.FluidTanks;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import io.polyfox.yatm.common.inventory.ContainerFuelGenerator;
import io.polyfox.yatm.common.tileentity.energy.YATMEnergyStorage;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFuelGenerator extends YATMGeneratorBase implements IInteractionObject, IFluidHandler
{
	protected FluidTanks fluidTanks;

	public TileEntityFuelGenerator()
	{
		super();
		this.fluidTanks = new FluidTanks(new FluidTank[] { new FluidTank(16000) });
	}

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(64000, 100);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:fuel_generator";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFuelGenerator(playerInventory, this);
	}

	protected void markForFluidUpdate()
	{
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	protected FluidStack doDrain(ForgeDirection dir, int amount, boolean shouldDrain)
	{
		return null;
	}

	protected FluidStack doDrain(ForgeDirection dir, FluidStack stack, boolean shouldDrain)
	{
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection dir, int amount, boolean shouldDrain)
	{
		final FluidStack result = doDrain(dir, amount, shouldDrain);
		if (shouldDrain && FluidTest.isValid(result)) markForFluidUpdate();
		return result;
	}

	@Override
	public FluidStack drain(ForgeDirection dir, FluidStack stack, boolean shouldDrain)
	{
		if (!FluidTest.isValid(stack)) return null;
		final FluidStack result = doDrain(dir, stack, shouldDrain);
		if (shouldDrain && FluidTest.isValid(result)) markForFluidUpdate();
		return result;
	}

	protected int doFill(ForgeDirection dir, FluidStack stack, boolean shouldFill)
	{
		return 0;
	}

	@Override
	public int fill(ForgeDirection dir, FluidStack stack, boolean shouldFill)
	{
		final int result = doFill(dir, stack, shouldFill);
		if (shouldFill && result != 0) markForFluidUpdate();
		return result;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return fluidTanks.getTankInfo(from);
	}

	protected void readTanksFromNBT(NBTTagCompound nbt)
	{
		if (fluidTanks != null)
			fluidTanks.readFromNBT(nbt);
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readTanksFromNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_FuelGenerator(NBTTagCompound nbt)
	{
		readTanksFromNBT(nbt);
	}

	private void writeTanksToNBT(NBTTagCompound nbt)
	{
		if (fluidTanks != null)
			fluidTanks.writeToNBT(nbt);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeTanksToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_FuelGenerator(NBTTagCompound nbt)
	{
		writeTanksToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_FuelGenerator(ByteBuf stream) throws IOException
	{
		if (fluidTanks != null)
			fluidTanks.readFromStream(stream);
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_FuelGenerator(ByteBuf stream) throws IOException
	{
		if (fluidTanks != null)
			fluidTanks.writeToStream(stream);
		return false;
	}
}
