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
package id2h.yatm.common.tileentity;

import growthcraft.core.util.ItemUtils;
import id2h.yatm.common.inventory.IYATMInventory;
import id2h.yatm.common.inventory.YATMInternalInventory;
import id2h.yatm.common.tileentity.energy.MachineEnergyStorage;
import id2h.yatm.common.tileentity.energy.YATMEnergyStorage;
import id2h.yatm.common.tileentity.feature.IInventoryWatcher;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCoalGenerator extends YATMGeneratorBase implements ISidedInventory
{
	protected IYATMInventory inventory;
	protected int burnTime;
	protected int idleTime;

	public TileEntityCoalGenerator()
	{
		super();
		this.inventory = new YATMInternalInventory(this,1);
		setEnergySyncPriority(200);
	}

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(16000, 50);
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
				final ItemStack fuel = getStackInSlot(0);
				if (fuel != null)
				{
					this.burnTime = GameRegistry.getFuelValue(fuel);
					if (burnTime > 0)
					{
						decrStackSize(0, 1);
					}
				}
			}
			this.idleTime = 5;
		}
	}

	@Override
	protected void updateEnergyProvider()
	{
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
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inventory.readFromNBT(nbt, "inventory");
		this.idleTime = nbt.getInteger("idle_time");
		this.burnTime = nbt.getInteger("burn_time");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		inventory.writeToNBT(nbt, "inventory");
		nbt.setInteger("idle_time", idleTime);
		nbt.setInteger("burn_time", burnTime);
	}
}
