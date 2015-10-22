package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityAutoCrafter;

import net.minecraft.block.material.Material;

public class BlockAutoCrafter extends YATMBlockBaseMachine
{
	public BlockAutoCrafter()
	{
		super(Material.rock, TileEntityAutoCrafter.class);
		setBlockName("yatm.BlockAutoCrafter");
		setBlockTextureName("yatm:BlockAutoCrafter");
	}
}
