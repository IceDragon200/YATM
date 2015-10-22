package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityCrusher extends YATMPoweredMachine
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(16000, 10);
	}

	@Override
	public int powerRequiredToWork()
	{
		return 100;
	}

	@Override
	public boolean canWork()
	{
		return false;
	}

	@Override
	public boolean doWork()
	{
		return true;
	}
}
