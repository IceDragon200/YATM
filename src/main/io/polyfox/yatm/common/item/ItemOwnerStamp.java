/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package io.polyfox.yatm.common.item;

import growthcraft.core.common.item.GrcItemBase;
import io.polyfox.yatm.security.ISecuredEntity;
import io.polyfox.yatm.creativetab.CreativeTabsYATM;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemOwnerStamp extends GrcItemBase
{
	public ItemOwnerStamp()
	{
		super();
		setTextureName("yatm:tools/owner_stamp");
		setUnlocalizedName("yatm.owner_stamp");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ)
	{
		final TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof ISecuredEntity)
		{
			if (!world.isRemote)
			{
				final ISecuredEntity secTile = (ISecuredEntity)te;
				final Block block = world.getBlock(x, y, z);
				final ForgeDirection dir = ForgeDirection.getOrientation(facing);
				final boolean pwned = player.capabilities.isCreativeMode ?
					// if player is in creative mode, just take the tile, regardless of owner
					secTile.getSecurityHeader().takeOwnership(player) :
					// try to take ownership of it
					secTile.getSecurityHeader().tryClaimOwnership(player);
				if (pwned)
				{
					te.markDirty();
					// @todo replace hard coded message format
					final String message = String.format("You have take ownership of %s", block.getLocalizedName());
					player.addChatMessage(new ChatComponentText(message));
				}
			}
			return true;
		}
		return false;
	}
}
