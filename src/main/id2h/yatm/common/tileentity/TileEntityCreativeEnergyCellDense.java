package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityCreativeEnergyCellDense extends TileEntityCreativeEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new CreativeEnergyStorage(8000000, 800);
	}
}
