package id2h.yatm.common.tileentity;

import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.EnergyStorage;

import net.minecraftforge.common.util.ForgeDirection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityElectrolyser extends TileEntity implements IEnergyReceiver
{
	protected final EnergyStorage energyStorage = new EnergyStorage(4096, 10);

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt.getCompoundTag("storage"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		final NBTTagCompound storageTag = new NBTTagCompound();
		energyStorage.writeToNBT(storageTag);
		nbt.setTag("storage", storageTag);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulated)
	{
		return energyStorage.receiveEnergy(maxReceive, simulated);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return energyStorage.getMaxEnergyStored();
	}
}
