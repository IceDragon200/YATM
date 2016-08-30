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

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.core.common.inventory.GrcInternalInventory;
import growthcraft.core.common.inventory.InventoryProcessor;
import growthcraft.core.common.tileentity.device.DeviceFluidSlot;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import growthcraft.core.common.tileentity.GrcTileDeviceBase;
import io.polyfox.yatm.common.inventory.ContainerCrystalVat;
import io.polyfox.yatm.common.inventory.YATMInternalInventory;

import appeng.api.implementations.items.IGrowableCrystal;
import appeng.api.implementations.tiles.ICrystalGrowthAccelerator;
import appeng.client.EffectType;
import appeng.core.CommonHelper;
import appeng.util.Platform;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityCrystalVat extends GrcTileDeviceBase implements IInteractionObject, INBTItemSerializable
{
	private static final int[] primarySlots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	protected int[] slotProgress = new int[9];
	private DeviceFluidSlot fluidSlotSource = new DeviceFluidSlot(this, 0);

	@Override
	protected GrcInternalInventory createInventory()
	{
		/**
		 * The vat can host 9 crystals
		 */
		return new YATMInternalInventory(this, 12).setInventoryName("yatm.inventory.crystal_vat");
	}

	@Override
	public String getGuiID()
	{
		return "yatm:crystal_vat";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerCrystalVat(playerInventory, this);
	}

	@Override
	public String getDefaultInventoryName()
	{
		return inventory.getInventoryName();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return primarySlots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		if (index > 8) return false;
		return InventoryProcessor.instance().canInsertItem(this, stack, index);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		if (index > 8) return false;
		return InventoryProcessor.instance().canExtractItem(this, stack, index);
	}

	@Override
	protected FluidTank[] createTanks()
	{
		return new FluidTank[] { new FluidTank(1000) };
	}

	@Override
	protected FluidStack doDrain(ForgeDirection from, int amount, boolean shouldDrain)
	{
		return drainFluidTank(0, amount, shouldDrain);
	}

	@Override
	protected FluidStack doDrain(ForgeDirection from, FluidStack stack, boolean shouldDrain)
	{
		if (stack == null || !stack.isFluidEqual(getFluidStack(0)))
		{
			return null;
		}
		return doDrain(from, stack.amount, shouldDrain);
	}

	@Override
	protected int doFill(ForgeDirection dir, FluidStack stack, boolean shouldFill)
	{
		return fillFluidTank(0, stack, shouldFill);
	}

	private boolean isAccelerated(int x, int y, int z)
	{
		final TileEntity te = worldObj.getTileEntity(x, y, z);
		return te instanceof ICrystalGrowthAccelerator && ((ICrystalGrowthAccelerator)te).isPowered();
	}

	private int getSpeed(int x, int y, int z)
	{
		final int per = 80;
		final float mul = 0.4f;

		int qty = 0;

		if (this.isAccelerated(x + 1, y, z))
		{
			qty += per + qty * mul;
		}

		if (this.isAccelerated(x, y + 1, z))
		{
			qty += per + qty * mul;
		}

		if (this.isAccelerated(x, y, z + 1))
		{
			qty += per + qty * mul;
		}

		if (this.isAccelerated(x - 1, y, z))
		{
			qty += per + qty * mul;
		}

		if (this.isAccelerated(x, y - 1, z))
		{
			qty += per + qty * mul;
		}

		if (this.isAccelerated(x, y, z - 1))
		{
			qty += per + qty * mul;
		}

		return qty;
	}

	protected void growCrystals()
	{
		final boolean isClient = Platform.isClient();
		final Material mat = Material.water;
		for (int i = 0; i < 9; ++i)
		{
			final ItemStack stack = getStackInSlot(i);
			if (stack == null)
			{
				slotProgress[i] = 0;
				continue;
			}
			final Item item = stack.getItem();
			if (item instanceof IGrowableCrystal)
			{
				final IGrowableCrystal cry = (IGrowableCrystal)item;
				final float multiplier = cry.getMultiplier(Blocks.water, mat);
				final int speed = (int)Math.max(1, getSpeed(xCoord, yCoord, zCoord) * multiplier);

				if (mat.isLiquid())
				{
					if (isClient)
					{
						slotProgress[i]++;
					}
					else
					{
						slotProgress[i] += speed;
					}
				}
				else
				{
					slotProgress[i] = 0;
				}

				if (isClient)
				{
					int len = 40;

					if (speed > 2)
					{
						len = 20;
					}

					if (speed > 90)
					{
						len = 15;
					}

					if (speed > 150)
					{
						len = 10;
					}

					if (speed > 240)
					{
						len = 7;
					}

					if (speed > 360)
					{
						len = 3;
					}

					if (speed > 500)
					{
						len = 1;
					}

					if (slotProgress[i] >= len)
					{
						slotProgress[i] = 0;
						CommonHelper.proxy.spawnEffect(EffectType.Vibrant, worldObj, xCoord + 0.5, yCoord + 0.8, zCoord + 0.5, null);
					}
				}
				else
				{
					if (slotProgress[i] > 1000)
					{
						slotProgress[i] -= 1000;
						setInventorySlotContents(i, cry.triggerGrowth(stack));
					}
				}
			}
			else
			{
				slotProgress[i] = 0;
			}
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (fluidSlotSource.isFull())
		{
			growCrystals();
		}
	}

	private void readCrystalVatBaseFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("slot_progress"))
		{
			final NBTBase tag = nbt.getTag("slot_progress");
			if (tag instanceof NBTTagIntArray)
			{
				final NBTTagIntArray intArrayTag = (NBTTagIntArray)tag;
				System.arraycopy(intArrayTag.func_150302_c(), 0, slotProgress, 0, 9);
			}
		}
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCrystalVatBaseFromNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_CrystalVat(NBTTagCompound nbt)
	{
		readCrystalVatBaseFromNBT(nbt);
	}

	private void writeCrystalVatBaseToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("slot_progress", new NBTTagIntArray(slotProgress));
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCrystalVatBaseToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_CrystalVat(NBTTagCompound nbt)
	{
		writeCrystalVatBaseToNBT(nbt);
	}
}
