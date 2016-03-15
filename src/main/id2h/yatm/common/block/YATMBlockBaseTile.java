/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package id2h.yatm.common.block;

import java.util.Random;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.util.ItemUtils;
import id2h.yatm.common.tileentity.YATMBaseTile;
import id2h.yatm.common.tileentity.YATMEnergyProviderTile;
import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.util.BlockSides;
import id2h.yatm.util.GuiType;
import id2h.yatm.util.YATMPlatform;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class YATMBlockBaseTile extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	protected FlippableIcon[] icons;

	protected Random rand = new Random();
	protected GuiType guiType;
	protected Class<? extends TileEntity> tileEntityType;

	public YATMBlockBaseTile(Material material, Class<? extends TileEntity> klass)
	{
		super(material);
		setStepSound(Block.soundTypeStone);
		setHardness(4.0F);
		setCreativeTab(CreativeTabsYATM.instance());
		this.tileEntityType = klass;
	}

	protected void setGuiType(GuiType type)
	{
		this.guiType = type;
	}

	public GuiType getGuiType()
	{
		return guiType;
	}

	public boolean canRotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		if (canRotateBlock(world, x, y, z, side))
		{
			final int meta = world.getBlockMetadata(x, y, z);
			final int orn = meta & 3;
			final int extflag = meta & 12;
			// first normalize the orientation and then get its clockwise direction
			final int newMeta = BlockSides.CW[BlockSides.ORIENTATIONS4[orn][0] - 2] - 2;
			world.setBlockMetadataWithNotify(x, y, z, newMeta | extflag, BlockFlags.SYNC);
			return true;
		}
		return false;
	}

	protected void placeBlockByDirection(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		if (canRotateBlock(world, x, y, z, ForgeDirection.UNKNOWN))
		{
			final int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			int meta = 2;

			if (l == 0) meta = 1;
			else if (l == 1) meta = 2;
			else if (l == 2) meta = 0;
			else if (l == 3) meta = 3;
			world.setBlockMetadataWithNotify(x, y, z, meta, BlockFlags.SYNC);
		}
	}

	protected void restoreBlockStateFromItemStack(World world, int x, int y, int z, ItemStack stack)
	{
		if (world.isRemote) return;
		if (stack == null) return;
		final TileEntity te = getTileEntity(world, x, y, z);
		if (te != null && te instanceof YATMBaseTile)
		{
			final NBTTagCompound tag = stack.getTagCompound();
			if (tag != null && tag.hasKey("tiledata"))
			{
				((YATMBaseTile)te).readFromNBTForItem(tag.getCompoundTag("tiledata"));
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		placeBlockByDirection(world, x, y, z, entity, stack);
		restoreBlockStateFromItemStack(world, x, y, z, stack);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isRemote) return;

		final TileEntity te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			if (te instanceof YATMEnergyProviderTile)
			{
				((YATMEnergyProviderTile)te).onNeighborBlockChange(world, x, y, z, block);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected FlippableIcon optionalIcon(IIconRegister ir, String name, IIcon substitute)
	{
		// if the input is an flippable IIcon find the original.
		while (substitute instanceof FlippableIcon)
		{
			substitute = ((FlippableIcon)substitute).getOriginal();
		}

		if (substitute != null)
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

	@SideOnly(Side.CLIENT)
	public FlippableIcon optionalSubIcon(IIconRegister ir, String name, IIcon substitute)
	{
		return optionalIcon(ir, getTextureName() + name, substitute);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[12];

		final IIcon defaultIcon = optionalIcon(reg, getTextureName(), null);
		final IIcon sideIconOff = optionalSubIcon(reg, "/Side.Off", optionalSubIcon(reg, "/Side", defaultIcon));
		icons[0] = optionalSubIcon(reg, "/Bottom.Off", optionalSubIcon(reg, "/Bottom", defaultIcon));
		icons[1] = optionalSubIcon(reg, "/Top.Off", optionalSubIcon(reg, "/Top", defaultIcon));
		icons[2] = optionalSubIcon(reg, "/Back.Off", optionalSubIcon(reg, "/Back", sideIconOff));
		icons[3] = optionalSubIcon(reg, "/Front.Off", optionalSubIcon(reg, "/Front", sideIconOff));
		icons[4] = optionalSubIcon(reg, "/East.Off", optionalSubIcon(reg, "/East", sideIconOff));
		icons[5] = optionalSubIcon(reg, "/West.Off", optionalSubIcon(reg, "/West", sideIconOff));

		icons[6] = optionalSubIcon(reg, "/Bottom.On", icons[0]);
		icons[7] = optionalSubIcon(reg, "/Top.On", icons[1]);
		icons[8] = optionalSubIcon(reg, "/Back.On",  optionalSubIcon(reg, "/Side.On", icons[2]));
		icons[9] = optionalSubIcon(reg, "/Front.On", optionalSubIcon(reg, "/Side.On", icons[3]));
		icons[10] = optionalSubIcon(reg, "/East.On", optionalSubIcon(reg, "/Side.On", icons[4]));
		icons[11] = optionalSubIcon(reg, "/West.On", optionalSubIcon(reg, "/Side.On", icons[5]));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		final boolean online = (meta & 4) == 4;
		final int onlineOffset = online ? 6 : 0;
		if (side > 1)
		{
			final int orientation = meta & 3;
			final int newSide = BlockSides.ORIENTATIONS4[orientation][side - 2];
			return icons[newSide + onlineOffset];
		}
		else
		{
			return icons[side + onlineOffset];
		}
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

	/**
	 * Drops the block as an item and replaces it with air
	 *
	 * @param world - world to drop in
	 * @param x - x Coord
	 * @param y - y Coord
	 * @param z - z Coord
	 */
	public void fellBlockAsItem(World world, int x, int y, int z)
	{
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
	}

	public boolean wrenchBlock(World world, int x, int y, int z, EntityPlayer player, ItemStack wrench)
	{
		if (player != null)
		{
			if (ItemUtils.canWrench(wrench, player, x, y, z))
			{
				if (player.isSneaking())
				{
					if (!world.isRemote)
					{
						fellBlockAsItem(world, x, y, z);
						ItemUtils.wrenchUsed(wrench, player, x, y, z);
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean tryWrenchItem(EntityPlayer player, World world, int x, int y, int z)
	{
		if (player == null) return false;
		final ItemStack is = player.inventory.getCurrentItem();
		return wrenchBlock(world, x, y, z, player, is);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		if (tryWrenchItem(p, world, x, y, z)) return true;
		if (getGuiType() != null)
		{
			final TileEntity te = this.getTileEntity(world, x, y, z);
			if (te != null && !p.isSneaking())
			{
				YATMPlatform.openGui(p, te, ForgeDirection.getOrientation(side), getGuiType());
				return true;
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int unused)
	{
		if (tileEntityType != null)
		{
			try
			{
				return tileEntityType.newInstance();
			}
			catch (InstantiationException e)
			{
				throw new IllegalStateException("Failed to create a new instance of an illegal class " + this.tileEntityType, e);
			}
			catch (IllegalAccessException e)
			{
				throw new IllegalStateException("Failed to create a new instance of " + this.tileEntityType + ", because lack of permissions", e);
			}
		}
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6)
	{
		final TileEntity te = getTileEntity(world, x, y, z);
		if (te instanceof IInventory)
		{
			final IInventory inv = (IInventory)te;
			for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1)
			{
				final ItemStack itemstack = inv.getStackInSlot(i1);
				ItemUtils.spawnBrokenItemStack(world, x, y, z, itemstack, rand);
			}
		}

		super.breakBlock(world, x, y, z, block, par6);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int a, int b)
	{
		super.onBlockEventReceived(world, x, y, z, a, b);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(a, b) : false;
	}
}
