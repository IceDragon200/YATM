package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityMixer;

import net.minecraft.block.material.Material;

public class BlockMixer extends YATMBlockBaseMachine
{
	public BlockMixer()
	{
		super(Material.rock, TileEntityMixer.class);
		setBlockName("yatm.BlockMixer");
		setBlockTextureName("yatm:BlockMixer");
	}
}
