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

import io.polyfox.yatm.common.inventory.ContainerWirelessRedstoneReceiver;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import io.polyfox.yatm.common.tileentity.feature.IWirelessReceiver;
import io.polyfox.yatm.system.WirelessSystem.WirelessEvent;
//import io.polyfox.yatm.YATM;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWirelessRedstoneReceiver extends TileEntityWirelessRedstoneBase implements IWirelessReceiver, IInteractionObject
{
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerWirelessRedstoneReceiver(playerInventory, this);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:wireless_redstone_receiver";
	}

	@Override
	protected void refreshState()
	{
		super.refreshState();
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, getBlockType());
		}
	}

	@Override
	public WirelessEvent onWirelessEvent(WirelessEvent event)
	{
		//YATM.getLogger().info(String.format("Got a Wireless Event from dimension_id=%d origin=%s frequency=%d address=%s", event.dimensionId, event.origin, event.frequency, address));
		if (!address.equals(event.address)) return null;
		switch (event.code)
		{
			case PUT:
			case PATCH:
				final ByteBuf buf = event.getPayload();
				if (buf.isReadable(4))
				{
					final int value = buf.readInt();
					this.lastPowerValue = currentPowerValue;
					this.currentPowerValue = value;
					if (lastPowerValue != currentPowerValue)
					{
						refreshState();
					}
				}
				break;
			default:
		}
		return null;
	}
}
