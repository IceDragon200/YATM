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
package io.polyfox.yatm.common.block;

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.block.GrcBlockContainer;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import io.polyfox.yatm.client.util.StateIconLoader;
import io.polyfox.yatm.common.tileentity.feature.ITileNeighbourAware;
import io.polyfox.yatm.creativetab.CreativeTabsYATM;
import io.polyfox.yatm.util.BlockFacing;
import io.polyfox.yatm.util.YATMPlatform;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class YATMBlockBaseTile extends GrcBlockContainer
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	protected Class<? extends TileEntity> tileEntityType;

	public YATMBlockBaseTile(Material material, Class<? extends TileEntity> klass)
	{
		super(material);
		setStepSound(Block.soundTypeStone);
		setHardness(4.0F);
		setCreativeTab(CreativeTabsYATM.instance());
		setTileEntityType(klass);
	}

	@Override
	protected ItemStack createStackedBlock(int metadata)
	{
		final Item item = Item.getItemFromBlock(this);
		return new ItemStack(item, 1, 0);
	}

	@Override
	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		final ItemStack stack = this.createStackedBlock(meta);
		final TileEntity te = getTileEntity(world, x, y, z);
		if (te instanceof INBTItemSerializable)
		{
			final NBTTagCompound tag = new NBTTagCompound();
			((INBTItemSerializable)te).writeToNBTForItem(tag);
			setTileTagCompound(world, x, y, z, stack, tag);
		}
		return stack;
	}

	@Override
	public boolean isRotatable(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}

	@Override
	public void doRotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		final int meta = world.getBlockMetadata(x, y, z);
		final int orn = meta & 3;
		final int extflag = meta & 12;
		// first normalize the orientation and then get its clockwise direction
		final int newMeta = BlockFacing.CW[BlockFacing.ORIENTATIONS4[orn][0].getHorizontalIndex()].getHorizontalIndex();
		world.setBlockMetadataWithNotify(x, y, z, newMeta | extflag, BlockFlags.SYNC);
	}

	protected void placeBlockByDirection(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		if (isRotatable(world, x, y, z, ForgeDirection.UNKNOWN))
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

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		placeBlockByDirection(world, x, y, z, entity, stack);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		if (world.isRemote) return;

		final TileEntity te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			if (te instanceof ITileNeighbourAware)
			{
				((ITileNeighbourAware)te).onNeighborBlockChange(world, x, y, z, block);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected StateIconLoader.IconVariant[] getIconVariants()
	{
		return StateIconLoader.instance.defaultIconVariants;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = StateIconLoader.instance.registerIconsWithIconVariants(getTextureName(), reg, getIconVariants());
	}

	public boolean isOnline(int meta)
	{
		return (meta & 4) == 4;
	}

	public int getOrientation(int meta)
	{
		return meta & 3;
	}

	@SideOnly(Side.CLIENT)
	public int getIconOffset(int meta)
	{
		return isOnline(meta) ? 6 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		final int iconOffset = getIconOffset(meta);
		if (side > 1)
		{
			final int orientation = getOrientation(meta);
			final int newSide = BlockFacing.ORIENTATIONS4[orientation][side - 2].getIndex();
			return icons[iconOffset + newSide];
		}
		else
		{
			return icons[iconOffset + side];
		}
	}

	protected boolean tryOpenGui(World world, int x, int y, int z, EntityPlayer p, int side)
	{
		final TileEntity te = getTileEntity(world, x, y, z);
		if (te instanceof IInteractionObject && !p.isSneaking())
		{
			YATMPlatform.openGui(p, te, ForgeDirection.getOrientation(side));
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		if (super.onBlockActivated(world, x, y, z, p, side, hitX, hitY, hitZ)) return true;
		if (tryOpenGui(world, x, y, z, p, side)) return true;
		return false;
	}
}
