package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.common.tileentity.TileEntityMixer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMixer extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockMixer()
	{
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.BlockMixer");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[3];
		icons[0] = reg.registerIcon("yatm:BlockMixerBottom");
		icons[1] = reg.registerIcon("yatm:BlockMixerTop");
		icons[2] = reg.registerIcon("yatm:BlockMixerSide");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0)
		{
			return icons[0];
		}
		else if (side == 1)
		{
			return icons[1];
		}
		return icons[2];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int unused)
	{
		return new TileEntityMixer();
	}
}
