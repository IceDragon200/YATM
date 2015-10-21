package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAutoCrafter extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockAutoCrafter()
	{
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.BlockAutoCrafter");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[3];

		icons[0] = reg.registerIcon("yatm:BlockAutocrafterBottom");
		icons[1] = reg.registerIcon("yatm:BlockAutocrafterTop");
		icons[2] = reg.registerIcon("yatm:BlockAutocrafterSide");
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

	public TileEntityAutoCrafter getTileEntity(World world, int x, int y, int z)
	{
		return (TileEntityAutoCrafter)world.getTileEntity(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		final TileEntityAutoCrafter tg = this.getTileEntity(world, x, y, z);
		if (tg != null && !p.isSneaking())
		{

			//Platform.openGUI(p, tg, ForgeDirection.getOrientation( side ), GuiBridge.GUI_GRINDER)
			return true;
		}
		return false;
	}

	public TileEntity createNewTileEntity(World world, int unused)
	{
		return new TileEntityAutoCrafter();
	}
}
