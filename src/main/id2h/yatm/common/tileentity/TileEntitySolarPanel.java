package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntitySolarPanel extends YATMPoweredTile
{
	@Override
	protected EnergyStorage createEnergyStorage()
	{
		return new EnergyStorage(4000, 10);
	}
}
