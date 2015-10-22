package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityDryer;

import net.minecraft.block.material.Material;

public class BlockDryer extends YATMBlockBaseMachine
{
	public BlockDryer()
	{
		super(Material.rock, TileEntityDryer.class);
		setBlockName("yatm.BlockDryer");
		setBlockTextureName("yatm:BlockDryer");
	}
}
