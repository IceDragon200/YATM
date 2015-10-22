package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityCreativeEnergyCellBasic extends TileEntityCreativeEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new CreativeEnergyStorage(500000, 50);
	}
}
