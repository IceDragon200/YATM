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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
import appeng.api.util.WorldCoord;
import appeng.tile.AEBaseInvTile;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.InventoryAdaptor;
import appeng.util.Platform;
import appeng.util.inv.WrapperInventoryRange;

public class TileEntityAutoGrinder extends AEBaseInvTile
{
	private static final int[] inputs = new int[] {0, 1, 2};
	private static final int[] sides = new int[] {0, 1, 2, 3, 4, 5};
	private final AppEngInternalInventory inv;
	private int points;

	public TileEntityAutoGrinder()
	{
		inv = new AppEngInternalInventory(this, 7);
	}

	@Override
	public void setOrientation(ForgeDirection inForward, ForgeDirection inUp)
	{
		super.setOrientation(inForward, inUp);
		this.getBlockType().onNeighborBlockChange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Platform.AIR);
	}

	@Override
	public IInventory getInternalInventory()
	{
		return inv;
	}

	@Override
	public void onChangeInventory(IInventory inventory, int slot, InvOperation mc, ItemStack removed, ItemStack added)
	{
	}

	@Override
	public boolean canInsertItem(int slotIndex, ItemStack insertingItem, int side)
	{
		if (AEApi.instance().registries().grinder().getRecipeForInput(insertingItem) == null)
		{
			return false;
		}
		return slotIndex >= 0 && slotIndex <= 2;
	}

	@Override
	public boolean canExtractItem(int slotIndex, ItemStack extractedItem, int side)
	{
		return slotIndex >= 3 && slotIndex <= 5;
	}

	@Override
	public int[] getAccessibleSlotsBySide(ForgeDirection side)
	{
		return sides;
	}

	public boolean canTurn()
	{
		if (Platform.isClient())
		{
			return false;
		}
		if (null == this.getStackInSlot(6))
		{
			final WrapperInventoryRange src = new WrapperInventoryRange(this, inputs, true);
			for (int x = 0; x < src.getSizeInventory(); ++x)
			{
				ItemStack item = src.getStackInSlot(x);
				if (item == null)
				{
					//continue
				}
				final IGrinderEntry r = AEApi.instance().registries().grinder().getRecipeForInput(item);
				if (r != null)
				{
					if (item.stackSize >= r.getInput().stackSize)
					{
						item.stackSize -= r.getInput().stackSize;

						final ItemStack ais = item.copy();
						ais.stackSize = r.getInput().stackSize;
						if (item.stackSize <= 0)
						{
							item = null;
						}
						src.setInventorySlotContents(x, item);
						this.setInventorySlotContents(6, ais);
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public void applyTurn()
	{
		if (Platform.isClient())
		{
			return;
		}
		this.points += 1;
		final ItemStack processing = this.getStackInSlot(6);
		final IGrinderEntry r = AEApi.instance().registries().grinder().getRecipeForInput(processing);
		if (r != null)
		{
			if (r.getEnergyCost() > this.points)
			{
				return;
			}
			this.points = 0;
			final InventoryAdaptor sia = InventoryAdaptor.getAdaptor(new WrapperInventoryRange(this, 3, 3, true), ForgeDirection.EAST);
			this.addItem(sia, r.getOutput());

			float chance = (Platform.getRandomInt() % 2000) / 2000.0f;
			if (chance <= r.getOptionalChance())
			{
				this.addItem(sia, r.getOptionalOutput());
			}

			chance = (Platform.getRandomInt() % 2000) / 2000.0f;
			if (chance <= r.getSecondOptionalChance())
			{
				this.addItem(sia, r.getSecondOptionalOutput());
			}
			this.setInventorySlotContents(6, null);
		}
	}

	private void addItem(InventoryAdaptor sia, ItemStack output)
	{
		if (output == null)
		{
			return;
		}
		final ItemStack notAdded = sia.addItems(output);
		if (notAdded != null)
		{
			final WorldCoord wc = new WorldCoord(this.xCoord, this.yCoord, this.zCoord);
			wc.add(this.getForward(), 1);
			final List<ItemStack> out = new ArrayList<ItemStack>();
			out.add(notAdded);
			Platform.spawnDrops(this.worldObj, wc.x, wc.y, wc.z, out);
		}
	}
}
