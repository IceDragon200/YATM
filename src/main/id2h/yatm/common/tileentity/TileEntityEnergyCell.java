package id2h.yatm.common.tileentity;

import id2h.yatm.util.BlockFlags;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergyCell extends YATMPoweredTile implements IEnergyHandler
{
	protected boolean needCacheRebuild = true;
	protected boolean needUpdate = true;
	protected int lastMeta = -1;
	protected TileEntity[] tileCache = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];

	@Override
	protected abstract EnergyStorage createEnergyStorage();

	private void rebuildTileCache()
	{
		for (int i = 0; i < tileCache.length; ++i)
		{
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
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

	private void updateCells()
	{
		for (int i = 0; i < tileCache.length; ++i)
		{
			final TileEntity te = tileCache[i];
			if (te != null)
			{
				final ForgeDirection dir = ForgeDirection.getOrientation(i);
				if (te instanceof TileEntityEnergyCell)
				{
					final TileEntityEnergyCell cell = (TileEntityEnergyCell)te;
					if (cell.getEnergyStored(dir.getOpposite()) < getEnergyStored(dir))
					{
						final int diff = cell.receiveEnergy(dir.getOpposite(), extractEnergy(dir, energyStorage.getMaxExtract() / 2, true), false);
						if (diff > 0)
						{
							extractEnergy(dir, diff, false);
						}
					}
				}
				else if (te instanceof IEnergyReceiver)
				{
					final IEnergyReceiver receiver = (IEnergyReceiver)te;
					final int diff = receiver.receiveEnergy(dir.getOpposite(), extractEnergy(dir, energyStorage.getMaxExtract(), true), false);
					if (diff > 0)
					{
						extractEnergy(dir, diff, false);
					}
				}
			}
		}
	}

	private void updateBlockMeta()
	{
		final int newMeta = calculateEnergyMeta();
		if (lastMeta != newMeta)
		{
			lastMeta = newMeta;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lastMeta, BlockFlags.UPDATE_CLIENT);
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!worldObj.isRemote)
		{
			if (needCacheRebuild)
			{
				needCacheRebuild = false;
				rebuildTileCache();
			}

			if (needUpdate)
			{
				needUpdate = false;
				updateBlockMeta();
				this.markDirty();
			}

			updateCells();
			needUpdate = true;
		}
	}
}
