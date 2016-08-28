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
import io.netty.buffer.ByteBuf;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.feature.IGuiNetworkSync;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import growthcraft.core.util.ItemUtils;
import io.polyfox.yatm.common.inventory.ContainerCoalGenerator;
import io.polyfox.yatm.common.inventory.IYATMInventory;
import io.polyfox.yatm.common.inventory.YATMInternalInventory;
import io.polyfox.yatm.common.tileentity.energy.YATMEnergyStorage;
import io.polyfox.yatm.common.tileentity.feature.IEnergyGridSync;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoalGenerator extends YATMGeneratorBase implements ISidedInventory, IGuiNetworkSync, IInteractionObject
{
	protected IYATMInventory inventory;
	protected int burnTime;
	protected int burnTimeMax;
	protected int idleTime;
	protected boolean online;
	protected boolean lastOnline;

	public TileEntityCoalGenerator()
	{
		super();
		this.inventory = new YATMInternalInventory(this, 1);
		setEnergySyncPriority(200);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:coal_generator";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerCoalGenerator(playerInventory, this);
	}

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(16000, 50);
	}

	public float getBurnTimeRate()
	{
		if (burnTimeMax > 0)
		{
			return (float)burnTime / (float)burnTimeMax;
		}
		return 0.0f;
	}

	@Override
	public boolean checkEnergySyncLevels(ForgeDirection dir, IEnergyGridSync other)
	{
		if (getEnergyStored(dir) > 0)
		{
			return true;
		}
		return false;
	}

	private void updateEnergyProduction()
	{
		if (idleTime > 0)
		{
			--idleTime;
		}
		else
		{
			if (burnTime > 0)
			{
				--burnTime;
				final int stored = energyStorage.getEnergyStored();
				energyStorage.modifyEnergyStored(10);
				if (stored != energyStorage.getEnergyStored())
				{
					markForBlockUpdate();
				}
			}
			else
			{
				this.online = false;
				if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored())
				{
					final ItemStack fuel = getStackInSlot(0);
					if (fuel != null)
					{
						this.burnTime = TileEntityFurnace.getItemBurnTime(fuel);
						if (burnTime > 0)
						{
							this.burnTimeMax = burnTime;
							setInventorySlotContents(0, ItemUtils.consumeStack(getStackInSlot(0)));
							this.online = true;
						}
						else
						{
						}
					}
				}
				this.idleTime = 5;
			}
		}

		if (lastOnline != online)
		{
			this.lastOnline = online;
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			if (online) meta |= 4;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, BlockFlags.UPDATE_AND_SYNC);
		}
	}

	@Override
	protected void updateEnergyProvider()
	{
		updateEnergyProduction();
		super.updateEnergyProvider();
	}

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
		if (index == 0)
		{
			return TileEntityFurnace.getItemBurnTime(stack) > 0;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_CoalGenerator(ByteBuf stream) throws IOException
	{
		this.idleTime = stream.readInt();
		this.burnTime = stream.readInt();
		this.burnTimeMax = stream.readInt();
		this.online = stream.readBoolean();
		return false;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_CoalGenerator(ByteBuf stream) throws IOException
	{
		stream.writeInt(idleTime);
		stream.writeInt(burnTime);
		stream.writeInt(burnTimeMax);
		stream.writeBoolean(online);
		return false;
	}

	private void readInventoryFromNBT(NBTTagCompound nbt)
	{
		inventory.readFromNBT(nbt, "inventory");
	}

	private void readTimeFromNBT(NBTTagCompound nbt)
	{
		this.idleTime = nbt.getInteger("idle_time");
		this.burnTime = nbt.getInteger("burn_time");
		this.burnTimeMax = nbt.getInteger("burn_time_max");
		this.online = nbt.getBoolean("online");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readInventoryFromNBT(nbt);
		readTimeFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readInventoryFromNBT(nbt);
		readTimeFromNBT(nbt);
	}

	private void writeInventoryToNBT(NBTTagCompound nbt)
	{
		inventory.writeToNBT(nbt, "inventory");
	}

	private void writeTimesToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("idle_time", idleTime);
		nbt.setInteger("burn_time", burnTime);
		nbt.setInteger("burn_time_max", burnTimeMax);
		nbt.setBoolean("online", online);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeInventoryToNBT(nbt);
		writeTimesToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeInventoryToNBT(nbt);
		writeTimesToNBT(nbt);
	}

	@Override
	public void sendGUINetworkData(Container container, ICrafting icrafting)
	{
		icrafting.sendProgressBarUpdate(container, 0, burnTime);
		icrafting.sendProgressBarUpdate(container, 1, burnTimeMax);
	}

	@Override
	public void receiveGUINetworkData(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.burnTime = value;
				break;
			case 1:
				this.burnTimeMax = value;
				break;
			default:
				System.err.println("Invalid Network DATA class=" + this + " id=" + id);
		}
	}
}
