package id2h.yatm.common.block;

import net.minecraft.block.material.Material;

// 8 direction solving
public class BlockDecorative8 extends BlockDecorativeBase
{
	public BlockDecorative8(Material material)
	{
		super(material);
		this.use8Dir = true;
	}
}
