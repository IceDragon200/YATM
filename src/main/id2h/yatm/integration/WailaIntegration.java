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
package id2h.yatm.integration;

import java.util.List;

import cofh.api.energy.IEnergyReceiver;

import id2h.yatm.common.block.YATMBlockBaseTile;
import id2h.yatm.common.tileentity.TileEntityEnergyCell;
import id2h.yatm.common.tileentity.YATMPoweredMachine;

import appeng.util.ReadableNumberConverter;
import appeng.util.IWideReadableNumberConverter;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WailaIntegration implements IWailaDataProvider
{
	private static final IWideReadableNumberConverter wideConverter = ReadableNumberConverter.INSTANCE;

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar reg)
	{
		final IWailaDataProvider instance = new WailaIntegration();

		reg.registerBodyProvider(instance, YATMBlockBaseTile.class);
		reg.registerNBTProvider(instance, YATMBlockBaseTile.class);
	}

	@Override
	@Optional.Method(modid = "Waila")
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return accessor.getStack();
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaHead(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return tooltip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		final TileEntity te = accessor.getTileEntity();
		final NBTTagCompound tag = accessor.getNBTData();
		if (te instanceof YATMPoweredMachine)
		{
			if (tag.getBoolean("WorkingState"))
			{
				tooltip.add("Machine Active");
			}
			else
			{
				tooltip.add("Machine Inactive");
			}
		}

		if (te instanceof IEnergyReceiver)
		{
			final long energy = tag.getLong("Energy");
			final long maxEnergy = tag.getLong("EnergyMax");


			tooltip.add("Energy: " +
				wideConverter.toWideReadableForm(energy) + "RF" +
				" / " +
				wideConverter.toWideReadableForm(maxEnergy) + "RF"
			);
		}

		if (te instanceof TileEntityEnergyCell)
		{
			final long maxIN = tag.getLong("InputRate");
			final long maxOUT = tag.getLong("OutputRate");
			tooltip.add("I/O: " +
				wideConverter.toWideReadableForm(maxIN) + "RF/t" +
				" / " +
				wideConverter.toWideReadableForm(maxOUT) + "RF/t"
			);
		}
		return tooltip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return tooltip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te instanceof YATMPoweredMachine)
		{
			final YATMPoweredMachine pm = (YATMPoweredMachine)te;
			tag.setBoolean("WorkingState", pm.getWorkingState());
		}

		if (te instanceof IEnergyReceiver)
		{
			final IEnergyReceiver er = (IEnergyReceiver)te;
			tag.setLong("EnergyMax", er.getMaxEnergyStored(ForgeDirection.UNKNOWN));
			tag.setLong("Energy", er.getEnergyStored(ForgeDirection.UNKNOWN));
		}

		if (te instanceof TileEntityEnergyCell)
		{
			final TileEntityEnergyCell energyCell = (TileEntityEnergyCell)te;
			tag.setLong("InputRate", energyCell.getMaxReceive());
			tag.setLong("OutputRate", energyCell.getMaxExtract());
		}
		return tag;
	}
}
