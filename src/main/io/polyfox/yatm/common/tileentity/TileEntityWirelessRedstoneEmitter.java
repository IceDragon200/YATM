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

import growthcraft.api.core.util.Point3;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import io.polyfox.yatm.api.core.wireless.EnumWirelessCode;
import io.polyfox.yatm.common.inventory.ContainerWirelessRedstoneEmitter;
import io.polyfox.yatm.system.WirelessSystem.WirelessEvent;
import io.polyfox.yatm.YATM;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileEntityWirelessRedstoneEmitter extends TileEntityWirelessRedstoneBase implements IInteractionObject
{
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerWirelessRedstoneEmitter(playerInventory, this);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:wireless_redstone_emitter";
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			this.currentPowerValue = worldObj.getStrongestIndirectPower(xCoord, yCoord, zCoord);
			if (checkState || lastPowerValue != currentPowerValue)
			{
				this.lastPowerValue = currentPowerValue;
				this.checkState = false;
				final ByteBuf buf = Unpooled.buffer();
				buf.writeInt(currentPowerValue);
				final WirelessEvent event = new WirelessEvent(
					worldObj.provider.dimensionId,
					address,
					null,
					new Point3(xCoord, yCoord, zCoord),
					emissionRange,
					EnumWirelessCode.PUT,
					buf);
				YATM.wireless.pub(event);
				refreshState();
			}
		}
	}

	@Override
	public WirelessEvent onWirelessEvent(WirelessEvent event)
	{
		if (!address.equals(event.address)) return null;
		switch (event.code)
		{
			case GET:
				this.checkState = true;
				break;
			default:
		}
		return null;
	}
}
