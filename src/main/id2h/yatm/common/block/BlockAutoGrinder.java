package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityAutoGrinder;

import net.minecraft.block.material.Material;

public class BlockAutoGrinder extends YATMBlockBaseMachine
{
	public BlockAutoGrinder()
	{
		super(Material.rock, TileEntityAutoGrinder.class);
		setBlockName("yatm.BlockAutoGrinder");
		setBlockTextureName("yatm:BlockAutoGrinder");
	}
}
