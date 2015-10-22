package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

import net.minecraft.nbt.NBTTagCompound;

public class CreativeEnergyStorage extends EnergyStorage
{
	public CreativeEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		super(capacity, maxReceive, maxExtract);
	}

	public CreativeEnergyStorage(int capacity, int maxTransfer)
	{
		this(capacity, maxTransfer, maxTransfer);
	}

	public CreativeEnergyStorage(int capacity)
	{
		this(capacity, capacity, capacity);
	}

	@Override
	public CreativeEnergyStorage readFromNBT(NBTTagCompound nbt)
	{
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		return nbt;
	}

	@Override
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}

	/**
	 * This function is included to allow for server -&gt; client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers
	 * are guaranteed to have it.
	 *
	 * @param energy
	 */
	@Override
	public void setEnergyStored(int energy)
	{

	}

	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the CreativeEnergyStorage. Do not rely on this
	 * externally, as not all IEnergyHandlers are guaranteed to have it.
	 *
	 * @param energy
	 */
	@Override
	public void modifyEnergyStored(int energy)
	{

	}

	/* ICreativeEnergyStorage */
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		final int energyExtracted = Math.min(capacity, Math.min(this.maxExtract, maxExtract));
		return energyExtracted;
	}

	@Override
	public int getEnergyStored()
	{
		return capacity;
	}

	@Override
	public int getMaxEnergyStored() {

		return capacity;
	}
}
