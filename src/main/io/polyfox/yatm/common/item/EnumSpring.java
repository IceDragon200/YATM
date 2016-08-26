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

import java.util.Locale;
import com.google.common.base.CaseFormat;

import growthcraft.api.core.definition.IItemStackFactory;
import io.polyfox.yatm.YATM;

import net.minecraft.item.ItemStack;

public enum EnumSpring implements IItemStackFactory
{
	CARBON_STEEL(0, "carbon_steel");

	public static final EnumSpring[] VALUES = values();

	public final String basename;
	public final String camelName;
	private final int meta;

	private EnumSpring(int p_meta, String p_basename)
	{
		this.basename = p_basename;
		this.camelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, basename);
		this.meta = p_meta;
	}

	public String getBasename()
	{
		return basename;
	}

	public int getMetadata()
	{
		return meta;
	}

	public String getCamelName()
	{
		return camelName;
	}

	public String getSpringName()
	{
		return String.format("springs/%s", basename);
	}

	public String getOreName()
	{
		return String.format("materialSpring%s", getCamelName());
	}

	public String getPlateOreName()
	{
		return String.format("materialPlate%s", getCamelName());
	}

	@Override
	public ItemStack asStack(int size)
	{
		return YATM.items.spring.asStack(size, meta);
	}

	@Override
	public ItemStack asStack()
	{
		return asStack(1);
	}

	public static EnumSpring byIndex(int index)
	{
		if (index >= 0 && index < VALUES.length)
		{
			return VALUES[index];
		}
		return CARBON_STEEL;
	}
}
