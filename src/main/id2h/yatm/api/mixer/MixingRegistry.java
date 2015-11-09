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
package id2h.yatm.api.mixer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import javax.annotation.Nonnull;

import growthcraft.api.core.util.ItemKey;

import net.minecraft.item.ItemStack;

public class MixingRegistry
{
	private List<MixingResult> results = new ArrayList<MixingResult>();
	private Set<ItemKey> ingredients = new HashSet<ItemKey>();

	public void addMix(@Nonnull ItemStack result, @Nonnull ItemStack[] items, int time)
	{
		if (items.length == 0)
		{
			System.err.println("No inputs for mixing recipe result=" + result);
			return;
		}
		else if (items.length > 4)
		{
			System.err.println("Too many inputs for mixing recipe result=" + result);
			return;
		}

		// No, I don't trust your input.
		final ItemStack[] inputs = new ItemStack[items.length];
		System.arraycopy(items, 0, inputs, 0, inputs.length);

		results.add(new MixingResult(result, items, time));
		for (ItemStack item : items)
		{
			ingredients.add(new ItemKey(item));
		}
	}

	public MixingResult getMix(ItemStack[] items)
	{
		if (items != null)
		{
			for (MixingResult result : results)
			{
				if (result.checkInputsMatch(items))
				{
					return result;
				}
			}
		}
		return null;
	}

	public boolean canMix(ItemStack[] items)
	{
		return getMix(items) != null;
	}

	public boolean isStackMixingIngredient(ItemStack item)
	{
		return ingredients.contains(new ItemKey(item));
	}
}
