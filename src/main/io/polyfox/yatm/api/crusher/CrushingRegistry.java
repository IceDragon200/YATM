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
package io.polyfox.yatm.api.crusher;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import growthcraft.api.core.util.MultiStacksUtil;
import io.polyfox.yatm.api.core.util.PossibleItemList;

import net.minecraft.item.ItemStack;

public class CrushingRegistry
{
	private List<CrushingRecipe> recipes = new ArrayList<CrushingRecipe>();

	public void addRecipe(@Nonnull String id, @Nonnull Object input, @Nonnull PossibleItemList items, int time)
	{
		recipes.add(new CrushingRecipe(id, MultiStacksUtil.toMultiItemStacks(input), items, time));
	}

	public CrushingRecipe getRecipe(ItemStack src)
	{
		if (src == null) return null;
		for (CrushingRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(src)) return recipe;
		}
		return null;
	}

	public boolean hasRecipe(ItemStack src)
	{
		return getRecipe(src) != null;
	}
}
