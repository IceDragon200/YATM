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
package io.polyfox.yatm.api.roller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import growthcraft.api.core.definition.IMultiItemStacks;

import net.minecraft.item.ItemStack;

public class RollingRegistry
{
	private final List<RollingRecipe> rollingEntries = new ArrayList<RollingRecipe>();

	public void addRecipe(@Nonnull RollingRecipe recipe)
	{
		rollingEntries.add(recipe);
	}

	public void addRecipe(@Nonnull String id, @Nonnull ItemStack result, @Nonnull IMultiItemStacks input, int time)
	{
		addRecipe(new RollingRecipe(id, input, result, time));
	}

	public RollingRecipe getRecipe(ItemStack input)
	{
		if (input == null) return null;
		for (RollingRecipe recipe : rollingEntries)
		{
			if (recipe.matchesRecipe(input)) return recipe;
		}
		return null;
	}

	public boolean hasRecipe(ItemStack input)
	{
		return getRecipe(input) != null;
	}

	public void displayDebug()
	{
		System.out.println("RollingRegistry");
		for (RollingRecipe result : rollingEntries)
		{
			System.out.println("		" +
				" blasting.input=" + result.getInput() +
				" blasting.output=" + result.getOutput() +
				" blasting.time=" + result.time
			);
		}
	}
}
