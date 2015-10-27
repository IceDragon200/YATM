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
package id2h.yatm.common.tileentity;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

import id2h.yatm.common.tileentity.inventory.IYATMInventory;
import id2h.yatm.common.tileentity.machine.IMachineLogic;
import id2h.yatm.common.tileentity.machine.IProgressiveMachine;
import id2h.yatm.common.tileentity.feature.IGuiNetworkSync;
import id2h.yatm.event.EventHandler;
import id2h.yatm.util.BlockFlags;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class YATMPoweredMachine extends YATMPoweredTile implements ISidedInventory, IGuiNetworkSync
{
	protected IYATMInventory inventory;
	protected IMachineLogic machine;
	protected boolean lastWorkingState = true;

	public YATMPoweredMachine()
	{
		super();
		this.inventory = createInventory();
		this.machine = createMachine();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inventory.readFromNBT(nbt, "inventory");
		machine.readFromNBT(nbt, "machine");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		inventory.writeToNBT(nbt, "inventory");
		machine.writeToNBT(nbt, "machine");
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Machine(ByteBuf stream) throws IOException
	{
		return machine.readFromStream(stream);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public void writeToStream_Machine(ByteBuf stream) throws IOException
	{
		machine.writeToStream(stream);
	}

	@Override
	public void sendGUINetworkData(Container container, ICrafting icrafting)
	{
		if (machine instanceof IProgressiveMachine)
		{
			final IProgressiveMachine progMachine = (IProgressiveMachine)machine;
			icrafting.sendProgressBarUpdate(container, 0, (int)progMachine.getProgress());
			icrafting.sendProgressBarUpdate(container, 1, (int)progMachine.getProgressMax());
		}
	}

	@Override
	public void receiveGUINetworkData(int id, int value)
	{
		if (machine instanceof IProgressiveMachine)
		{
			final IProgressiveMachine progMachine = (IProgressiveMachine)machine;
			switch (id)
			{
				case 0:
					progMachine.setProgress((float)value);
					break;
				case 1:
					progMachine.setProgressMax((float)value);
					break;
				default:
					System.err.println("Invalid DATA class=" + this + " id=" + id);
			}
		}
	}

	protected abstract IYATMInventory createInventory();
	protected abstract IMachineLogic createMachine();

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		return inventory.decrStackSize(index, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		return inventory.getStackInSlotOnClosing(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inventory.setInventorySlotContents(index, stack);
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return inventory.hasCustomInventoryName();
	}

	@Override
	public String getInventoryName()
	{
		return inventory.getInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory()
	{
		inventory.openInventory();
	}

	@Override
	public void closeInventory()
	{
		inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return inventory.isItemValidForSlot(index, stack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] {0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	public float getMachineProgressRate()
	{
		if (machine instanceof IProgressiveMachine)
		{
			return ((IProgressiveMachine)machine).getProgressRate();
		}
		return 0.0f;
	}

	public int getRunningPowerCost()
	{
		return machine.getRunningPowerCost(energyStorage, inventory);
	}

	public int getWorkingPowerCost()
	{
		return machine.getWorkingPowerCost(energyStorage, inventory);
	}

	public boolean canWork()
	{
		return machine.canWork(energyStorage, inventory);
	}

	public int doWork()
	{
		return machine.doWork(energyStorage, inventory);
	}

	public void updateMachine()
	{
		int consumed = getRunningPowerCost();
		boolean didWork = false;
		if (canWork())
		{
			consumed += getWorkingPowerCost();
			if (energyStorage.getEnergyStored() >= consumed)
			{
				consumed += doWork();
				didWork = true;
			}
		}
		if (consumed != 0)
		{
			energyStorage.extractEnergy(consumed, false);
			markForUpdate();
		}
		if (lastWorkingState != didWork)
		{
			lastWorkingState = didWork;
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			if (lastWorkingState) meta |= 4;

			System.out.println("Machine changed state:" +
				" obj=" + this +
				" state=" + lastWorkingState +
				" meta=" + meta +
				" x=" + xCoord +
				" y=" + yCoord +
				" z=" + zCoord);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, BlockFlags.UPDATE_CLIENT);
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote) updateMachine();
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		System.out.println("Marked as dirty:" +
				" obj=" + this +
				" x=" + xCoord +
				" y=" + yCoord +
				" z=" + zCoord);
	}
}