package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityElectrolyser extends YATMPoweredMachine
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(4000, 10);
	}
}
