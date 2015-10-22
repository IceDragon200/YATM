package id2h.yatm.common.block;

import id2h.yatm.creativetab.CreativeTabsYATM;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//import net.minecraftforge.common.util.ForgeDirection;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class YATMBlockBaseTile extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	protected FlippableIcon[] icons;

	protected Class<? extends TileEntity> tileEntityType;

	public YATMBlockBaseTile(Material material, Class<? extends TileEntity> klass)
	{
		super(material);
		setStepSound(Block.soundTypeStone);
		setHardness(4.0F);
		setCreativeTab(CreativeTabsYATM.instance());
		this.tileEntityType = klass;
	}

	@SideOnly(Side.CLIENT)
	protected FlippableIcon optionalIcon( IIconRegister ir, String name, IIcon substitute )
	{
		// if the input is an flippable IIcon find the original.
		while( substitute instanceof FlippableIcon )
		{
			substitute = ( (FlippableIcon) substitute ).getOriginal();
		}

		if( substitute != null )
		{
			try
			{
				ResourceLocation resLoc = new ResourceLocation( name );
				resLoc = new ResourceLocation( resLoc.getResourceDomain(), String.format( "%s/%s%s", "textures/blocks", resLoc.getResourcePath(), ".png" ) );

				final IResource res = Minecraft.getMinecraft().getResourceManager().getResource( resLoc );
				if( res != null )
				{
					return new FlippableIcon( ir.registerIcon( name ) );
				}
			}
			catch( Throwable e )
			{
				return new FlippableIcon( substitute );
			}
		}

		return new FlippableIcon( ir.registerIcon( name ) );
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[6];
		this.blockIcon = optionalIcon(reg, getTextureName(), null);
		final IIcon sideIcon = optionalIcon(reg, getTextureName() + "/Side", blockIcon);
		icons[0] = optionalIcon(reg, getTextureName() + "/Bottom", this.blockIcon);
		icons[1] = optionalIcon(reg, getTextureName() + "/Top", this.blockIcon);
		icons[2] = optionalIcon(reg, getTextureName() + "/Back", sideIcon);
		icons[3] = optionalIcon(reg, getTextureName() + "/Front", sideIcon);
		icons[4] = optionalIcon(reg, getTextureName() + "/East", sideIcon);
		icons[5] = optionalIcon(reg, getTextureName() + "/West", sideIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[side];
	}

	public <T extends TileEntity> T getTileEntity(World world, int x, int y, int z)
	{
		final TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			if (tileEntityType.isInstance(te))
			{
				return (T)te;
			}
			else
			{
				// warn
			}
		}
		return null;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		final TileEntity te = this.getTileEntity(world, x, y, z);
		//if (te != null && !p.isSneaking())
		//{
		//	Platform.openGUI(p, te, ForgeDirection.getOrientation(side), GuiBridge.GUI_GRINDER);
		//	return true;
		//}
		return false;
	}

	public TileEntity createNewTileEntity(World world, int unused)
	{
		if (tileEntityType != null)
		{
			try
			{
				return tileEntityType.newInstance();
			}
			catch( InstantiationException e )
			{
				throw new IllegalStateException( "Failed to create a new instance of an illegal class " + this.tileEntityType, e );
			}
			catch( IllegalAccessException e )
			{
				throw new IllegalStateException( "Failed to create a new instance of " + this.tileEntityType + ", because lack of permissions", e );
			}
		}
		return null;
	}
}
