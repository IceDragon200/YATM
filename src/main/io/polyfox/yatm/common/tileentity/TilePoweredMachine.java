/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.feature.IGuiNetworkSync;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import growthcraft.core.util.ItemUtils;
import io.polyfox.yatm.api.core.util.BytePack;
import io.polyfox.yatm.api.power.IPowerProducer;
import io.polyfox.yatm.common.inventory.IYATMInventory;
import io.polyfox.yatm.common.tileentity.machine.IMachineLogic;
import io.polyfox.yatm.common.tileentity.machine.IProgressiveMachine;
import io.polyfox.yatm.common.tileentity.machine.MachineUpdateState;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TilePoweredMachine extends TilePowered implements ISidedInventory, IGuiNetworkSync, IInventoryWatcher, IInteractionObject
{
	protected IYATMInventory inventory;
	protected IMachineLogic machine;
	protected boolean lastWorkingState = true;
	protected boolean dirtyNext = true;
	protected MachineUpdateState machineState = new MachineUpdateState();

	public TilePoweredMachine()
	{
		super();
		this.inventory = createInventory();
		this.machine = createMachine();
		this.machine.setTileEntity(this);
	}

	public boolean getWorkingState()
	{
		return lastWorkingState;
	}

	private void readInventoryFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("inventory"))
			inventory.readFromNBT(nbt, "inventory");
	}

	private void readMachineFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("machine"))
			machine.readFromNBT(nbt, "machine");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readInventoryFromNBT(nbt);
		readMachineFromNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_Machine(NBTTagCompound nbt)
	{
		readInventoryFromNBT(nbt);
		readMachineFromNBT(nbt);
	}

	private void writeInventoryToNBT(NBTTagCompound nbt)
	{
		inventory.writeToNBT(nbt, "inventory");
	}

	private void writeMachineToNBT(NBTTagCompound nbt)
	{
		machine.writeToNBT(nbt, "machine");
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeInventoryToNBT(nbt);
		writeMachineToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_Machine(NBTTagCompound nbt)
	{
		writeInventoryToNBT(nbt);
		writeMachineToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Machine(ByteBuf stream) throws IOException
	{
		return machine.readFromStream(stream);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Machine(ByteBuf stream) throws IOException
	{
		return machine.writeToStream(stream);
	}

	@Override
	public void sendGUINetworkData(Container container, ICrafting icrafting)
	{
		if (machine instanceof IProgressiveMachine)
		{
			final IProgressiveMachine progMachine = (IProgressiveMachine)machine;
			icrafting.sendProgressBarUpdate(container, 100, (int)progMachine.getProgress());
			icrafting.sendProgressBarUpdate(container, 101, (int)progMachine.getProgressMax());
		}
		final short[] buffer = new short[4];
		BytePack.packI64ToI16(powerStorage.getCapacity(), buffer, 0);
		for (int i = 0; i < buffer.length; ++i)
		{
			icrafting.sendProgressBarUpdate(container, 200 + i, buffer[i]);
		}
		BytePack.packI64ToI16(powerStorage.getAmount(), buffer, 0);
		for (int i = 0; i < buffer.length; ++i)
		{
			icrafting.sendProgressBarUpdate(container, 204 + i, buffer[i]);
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
				case 100:
					progMachine.setProgress((float)value);
					break;
				case 101:
					progMachine.setProgressMax((float)value);
					break;
				default:
			}
		}
		switch (id)
		{
			case 200:
			case 201:
			case 202:
			case 203:
			{
				final int segment = id - 200;
				powerStorage.setCapacity(BytePack.replaceI16SegmentInI64(segment, (short)value, powerStorage.getCapacity()));
			}	break;
			case 204:
			case 205:
			case 206:
			case 207:
			{
				final int segment = id - 204;
				powerStorage.setAmountUnsafe(BytePack.replaceI16SegmentInI64(segment, (short)value, powerStorage.getAmount()));
			}	break;
			default:
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

	@Override
	public void markDirty()
	{
		super.markDirty();
		machine.awake();
	}

	// This is completely different from markDirty, and is more of a delayed mark
	// "Mark me as dirty next update"
	public void markDirtyNext()
	{
		this.dirtyNext |= true;
	}

	public void updateMachine()
	{
		machineState.clear();
		machineState.inventory = inventory;
		machineState.powerStorage = powerStorage;
		machine.updateMachine(machineState);

		if (machineState.powerConsumed != 0)
		{
			powerStorage.consume(machineState.powerConsumed, false);
			onInternalPowerChanged();
		}

		if (lastWorkingState != machineState.didWork)
		{
			lastWorkingState = machineState.didWork;
			int meta = getBlockMetadata() & 3;
			if (lastWorkingState) meta |= 4;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, BlockFlags.SYNC);
			markDirty();
		}
	}

	protected void demandPowerFromNeighbours()
	{
		final long demand = powerThrottle.getMaxConsume();
		for (int i = 0; i < tileCache.length; ++i)
		{
			if (demand <= 0) break;
			final TileEntity te = tileCache[i];
			if (te != null)
			{
				if (te instanceof IPowerProducer)
				{
					final IPowerProducer producer = (IPowerProducer)te;
					final ForgeDirection dir = ForgeDirection.getOrientation(i);
					producer.demandPowerFrom(dir.getOpposite(), demand);
				}
			}
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			demandPowerFromNeighbours();
			updateMachine();
		}

		if (dirtyNext)
		{
			dirtyNext = false;
			markDirty();
		}
	}

	protected void triggerInventoryChanged(IInventory inv, int index)
	{
		if (machine instanceof IInventoryWatcher)
		{
			((IInventoryWatcher)machine).onInventoryChanged(inv, index);
		}
	}

	@Override
	public void onInventoryChanged(IInventory inv, int index)
	{
		markDirtyNext();
		triggerInventoryChanged(inv, index);
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
		if (machine instanceof IInventoryWatcher)
		{
			((IInventoryWatcher)machine).onItemDiscarded(inv, stack, index, discardedAmount);
		}
		final ItemStack discarded = stack.copy();
		discarded.stackSize = discardedAmount;
		ItemUtils.spawnItemStack(worldObj, xCoord, yCoord, zCoord, discarded, worldObj.rand);
	}
}
