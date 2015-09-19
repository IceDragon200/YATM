package id2h.yatm.integration

import java.util.List

import cofh.api.energy.IEnergyReceiver

import id2h.yatm.block.BlockAutoCrafter
import id2h.yatm.block.BlockDryer
import id2h.yatm.block.BlockElectrolyser

import cpw.mods.fml.common.Optional

import net.minecraftforge.common.util.ForgeDirection

import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import mcp.mobius.waila.api.IWailaDataProvider
import mcp.mobius.waila.api.IWailaRegistrar

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

object WailaIntegration
{
	@Optional.Method(modid = "Waila")
	def register(reg: IWailaRegistrar) {
		val instance = new WailaIntegration()

        reg.registerBodyProvider(instance, classOf[BlockAutoCrafter])
        reg.registerNBTProvider(instance, classOf[BlockAutoCrafter])
        reg.registerBodyProvider(instance, classOf[BlockDryer])
        reg.registerNBTProvider(instance, classOf[BlockDryer])
        reg.registerBodyProvider(instance, classOf[BlockElectrolyser])
        reg.registerNBTProvider(instance, classOf[BlockElectrolyser])
	}
}

class WailaIntegration extends IWailaDataProvider
{
	@Optional.Method(modid = "Waila")
	override def getWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack = accessor.getStack()

	@Optional.Method(modid = "Waila")
	override def getWailaHead(itemStack: ItemStack, tooltip: List[String], accessor: IWailaDataAccessor, config: IWailaConfigHandler): List[String] = tooltip

    @Optional.Method(modid = "Waila")
    override def getWailaBody(itemStack: ItemStack, tooltip: List[String], accessor: IWailaDataAccessor, config: IWailaConfigHandler): List[String] = {
    	accessor.getTileEntity() match {
    		case er: IEnergyReceiver =>
                val tag = accessor.getNBTData()
                val energy = tag.getInteger("energy")
                val maxEnergy = tag.getInteger("maxenergy")
                tooltip.add("Online: " + (energy > 0))
                tooltip.add("Energy: " + energy + " / " + maxEnergy + " RF")
    	}
        tooltip
    }

    @Optional.Method(modid = "Waila")
    override def getWailaTail(itemStack: ItemStack, tooltip: List[String], accessor: IWailaDataAccessor, config: IWailaConfigHandler): List[String] = tooltip

    @Optional.Method(modid = "Waila")
    override def getNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound, world: World, x: Int, y: Int, z: Int): NBTTagCompound = {
        te match {
            case er: IEnergyReceiver =>
                tag.setLong("maxenergy", er.getMaxEnergyStored(ForgeDirection.UNKNOWN))
                tag.setLong("energy", er.getEnergyStored(ForgeDirection.UNKNOWN))
        }
        tag
    }
}
