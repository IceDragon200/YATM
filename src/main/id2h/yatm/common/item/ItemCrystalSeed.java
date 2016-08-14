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
import id2h.yatm.creativetab.CreativeTabsYATM;

import appeng.api.implementations.items.IGrowableCrystal;
import appeng.api.recipes.ResolverResult;
import appeng.core.features.AEFeature;
import appeng.core.localization.ButtonToolTips;
import appeng.entity.EntityGrowingCrystal;
import appeng.items.AEBaseItem;
import appeng.util.Platform;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemCrystalSeed extends AEBaseItem implements IGrowableCrystal
{
	public static final int LEVEL_OFFSET = 200;
	public static final int SINGLE_OFFSET = LEVEL_OFFSET * 3;
	public static final int[] LEVEL_OFFSETS = { 0, SINGLE_OFFSET };
	protected static final String[] crystalName = { "uranium", "redstone" };

	@SideOnly(Side.CLIENT)
	protected IIcon[][] iconsTable;

	public ItemCrystalSeed()
	{
		super();
		setHasSubtypes(true);
		setFeature(EnumSet.of(AEFeature.Core));
		setUnlocalizedName("yatm.crystal_seed");
		setCreativeTab(CreativeTabsYATM.instance());
	}

	private static ItemStack newStyle(ItemStack itemStack)
	{
		final Item item = itemStack.getItem();
		if (item instanceof ItemCrystalSeed)
		{
			((ItemCrystalSeed)item).getProgress(itemStack);
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
		ItemStack crystalSeedStack = YATM.items.crystalSeed.asStack(1);
		crystalSeedStack.setItemDamage(damage);
		crystalSeedStack = newStyle(crystalSeedStack);
		return new ResolverResult("crystal_seed", crystalSeedStack.getItemDamage(), crystalSeedStack.getTagCompound());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		final String prefix = "yatm:crystal_seeds/";
		this.iconsTable = new IIcon[crystalName.length][];
		int i = 0;
		for (String name : crystalName)
		{
			iconsTable[i] = new IIcon[3];
			iconsTable[i][0] = ir.registerIcon(prefix + name + "0");
			iconsTable[i][1] = ir.registerIcon(prefix + name + "1");
			iconsTable[i][2] = ir.registerIcon(prefix + name + "2");
			i++;
		}
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
		lines.add(ButtonToolTips.DoesntDespawn.getLocal());
		final int progress = this.getProgress(stack) % SINGLE_OFFSET;
		lines.add(Math.floor((float)progress / (float)(SINGLE_OFFSET / 100)) + "%");

		super.addCheckedInformation(stack, player, lines, displayMoreInfo);
	}

	@Override
	public int getEntityLifespan( ItemStack itemStack, World world )
	{
		return Integer.MAX_VALUE;
	}

	public int[] getCrystalData(ItemStack is)
	{
		int progress = getProgress(is);
		int index = 0;

		while (index < LEVEL_OFFSETS.length)
		{
			if (progress < (LEVEL_OFFSETS[index] + SINGLE_OFFSET))
			{
				progress -= LEVEL_OFFSETS[index];
				break;
			}
			index++;
		}

		return new int[] { MathHelper.clamp_int(index, 0, LEVEL_OFFSETS.length - 1), progress };
	}

	public int getCrystalIndex(ItemStack is)
	{
		return getCrystalData(is)[0];
	}

	public int getCrystalProgress(ItemStack is)
	{
		return getCrystalData(is)[1];
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		return this.getUnlocalizedName() + "." + crystalName[getCrystalIndex(is)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		final int[] data = getCrystalData(stack);
		final int index = data[0];
		final int progress = data[1];
		final IIcon[] icons = iconsTable[index];

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
	@SideOnly(Side.CLIENT)
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

		for (int i = 0; i < LEVEL_OFFSETS.length; ++i)
		{
			if (progress == LEVEL_OFFSETS[i] + SINGLE_OFFSET)
			{
				return YATM.items.crystal.asStack(size, i);
			}
		}

		setProgress(is, progress);
		return is;
	}

	@Override
	public Entity createEntity(World world , Entity location, ItemStack itemstack)
	{
		final EntityGrowingCrystal egc = new EntityGrowingCrystal(world, location.posX, location.posY, location.posZ, itemstack);

		egc.motionX = location.motionX;
		egc.motionY = location.motionY;
		egc.motionZ = location.motionZ;

		if (location instanceof EntityItem)
		{
			egc.delayBeforeCanPickup = ((EntityItem)location).delayBeforeCanPickup;
		}

		return egc;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks)
	{
		for (int offset : LEVEL_OFFSETS)
		{
			itemStacks.add(newStyle(new ItemStack(this, 1, offset)));
			itemStacks.add(newStyle(new ItemStack(this, 1, offset + LEVEL_OFFSET * 1)));
			itemStacks.add(newStyle(new ItemStack(this, 1, offset + LEVEL_OFFSET * 2)));
		}
	}
}
