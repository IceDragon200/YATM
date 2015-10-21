package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;

import appeng.core.sync.GuiBridge;
import appeng.util.Platform;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAutoGrinder extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockAutoGrinder()
	{
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(3.2F);
		setBlockName("yatm.BlockAutoGrinder");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[3];
		icons[0] = reg.registerIcon("yatm:BlockAutoGrinderBottom");
		icons[1] = reg.registerIcon("yatm:BlockAutoGrinderTop");
		icons[2] = reg.registerIcon("yatm:BlockAutoGrinderSide");
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

	public TileEntityAutoGrinder getTileEntity(World world, int x, int y, int z)
	{
		return (TileEntityAutoGrinder)world.getTileEntity(x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		final TileEntityAutoGrinder tg = this.getTileEntity(world, x, y, z);
		if (tg != null && !p.isSneaking())
		{
			Platform.openGUI(p, tg, ForgeDirection.getOrientation(side), GuiBridge.GUI_GRINDER);
			return true;
		}
		return false;
	}

	public TileEntity createNewTileEntity(World world, int unused)
	{
		return new TileEntityAutoGrinder();
	}
}
