/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package io.polyfox.yatm.integration.waila;

import java.util.List;

import io.polyfox.yatm.api.power.IPowerStorageTile;
import io.polyfox.yatm.common.tileentity.TileEntitySolarPanel;
import io.polyfox.yatm.common.tileentity.TileEntitySpringWoundCrank;
import io.polyfox.yatm.common.tileentity.TilePowered;
import io.polyfox.yatm.common.tileentity.TilePoweredMachine;
import io.polyfox.yatm.common.tileentity.TilePowerProviderBase;
import io.polyfox.yatm.security.ISecuredEntity;

import appeng.util.ReadableNumberConverter;
import appeng.util.IWideReadableNumberConverter;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class YATMDataProvider implements IWailaDataProvider
{
	private static final IWideReadableNumberConverter wideConverter = ReadableNumberConverter.INSTANCE;

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
		if (te instanceof TileEntitySpringWoundCrank)
		{
			final int w = tag.getInteger("winds");
			final int m = tag.getInteger("max_winds");
			tooltip.add(String.format("Winds: %d / %d", w, m));
		}

		if (te instanceof TilePowered)
		{
			if (tag.hasKey("owner_name"))
			{
				final String ownerName = tag.getString("owner_name");
				tooltip.add("Owner: " + ownerName);
			}
		}

		if (te instanceof TilePoweredMachine)
		{
			if (tag.getBoolean("working_state"))
			{
				tooltip.add("Machine Active");
			}
			else
			{
				tooltip.add("Machine Inactive");
			}
		}

		if (te instanceof IPowerStorageTile)
		{
			final long power = tag.getLong("power");
			final long powerMax = tag.getLong("power_max");

			tooltip.add("Power: " +
				wideConverter.toWideReadableForm(power) + " YW" +
				" / " +
				wideConverter.toWideReadableForm(powerMax) + " YW"
			);
		}

		if (te instanceof TilePowerProviderBase)
		{
			final int level = tag.getInteger("get_energy_level");
			final long maxIn = tag.getLong("input_rate");
			final long maxOut = tag.getLong("output_rate");
			tooltip.add("Sync Level: " + level);
			tooltip.add("Max I/O: " +
				wideConverter.toWideReadableForm(maxIn) + " YW/t" +
				" / " +
				wideConverter.toWideReadableForm(maxOut) + " YW/t"
			);
		}

		if (te instanceof TileEntitySolarPanel)
		{
			tooltip.add("Last Gain: " + tag.getLong("last_energy_gain") + " YW/t");
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
		if (te instanceof TileEntitySpringWoundCrank)
		{
			final TileEntitySpringWoundCrank swc = (TileEntitySpringWoundCrank)te;
			tag.setInteger("winds", swc.winds);
			tag.setInteger("max_winds", swc.maxWinds);
		}

		if (te instanceof TilePowered)
		{
			final TilePowered tp = (TilePowered)te;
			final EntityPlayer owner = tp.getOwnerPlayer();
			if (owner != null)
			{
				tag.setString("owner_name", owner.getDisplayName());
			}
		}

		if (te instanceof TilePoweredMachine)
		{
			final TilePoweredMachine pm = (TilePoweredMachine)te;
			tag.setBoolean("working_state", pm.getWorkingState());
		}

		if (te instanceof IPowerStorageTile)
		{
			final IPowerStorageTile pt = (IPowerStorageTile)te;
			tag.setLong("power_max", pt.getPowerCapacityFrom(ForgeDirection.UNKNOWN));
			tag.setLong("power", pt.getPowerStoredFrom(ForgeDirection.UNKNOWN));
		}

		if (te instanceof TilePowerProviderBase)
		{
			final TilePowerProviderBase prov = (TilePowerProviderBase)te;
			tag.setInteger("get_energy_level", prov.getPowerSyncLevelFrom(ForgeDirection.UNKNOWN));
			tag.setLong("input_rate", prov.getMaxReceive());
			tag.setLong("output_rate", prov.getMaxExtract());
		}

		if (te instanceof TileEntitySolarPanel)
		{
			final TileEntitySolarPanel sp = (TileEntitySolarPanel)te;
			tag.setLong("last_energy_gain", sp.lastPowerGain);
		}
		return tag;
	}
}
