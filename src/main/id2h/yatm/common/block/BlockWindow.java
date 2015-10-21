package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockWindow extends Block
{
	public BlockWindow()
	{
		super(Material.glass);
		setStepSound(Block.soundTypeGlass);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.BlockWindow");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon("yatm:BlockVentWindow");
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
