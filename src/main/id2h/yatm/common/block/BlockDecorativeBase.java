package id2h.yatm.common.block;

import id2h.yatm.client.util.ConnectedTextureSolver;
import id2h.yatm.creativetab.CreativeTabsYATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/* Base class for Connected Texture Decorative Blocks */
public abstract class BlockDecorativeBase extends Block
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	@SideOnly(Side.CLIENT)
	protected IIcon noIcon;

	protected boolean use8Dir;

	public BlockDecorativeBase(Material material)
	{
		super(material);
		opaque = true;
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		if (use8Dir)
		{
			icons = new IIcon[47];
		}
		else
		{
			icons = new IIcon[16];
		}
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = reg.registerIcon(getTextureName() + "/" + i);
		}
		noIcon = reg.registerIcon("yatm:blank");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		final int value = ConnectedTextureSolver.iconForSide(world, x, y, z, this, side, use8Dir);

		if (!isOpaqueCube())
		{
			if ((value & ConnectedTextureSolver.OBSTRUCTED) == ConnectedTextureSolver.OBSTRUCTED)
			{
				return noIcon;
			}
		}

		return icons[value & ConnectedTextureSolver.MASK];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[0];
	}

	public Block setOpaque(boolean state)
	{
		this.opaque = state;
		return this;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return opaque;
	}
}
