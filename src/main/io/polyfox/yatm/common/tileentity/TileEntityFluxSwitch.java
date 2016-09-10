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
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import io.polyfox.yatm.api.power.IPowerGridSync;
import io.polyfox.yatm.api.power.PowerStorage;
import io.polyfox.yatm.api.power.PowerSyncDirection;
import io.polyfox.yatm.api.power.PowerThrottle;
import io.polyfox.yatm.common.inventory.ContainerFluxSwitch;
import io.polyfox.yatm.util.BlockFacing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluxSwitch extends TilePowerProviderBase implements IInteractionObject
{
	public boolean online;

	@Override
	protected PowerStorage createPowerStorage()
	{
		return new PowerStorage(400000);
	}

	@Override
	protected PowerThrottle createPowerThrottle()
	{
		return new PowerThrottle(powerStorage, 4000, 4000);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:flux_switch";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFluxSwitch(playerInventory, this);
	}

	public BlockFacing getBlockFacing()
	{
		return BlockFacing.byHorizontalIndex(getBlockMetadata() & 3);
	}

	protected void refreshState()
	{
		final int meta = getBlockMetadata()	& 0xF;
		final boolean powered = powerStorage.getAmount() > 0;
		final int newMeta = (meta & 3) | (online ? 4 : 0) | (powered ? 8 : 0);
		if (meta != newMeta)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, BlockFlags.UPDATE_AND_SYNC);
			markDirty();
		}
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			this.online = worldObj.getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;
			refreshState();
		}
		super.updateEntity();
	}

	// @todo - FIXME RF
	//@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		final BlockFacing facing = getBlockFacing();
		if (facing.rotateClockwiseOnY().getForgeDirection().equals(from) ||
			facing.rotateCounterClockwiseOnY().getForgeDirection().equals(from)) {
			return true;
		}
		return false;
	}

	/*@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulated)
	{
		if (online && canConnectEnergy(from))
		{
			return super.extractEnergy(from, maxExtract, simulated);
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulated)
	{
		if (online && canConnectEnergy(from))
		{
			return super.receiveEnergy(from, maxReceive, simulated);
		}
		return 0;
	}*/

	@Override
	public long getPowerSyncAmount(ForgeDirection from, IPowerGridSync other)
	{
		if (canConnectEnergy(from))
		{
			return super.getPowerSyncAmount(from, other);
		}
		return 0;
	}

	@Override
	public PowerSyncDirection getPowerSyncDirectionFor(ForgeDirection from, IPowerGridSync other)
	{
		if (online && canConnectEnergy(from))
		{
			return super.getPowerSyncDirectionFor(from, other);
		}
		return PowerSyncDirection.NONE;
	}
}
