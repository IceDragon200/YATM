package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityEnergyCellDense extends TileEntityEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(8000000, 800);
	}
}
