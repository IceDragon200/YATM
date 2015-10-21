package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.common.tileentity.TileEntityDryer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDryer extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	public BlockDryer()
	{
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.BlockDryer");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	public TileEntityDryer getTileEntity(IBlockAccess world, int x, int y, int z)
	{
		return (TileEntityDryer)world.getTileEntity(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[4];
		icons[0] = reg.registerIcon("yatm:BlockDryerBottom");
		icons[1] = reg.registerIcon("yatm:BlockDryerTop");
		icons[2] = reg.registerIcon("yatm:BlockDryerSide.Offline");
		icons[3] = reg.registerIcon("yatm:BlockDryerSide");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		if (side == 0)
		{
			return icons[0];
		}
		else if (side == 1)
		{
			return icons[1];
		}
		else
		{
			final TileEntityDryer te = getTileEntity(world, x, y, z);
			//if (te != null && te.isOnline(ForgeDirection.getOrientation(side))) {
			//	return icons(3)
			//}
			return icons[3];
		}
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		final TileEntityDryer te = this.getTileEntity(world, x, y, z);
		if (te != null && !p.isSneaking())
		{
			//
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int unused)
	{
		return new TileEntityDryer();
	}
}
