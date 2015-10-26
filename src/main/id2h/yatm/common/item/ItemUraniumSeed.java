/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package id2h.yatm.common.item;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

import id2h.yatm.YATM;

import appeng.api.implementations.items.IGrowableCrystal;
import appeng.api.recipes.ResolverResult;
import appeng.core.features.AEFeature;
import appeng.core.localization.ButtonToolTips;
import appeng.entity.EntityGrowingCrystal;
import appeng.items.AEBaseItem;
import appeng.util.Platform;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/* Because of some Scala quirks with Raw types, this is written in Java */

public class ItemUraniumSeed extends AEBaseItem implements IGrowableCrystal
{
	static final int LEVEL_OFFSET = 200;
	static final int MAX_LEVEL_OFFSET = LEVEL_OFFSET * 3;

	final IIcon[] icons = new IIcon[3];

	public ItemUraniumSeed()
	{
		this.setHasSubtypes(true);
		this.setFeature(EnumSet.of(AEFeature.Core));
		this.setUnlocalizedName("yatm.ItemUraniumSeed");
	}

	private static ItemStack newStyle(ItemStack itemStack)
	{
		final Item item = itemStack.getItem();
		if (item instanceof ItemUraniumSeed)
		{
			((ItemUraniumSeed)item).getProgress(itemStack);
		}
		else
		{
			// TODO: warn
		}
		return itemStack;
	}

	@Nullable
	public static ResolverResult getResolver(int damage)
	{
		ResolverResult resolver = null;

		for (ItemStack crystalSeedStack : YATM.items.uraniumSeed.maybeStack(1).asSet())
		{
			crystalSeedStack.setItemDamage(damage);
			crystalSeedStack = newStyle(crystalSeedStack);
			resolver = new ResolverResult("ItemUraniumSeed", crystalSeedStack.getItemDamage(), crystalSeedStack.getTagCompound());
		}

		return resolver;
	}

	@Override
	public void registerIcons(IIconRegister ir)
	{
		final String prefix = "yatm:ItemCrystalSeed.Uranium";
		icons[0] = ir.registerIcon(prefix + "0");
		icons[1] = ir.registerIcon(prefix + "1");
		icons[2] = ir.registerIcon(prefix + "2");
	}

	@Override
	public boolean isDamageable()
	{
		return false;
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return false;
	}

	@Override
	public void addCheckedInformation(ItemStack stack, EntityPlayer player, List<String> lines, boolean displayMoreInfo)
	{
		lines.add( ButtonToolTips.DoesntDespawn.getLocal() );
		final int progress = this.getProgress(stack);
		lines.add(Math.floor((float) progress / (float)(MAX_LEVEL_OFFSET / 100)) + "%");

		super.addCheckedInformation(stack, player, lines, displayMoreInfo);
	}

	@Override
	public int getEntityLifespan( ItemStack itemStack, World world )
	{
		return Integer.MAX_VALUE;
	}

	//@Override
	//public String getUnlocalizedName(ItemStack is) {
	//	return this.getUnlocalizedName();
	//}

	@Override
	public IIcon getIconIndex(ItemStack stack)
	{
		final int progress = getProgress(stack);
		if (progress < LEVEL_OFFSET)
		{
			return icons[0];
		}
		else if (progress < LEVEL_OFFSET * 2)
		{
			return icons[1];
		}
		else
		{
			return icons[2];
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return getIconIndex(stack);
	}

	@Override
	public float getMultiplier(Block blk, Material mat)
	{
		return 1.0f;
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	private void setProgress(ItemStack is, int newDamage)
	{
		final NBTTagCompound comp = Platform.openNbtData(is);
		comp.setInteger("progress", newDamage);
		is.setItemDamage(newDamage);
	}

	private int getProgress(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			return is.getTagCompound().getInteger("progress");
		}
		else
		{
			final int progress = is.getItemDamage();
			final NBTTagCompound comp = Platform.openNbtData(is);
			comp.setInteger("progress", progress);
			is.setItemDamage(progress);
			return progress;
		}
	}

	@Override
	public ItemStack triggerGrowth(ItemStack is)
	{
		final int progress = getProgress(is) + 1;
		final int size = is.stackSize;

		if (progress >= MAX_LEVEL_OFFSET)
		{
			for (ItemStack stack : YATM.items.purifiedUraniumCrystal.maybeStack(size).asSet())
			{
				return stack;
			}
		}

		this.setProgress(is, progress);
		return is;
	}

	@Override
	public Entity createEntity(World world , Entity location, ItemStack itemstack)
	{
		final EntityGrowingCrystal egc = new EntityGrowingCrystal(world, location.posX, location.posY, location.posZ, itemstack);

		egc.motionX = location.motionX;
		egc.motionY = location.motionY;
		egc.motionZ = location.motionZ;

		if (location instanceof EntityItem) {
			egc.delayBeforeCanPickup = ((EntityItem)location).delayBeforeCanPickup;
		}

		return egc;
	}

	@Override
	public void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks)
	{
		itemStacks.add(newStyle(new ItemStack(this, 1, 0)));
		itemStacks.add(newStyle(new ItemStack(this, 1, LEVEL_OFFSET * 1)));
		itemStacks.add(newStyle(new ItemStack(this, 1, LEVEL_OFFSET * 2)));
	}
}
