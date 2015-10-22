package id2h.yatm.common.block;

import id2h.yatm.client.renderer.RenderCagedEnergyCell;
import id2h.yatm.common.tileentity.TileEntityCagedEnergyCell;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockCagedEnergyCell extends YATMBlockBaseTile
{
	public BlockCagedEnergyCell()
	{
		super(Material.rock, TileEntityCagedEnergyCell.class);
		setBlockName("yatm.BlockCagedEnergyCell");
		setBlockTextureName("yatm:BlockCagedEnergyCell");
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isRemote()) return;

		final TileEntityCagedEnergyCell te = getTileEntity<TileEntityCagedEnergyCell>(world, x, y, z);
		if (te != null)
		{
			te.onNeighborBlockChange(world, x, y, z, block);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return RenderCagedEnergyCell.id;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[8];
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = optionalIcon(reg, getTextureName() + "/Stage" + i, null);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= 0 && meta < icons.length)
		{
			return icons[meta];
		}
		return icons[7];
	}
}
