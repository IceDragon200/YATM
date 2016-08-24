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

import java.util.HashMap;
import java.util.Map;
import com.google.common.base.CaseFormat;

import growthcraft.api.core.definition.IItemStackFactory;
import io.polyfox.yatm.YATM;

import net.minecraft.item.ItemStack;

public enum EnumDust implements IItemStackFactory
{
	URANIUM(0, "uranium"),
	PURE_URANIUM(1, "pure_uranium"),
	PURE_REDSTONE(2, "pure_redstone"),
	IRON(3, "iron"),
	GOLD(4, "gold"),
	CARBON_STEEL(5, "carbon_steel"),
	CRYSTAL_STEEL(6, "crystal_steel"),
	STEEL(7, "steel"),
	COPPER(8, "copper"),
	ALUMINUM(9, "aluminum"),
	TIN(10, "tin"),
	ZINC(11, "zinc"),
	LEAD(12, "lead"),
	NICKEL(13, "nickel"),
	MITHRIL(14, "mithril"),
	WROUGHT_IRON(15, "wrought_iron"),
	PIG_IRON(16, "pig_iron"),
	SILVER(17, "silver"),
	INVAR(18, "invar"),
	BRONZE(19, "bronze"),
	BRASS(20, "brass");

	private static final EnumDust[] BY_INDEX = new EnumDust[values().length];
	private static final Map<String, EnumDust> BY_NAME = new HashMap<String, EnumDust>();

	static
	{
		for (EnumDust dust : values())
		{
			BY_NAME.put(dust.getBasename(), dust);
			BY_INDEX[dust.getMetadata()] = dust;
		}
	}

	private final String camelName;
	private final String basename;
	private final int meta;

	private EnumDust(int p_meta, String p_basename)
	{
		this.meta = p_meta;
		this.basename = p_basename;
		this.camelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name());
	}

	public int getMetadata()
	{
		return meta;
	}

	public String getBasename()
	{
		return basename;
	}

	public String getCamelName()
	{
		return camelName;
	}

	public String getDustName()
	{
		return String.format("dusts/%s", basename);
	}

	public String getOreName()
	{
		return String.format("dust%s", getCamelName());
	}

	@Override
	public ItemStack asStack(int size)
	{
		return YATM.items.dust.asStack(size, meta);
	}

	@Override
	public ItemStack asStack()
	{
		return asStack(1);
	}

	public static EnumDust byIndex(int index)
	{
		if (index >= 0 && index < BY_INDEX.length)
		{
			return BY_INDEX[index];
		}
		return IRON;
	}

	public static EnumDust byBasename(String name)
	{
		final EnumDust dust = BY_NAME.get(name);
		if (dust != null) return dust;
		return IRON;
	}
}
