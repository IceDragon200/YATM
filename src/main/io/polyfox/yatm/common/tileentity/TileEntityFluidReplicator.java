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

import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.PulseStepper;
import growthcraft.core.common.tileentity.device.DeviceFluidSlot;
import growthcraft.core.common.tileentity.GrcTileEntityDeviceBase;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityFluidReplicator extends GrcTileEntityDeviceBase
{
	private DeviceFluidSlot fluidSlotSource;
	private DeviceFluidSlot fluidSlotDest;
	private PulseStepper workPulsar = new PulseStepper(20, 0);

	public TileEntityFluidReplicator()
	{
		super();
		this.fluidSlotSource = new DeviceFluidSlot(this, 0);
		this.fluidSlotDest = new DeviceFluidSlot(this, 1);
	}

	@Override
	protected FluidTank[] createTanks()
	{
		return new FluidTank[] { new FluidTank(1000), new FluidTank(32000) };
	}

	@Override
	protected FluidStack doDrain(ForgeDirection dir, int amount, boolean shouldDrain)
	{
		return fluidSlotDest.consume(amount, shouldDrain);
	}

	@Override
	protected FluidStack doDrain(ForgeDirection dir, FluidStack stack, boolean shouldDrain)
	{
		return fluidSlotDest.consume(stack, shouldDrain);
	}

	@Override
	protected int doFill(ForgeDirection dir, FluidStack stack, boolean shouldFill)
	{
		return fluidSlotSource.fill(stack, shouldFill);
	}

	protected void setState(boolean newState)
	{
		final int meta = getBlockMetadata();
		final int curMeta = (meta & 3) | (newState ? 4 : 0) | (fluidSlotDest.isFull() ? 8 : 0);
		if (curMeta != meta)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, curMeta, BlockFlags.UPDATE_AND_SYNC);
			markDirty();
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			if (workPulsar.update() == PulseStepper.State.PULSE)
			{
				if (fluidSlotSource.hasContent())
				{
					if (!fluidSlotDest.isFull())
					{
						fluidSlotDest.fill(fluidSlotSource.get(), true);
						markDirty();
					}
					setState(true);
				}
				else
				{
					setState(false);
				}
			}
		}
	}
}
