package id2h.yatm.common.tileentity;

import cofh.api.energy.EnergyStorage;

public class TileEntitySolarPanel extends YATMPoweredTile
{
	@Override
	protected void createEnergyStorage()
	{
		energyStorage = new EnergyStorage(4000, 10);
	}
}
