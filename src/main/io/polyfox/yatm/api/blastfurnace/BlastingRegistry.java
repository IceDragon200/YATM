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
package io.polyfox.yatm.api.blastfurnace;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import growthcraft.api.core.util.MultiStacksUtil;

import net.minecraft.item.ItemStack;

public class BlastingRegistry
{
	private final List<BlastingRecipe> recipes = new ArrayList<BlastingRecipe>();

	public void addRecipe(@Nonnull BlastingRecipe recipe)
	{
		recipes.add(recipe);
	}

	public void addRecipe(@Nonnull String id, @Nonnull ItemStack result, @Nonnull Object i1, @Nonnull Object i2, int time, int heatRequirement)
	{
		addRecipe(new BlastingRecipe(id, result, MultiStacksUtil.toMultiItemStacks(i1), MultiStacksUtil.toMultiItemStacks(i2), time, heatRequirement));
	}

	public BlastingRecipe getRecipe(ItemStack i1, ItemStack i2)
	{
		if (i1 == null) return null;
		if (i2 == null) return null;

		for (BlastingRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(i1, i2)) return recipe;
		}
		return null;
	}

	public boolean hasRecipe(ItemStack i1, ItemStack i2)
	{
		return getRecipe(i1, i2) != null;
	}

	public void displayDebug()
	{
		System.out.println("BlastingRegistry");
		for (BlastingRecipe recipe : recipes)
		{
			System.out.println(recipe.id + ": " +
				" blasting.catalyst=" + recipe.getCatalystItem() +
				" blasting.primary=" + recipe.getPrimaryItem() +
				" blasting.output=" + recipe.getOutput() +
				" blasting.time=" + recipe.time
			);
		}
	}
}
