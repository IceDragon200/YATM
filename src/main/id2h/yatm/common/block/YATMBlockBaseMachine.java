package id2h.yatm.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public abstract class YATMBlockBaseMachine extends YATMBlockBaseTile
{
	public YATMBlockBaseMachine(Material mat, Class<? extends TileEntity> klass)
	{
		super(mat, klass);
	}
}
