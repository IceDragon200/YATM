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

import id2h.yatm.common.tileentity.energy.YATMEnergyStorage;
import id2h.yatm.common.tileentity.feature.IEnergyGridSync;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySolarPanel extends YATMEnergyProviderTile
{
	public int lastEnergyGain;

	public TileEntitySolarPanel()
	{
		super();
		setEnergySyncPriority(200);
	}

	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(100000, 10);
	}

	@Override
	public int getEnergySyncAmount(ForgeDirection dir, IEnergyGridSync other)
	{
		// Solar panels can only sync at half their rate if their target
		// has more energy than it does
		if (other.getEnergyStored(dir.getOpposite()) > getEnergyStored(dir))
		{
			return energyStorage.getMaxExtract() / 2;
		}
		// Otherwise it syncs at its original rate
		return energyStorage.getMaxExtract();
	}

	@Override
	public boolean checkEnergySyncLevels(ForgeDirection dir, IEnergyGridSync other)
	{
		if (getEnergyStored(dir) > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	protected void updateEnergyProvider()
	{
		if (!worldObj.provider.hasNoSky)
		{
			float f = worldObj.getCelestialAngleRadians(1.0F);

			if (f < (float)Math.PI)
			{
				f += (0.0F - f) * 0.2F;
			}
			else
			{
				f += (((float)Math.PI * 2F) - f) * 0.2F;
			}

			int lv = worldObj.getSavedLightValue(EnumSkyBlock.Sky, xCoord, yCoord, zCoord) - worldObj.skylightSubtracted;
			lv = Math.round((float)lv * MathHelper.cos(f));

			if (lv < 0)
			{
				lv = 0;
			}

			if (lv > 15)
			{
				lv = 15;
			}

			final float r = (float)lv / 15.0f;
			lastEnergyGain = (int)((float)energyStorage.getMaxReceive() * r);
			if (lastEnergyGain > 0)
			{
				receiveEnergy(ForgeDirection.UNKNOWN, lastEnergyGain, false);
				markForBlockUpdate();
			}
		}
		super.updateEnergyProvider();
	}
}
