package id2h.yatm.common.tileentity;

import id2h.yatm.util.BlockFlags;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.EnergyStorage;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCagedEnergyCell extends YATMPoweredTile implements IEnergyHandler
{
	protected int resyncTime;
	protected boolean needUpdate = true;
	protected boolean needCacheRebuild = true;
	protected int lastMeta = -1;
	protected TileEntity[] tileCache = new TileEntity[ForgeDirection.VALID_DIRECTIONS];

	private void rebuildTileCache()
	{
		for (int i = 0; i < tileCache.length; ++i)
		{
			final ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			final int fx = xCoord + dir.offsetX;
			final int fy = yCoord + dir.offsetY;
			final int fz = zCoord + dir.offsetZ;
			tileCache[i] = worldObj.getTileEntity(fx, fy, fz);
		}
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		needCacheRebuild = true;
	}

	@Override
	protected void createEnergyStorage()
	{
		energyStorage = new EnergyStorage(12000, 100);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	public int calculateEnergyMeta()
	{
		final int stored = getEnergyStored(ForgeDirection.UNKNOWN);
		final int max = getMaxEnergyStored(ForgeDirection.UNKNOWN);
		if (max <= 0) return 0;
		return stored * 8 / max;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (needCacheRebuild)
		{
			needCacheRebuild = false;
			rebuildTileCache();
		}

		if (needUpdate)
		{
			needUpdate = false;
			final int newMeta = calculateEnergyMeta();
			if (lastMeta != newMeta)
			{
				lastMeta = newMeta;
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, BlockFlags.SEND_TO_CLIENT);
			}
			this.markDirty();
		}

		if (!worldObj.isRemote)
		{
			resyncTime--;
			if (resyncTime < 0)
			{
				resyncTime = 10;
				for (int i = 0; i < tileCache.length; ++i)
				{
					final TileEntity te = tileCache[i];
					if (te instanceof TileEntityCagedEnergyCell)
					{
						final TileEntityCagedEnergyCell cell = (TileEntityCagedEnergyCell)te;
						if (cell.getEnergyStored(dir.getOpposite()) < getEnergyStored(dir))
						{
							cell.receiveEnergy(dir.getOpposite(), extractEnergy(dir, 50, false), false);
						}
					}
				}
				needUpdate = true;
			}
		}
	}
}
