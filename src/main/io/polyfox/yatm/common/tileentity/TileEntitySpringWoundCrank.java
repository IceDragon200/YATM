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

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.tileentity.event.TileEventHandler;

import appeng.api.implementations.tiles.ICrankable;

import io.netty.buffer.ByteBuf;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySpringWoundCrank extends YATMTileBase implements ICrankable, INBTItemSerializable
{
	// The interval between winds/turns
	private static final int ticksPerWind = 5;
	// How much should the shaft turn (visibily) per wind
	private static final int clicksPerWind = 360 / ticksPerWind;
	// The maximum number of winds allowed, afer passing that,
	// this device will self destruct, err I mean it will be destroyed.
	public final int maxWinds = 150;
	// Number of winds before entering the 'warning' state
	public final int warnWinds = 100;
	public int winds;
	public int clicks;
	protected int nextWindIn;

	@Override
	public boolean canTurn()
	{
		// @todo Add option to control overwinding
		//return winds < maxWinds;
		return true;
	}

	@Override
	public void applyTurn()
	{
		this.winds = winds + 10;
	}

	@Override
	public boolean canCrankAttach(ForgeDirection directionToCrank)
	{
		switch (directionToCrank)
		{
			case UP:
			case DOWN:
				return false;
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				return true;
			default:
		}
		return false;
	}

	public ForgeDirection getUp()
	{
		return ForgeDirection.UP;
	}

	public ICrankable getCrankable()
	{
		if (worldObj.isRemote)
		{
			return null;
		}

		final ForgeDirection below = this.getUp().getOpposite();
		final TileEntity te = this.worldObj.getTileEntity(this.xCoord + below.offsetX, this.yCoord + below.offsetY, this.zCoord + below.offsetZ);
		if (te instanceof ICrankable)
		{
			return (ICrankable) te;
		}
		return null;
	}

	protected boolean isOverwound()
	{
		return winds >= maxWinds;
	}

	protected boolean isAlmostOverwound()
	{
		return winds >= warnWinds;
	}

	protected void refreshState()
	{
		final int meta = getBlockMetadata()	& 0xF;
		final boolean online = winds > 0;
		final boolean warn = isAlmostOverwound();
		final int newMeta = (meta & 3) | (online ? 4 : 0) | (warn ? 8 : 0);
		if (meta != newMeta)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, BlockFlags.UPDATE_AND_SYNC);
			markDirty();
		}
	}

	protected void backlash()
	{
		this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
		// worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (winds > 0)
		{
			if (isOverwound())
			{
				backlash();
			}
			else
			{
				if (nextWindIn-- <= 0)
				{
					this.nextWindIn = ticksPerWind;
					final ICrankable crankable = getCrankable();
					if (crankable != null)
					{
						if (!worldObj.isRemote)
						{
							if (crankable.canTurn())
							{
								this.winds--;
								crankable.applyTurn();
							}
						}
					}
				}
				refreshState();
				this.clicks = (this.clicks + clicksPerWind / ticksPerWind) % 360;
			}
		}
	}

	protected void readCrankFromNBT(NBTTagCompound nbt)
	{
		this.winds = nbt.getInteger("winds");
		this.nextWindIn = nbt.getInteger("winds");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCrankFromNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_Crank(NBTTagCompound nbt)
	{
		readCrankFromNBT(nbt);
	}

	private void writeCrankToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("winds", winds);
		nbt.setInteger("next_wind_in", nextWindIn);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCrankToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_Crank(NBTTagCompound nbt)
	{
		writeCrankToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_Crank(ByteBuf stream) throws IOException
	{
		this.winds = stream.readInt();
		return true;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_Crank(ByteBuf stream) throws IOException
	{
		stream.writeInt(winds);
		return false;
	}
}
