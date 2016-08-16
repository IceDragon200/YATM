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

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.tileentity.event.EventHandler;
import io.polyfox.yatm.common.tileentity.feature.IUpdatableTile;
import io.polyfox.yatm.util.YATMStreamUtils;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityWirelessRedstoneBase extends YATMBaseTile implements IUpdatableTile
{
	protected int lastPowerValue;
	protected int currentPowerValue;
	protected String address = "0.0.0.0";

	protected void refreshState()
	{
		final int meta = getBlockMetadata()	& 7;
		final boolean oldState = (meta & 4) > 0;
		final boolean curState = currentPowerValue > 0;
		if (oldState != curState)
		{
			final int curMeta = (meta & 3) | (curState ? 4 : 0);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, curMeta, BlockFlags.UPDATE_AND_SYNC);
		}
		markDirty();
	}

	public int getPowerValue()
	{
		return currentPowerValue;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String p_address)
	{
		this.address = p_address;
		markDirty();
	}

	private void readWirelessBaseFromNBT(NBTTagCompound nbt)
	{
		this.lastPowerValue = nbt.getInteger("last_power_value");
		this.currentPowerValue = nbt.getInteger("current_power_value");
		if (nbt.hasKey("address"))
			this.address = nbt.getString("address");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readWirelessBaseFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readWirelessBaseFromNBT(nbt);
	}

	private void writeWirelessBaseToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("last_power_value", lastPowerValue);
		nbt.setInteger("current_power_value", currentPowerValue);
		nbt.setString("address", address);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeWirelessBaseToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeWirelessBaseToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_WirelessBase(ByteBuf stream) throws IOException
	{
		this.lastPowerValue = stream.readInt();
		this.currentPowerValue = stream.readInt();
		this.address = YATMStreamUtils.readString(stream, address);
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_WirelessBase(ByteBuf stream) throws IOException
	{
		stream.writeInt(lastPowerValue);
		stream.writeInt(currentPowerValue);
		YATMStreamUtils.writeString(stream, address);
		return true;
	}

	@Override
	public void updateTilePropertyFromObject(int code, Object payload)
	{
		switch (code)
		{
			case 0:
				if (payload instanceof String)
				{
					setAddress((String)payload);
				}
				break;
			default:
		}
	}
}
