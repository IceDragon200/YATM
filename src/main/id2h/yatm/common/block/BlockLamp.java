package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockLamp extends Block
{
	public BlockLamp()
	{
		super(Material.glass);
		setStepSound(Block.soundTypeGlass);
		setHardness(2.0F);
		setResistance(5.0F);
		setLightLevel(1.0f);
		setBlockName("yatm.BlockLamp");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon("yatm:BlockLamp.On");
	}
}
