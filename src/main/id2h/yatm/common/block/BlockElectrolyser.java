package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityElectrolyser;

import net.minecraft.block.material.Material;

public class BlockElectrolyser extends YATMBlockBaseMachine
{
	public BlockElectrolyser()
	{
		super(Material.rock, TileEntityElectrolyser.class);
		setBlockName("yatm.BlockElectrolyser");
		setBlockTextureName("yatm:BlockElectrolyser");
	}
}
