package id2h.yatm.integration;

import java.util.List;

import cofh.api.energy.IEnergyReceiver;

import id2h.yatm.common.block.YATMBlockBaseTile;
import id2h.yatm.common.tileentity.TileEntityEnergyCell;

import cpw.mods.fml.common.Optional;

import net.minecraftforge.common.util.ForgeDirection;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WailaIntegration implements IWailaDataProvider
{
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
		if (te instanceof IEnergyReceiver)
		{
			final long energy = tag.getLong("energy");
			final long maxEnergy = tag.getLong("maxenergy");
			tooltip.add("Online: " + (energy > 0));
			tooltip.add("Energy: " + energy + " / " + maxEnergy + " RF");
		}
		if (te instanceof TileEntityEnergyCell)
		{
			final long maxIN = tag.getLong("maxIN");
			final long maxOUT = tag.getLong("maxOUT");
			tooltip.add("I/O: " + maxIN + " / " + maxOUT + " RF/t");
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
		if (te instanceof IEnergyReceiver)
		{
			final IEnergyReceiver er = (IEnergyReceiver)te;
			tag.setLong("maxenergy", er.getMaxEnergyStored(ForgeDirection.UNKNOWN));
			tag.setLong("energy", er.getEnergyStored(ForgeDirection.UNKNOWN));
		}
		if (te instanceof TileEntityEnergyCell)
		{
			final TileEntityEnergyCell energyCell = (TileEntityEnergyCell)te;
			tag.setLong("maxIN", energyCell.getMaxReceive());
			tag.setLong("maxOUT", energyCell.getMaxExtract());
		}
		return tag;
	}
}
