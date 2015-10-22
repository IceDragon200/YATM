package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityMixer extends YATMPoweredMachine
{
	@Override
	protected void createEnergyStorage()
	{
		energyStorage = new EnergyStorage(4000, 10);
	}
}
