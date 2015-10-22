package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityEnergyCellNormal extends TileEntityEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(2000000, 200);
	}
}
