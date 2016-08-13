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
import growthcraft.api.core.util.Point3;
import growthcraft.core.common.tileentity.event.EventHandler;
import id2h.yatm.api.core.wireless.EnumWirelessCode;
import id2h.yatm.system.WirelessSystem.WirelessEvent;
import id2h.yatm.YATM;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityWirelessRedstoneEmitter extends YATMBaseTile
{
	protected int lastPowerValue;
	protected int currentPowerValue;
	protected int emissionFrequency = 12345;
	protected double emissionRange = 16.0D;

	protected void refreshState()
	{
		final int meta = getBlockMetadata()	& 7;
		final boolean oldState = (meta & 4) > 0;
		final boolean curState = currentPowerValue > 0;
		if (oldState != curState)
		{
			final int curMeta = (meta & 3) | (curState ? 4 : 0);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, curMeta, BlockFlags.SYNC);
		}
		markDirty();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			this.currentPowerValue = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);
			if (lastPowerValue != currentPowerValue)
			{
				this.lastPowerValue = currentPowerValue;
				final ByteBuf buf = Unpooled.buffer();
				buf.writeInt(currentPowerValue);
				final WirelessEvent event = new WirelessEvent(
					worldObj.provider.dimensionId,
					emissionFrequency,
					-1,
					new Point3(xCoord, yCoord, zCoord),
					emissionRange,
					EnumWirelessCode.PUT,
					buf);
				YATM.wireless.pub(event);
				refreshState();
			}
		}
	}

	private void readEmitterFromNBT(NBTTagCompound nbt)
	{
		this.lastPowerValue = nbt.getInteger("last_power_value");
		this.currentPowerValue = nbt.getInteger("current_power_value");
		this.emissionFrequency = nbt.getInteger("emission_frequency");
		this.emissionRange = nbt.getDouble("emission_range");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readEmitterFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readEmitterFromNBT(nbt);
	}

	private void writeEmitterToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("last_power_value", lastPowerValue);
		nbt.setInteger("current_power_value", currentPowerValue);
		nbt.setInteger("emission_frequency", emissionFrequency);
		nbt.setDouble("emission_range", emissionRange);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeEmitterToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeEmitterToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Power(ByteBuf stream) throws IOException
	{
		this.lastPowerValue = stream.readInt();
		this.currentPowerValue = stream.readInt();
		this.emissionFrequency = stream.readInt();
		this.emissionRange = stream.readDouble();
		return false;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Power(ByteBuf stream) throws IOException
	{
		stream.writeInt(lastPowerValue);
		stream.writeInt(currentPowerValue);
		stream.writeInt(emissionFrequency);
		stream.writeDouble(emissionRange);
		return false;
	}
}
