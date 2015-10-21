package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMetalCrate extends Block
{
	public BlockMetalCrate()
	{
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.BlockMetalCrate");
		setBlockTextureName("yatm:BlockMetalCrate");
		setCreativeTab(CreativeTabsYATM.instance());
	}
}
