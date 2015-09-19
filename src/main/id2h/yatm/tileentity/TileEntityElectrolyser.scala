package id2h.yatm.tileentity

import cofh.api.energy.IEnergyReceiver
import cofh.api.energy.EnergyStorage

import net.minecraftforge.common.util.ForgeDirection

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

class TileEntityElectrolyser extends TileEntity with IEnergyReceiver
{
	protected val energyStorage = new EnergyStorage(8192, 20)

	override def readFromNBT(nbt: NBTTagCompound) {
		super.readFromNBT(nbt)
		energyStorage.readFromNBT(nbt.getCompoundTag("storage"))
	}

	override def writeToNBT(nbt: NBTTagCompound) {
		super.writeToNBT(nbt)
		val storageTag = new NBTTagCompound()
		energyStorage.writeToNBT(storageTag)
		nbt.setTag("storage", storageTag)
	}

	override def canConnectEnergy(from: ForgeDirection): Boolean = true
	override def receiveEnergy(from: ForgeDirection, maxReceive: Int, simulated: Boolean): Int = this.energyStorage.receiveEnergy(maxReceive, simulated)
	override def getEnergyStored(from: ForgeDirection): Int = this.energyStorage.getEnergyStored()
	override def getMaxEnergyStored(from: ForgeDirection): Int = this.energyStorage.getMaxEnergyStored()
}
