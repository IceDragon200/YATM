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

import java.io.IOException;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.tileentity.event.EventHandler;
import id2h.yatm.common.tileentity.feature.IWirelessReceiver;
import id2h.yatm.system.WirelessSystem.WirelessEvent;
import id2h.yatm.YATM;

import io.netty.buffer.ByteBuf;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityWirelessRedstoneReceiver extends YATMBaseTile implements IWirelessReceiver
{
	protected int lastPowerValue;
	protected int currentPowerValue;
	protected int receiverFrequency = 12345;

	protected void notifySorroundingBlocks()
	{
		final int meta = getBlockMetadata()	& 7;
		final boolean oldState = (meta & 4) > 0;
		final boolean curState = currentPowerValue > 0;
		if (oldState != curState)
		{
			final int curMeta = (meta & 3) | (curState ? 4 : 0);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, curMeta, BlockFlags.UPDATE_AND_SYNC);
		}

		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, getBlockType());
		}

		markDirty();
	}

	@Override
	public WirelessEvent onWirelessEvent(WirelessEvent event)
	{
		//YATM.getLogger().info(String.format("Got a Wireless Event from dimension_id=%d origin=%s frequency=%d receiverFrequency=%d", event.dimensionId, event.origin, event.frequency, receiverFrequency));
		if (event.frequency != receiverFrequency) return null;
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
						notifySorroundingBlocks();
					}
				}
				break;
			default:
		}
		return null;
	}

	public int getPowerValue()
	{
		return currentPowerValue;
	}

	private void readReceiverFromNBT(NBTTagCompound nbt)
	{
		this.lastPowerValue = nbt.getInteger("last_power_value");
		this.currentPowerValue = nbt.getInteger("current_power_value");
		this.receiverFrequency = nbt.getInteger("receiver_frequency");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readReceiverFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readReceiverFromNBT(nbt);
	}

	private void writeReceiverToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("last_power_value", lastPowerValue);
		nbt.setInteger("current_power_value", currentPowerValue);
		nbt.setInteger("receiver_frequency", receiverFrequency);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeReceiverToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeReceiverToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Power(ByteBuf stream) throws IOException
	{
		this.lastPowerValue = stream.readInt();
		this.currentPowerValue = stream.readInt();
		this.receiverFrequency = stream.readInt();
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Power(ByteBuf stream) throws IOException
	{
		stream.writeInt(lastPowerValue);
		stream.writeInt(currentPowerValue);
		stream.writeInt(receiverFrequency);
		return true;
	}
}
