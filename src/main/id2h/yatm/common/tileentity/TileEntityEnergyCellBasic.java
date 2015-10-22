package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityEnergyCellBasic extends TileEntityEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(500000, 50);
	}
}
