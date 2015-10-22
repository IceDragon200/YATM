package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityDryer extends YATMPoweredMachine
{
	@Override
	protected void createEnergyStorage()
	{
		energyStorage = new EnergyStorage(4000, 10);
	}
}
