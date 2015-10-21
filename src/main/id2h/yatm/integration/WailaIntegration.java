package id2h.yatm.integration;

import java.util.List;

import cofh.api.energy.IEnergyReceiver;

import id2h.yatm.common.block.BlockAutoCrafter;
import id2h.yatm.common.block.BlockDryer;
import id2h.yatm.common.block.BlockElectrolyser;

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

		reg.registerBodyProvider(instance, BlockAutoCrafter.class);
		reg.registerNBTProvider(instance, BlockAutoCrafter.class);
		reg.registerBodyProvider(instance, BlockDryer.class);
		reg.registerNBTProvider(instance, BlockDryer.class);
		reg.registerBodyProvider(instance, BlockElectrolyser.class);
		reg.registerNBTProvider(instance, BlockElectrolyser.class);
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
		if (te instanceof IEnergyReceiver)
		{
			final NBTTagCompound tag = accessor.getNBTData();
			final int energy = tag.getInteger("energy");
			final int maxEnergy = tag.getInteger("maxenergy");
			tooltip.add("Online: " + (energy > 0));
			tooltip.add("Energy: " + energy + " / " + maxEnergy + " RF");
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

		return tag;
	}
}
