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
package id2h.yatm.common.item;

import java.util.Locale;
import com.google.common.base.CaseFormat;

import growthcraft.api.core.definition.IItemStackFactory;
import id2h.yatm.YATM;

import net.minecraft.item.ItemStack;

public enum EnumDust implements IItemStackFactory
{
	URANIUM,
	PURE_URANIUM,
	PURE_REDSTONE,
	IRON,
	GOLD,
	CARBON_STEEL,
	CRYSTAL_STEEL,
	STEEL,
	COPPER,
	ALUMINUM,
	TIN,
	ZINC,
	LEAD,
	NICKEL,
	MITHRIL,
	WROUGHT_IRON,
	PIG_IRON,
	SILVER,
	INVAR,
	BRONZE,
	BRASS;

	public static final EnumDust[] VALUES = values();

	public final String unlocalizedName;
	public final String camelName;
	public final int meta;

	private EnumDust(String camel)
	{
		if (camel == null)
		{
			this.camelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name());
		}
		else
		{
			this.camelName = camel;
		}
		this.unlocalizedName = name().toLowerCase(Locale.ENGLISH);
		this.meta = ordinal();
	}

	private EnumDust()
	{
		this((String)null);
	}

	public String getCamelName()
	{
		return camelName;
	}

	public String getDustName()
	{
		return String.format("dust.%s", unlocalizedName);
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

	public static EnumDust get(int index)
	{
		if (index >= 0 && index < VALUES.length)
		{
			return VALUES[index];
		}
		return IRON;
	}
}
