package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityCrusher;
import net.minecraft.block.material.Material;

public class BlockCrusher extends YATMBlockBaseMachine
{
	public BlockCrusher()
	{
		super(Material.rock, TileEntityCrusher.class);
		setBlockName("yatm.BlockCrusher");
		setBlockTextureName("yatm:BlockCrusher");
	}
}
