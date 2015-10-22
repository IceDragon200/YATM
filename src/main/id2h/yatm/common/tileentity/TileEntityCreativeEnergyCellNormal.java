package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntityCreativeEnergyCellNormal extends TileEntityCreativeEnergyCell
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new CreativeEnergyStorage(2000000, 200);
	}
}
