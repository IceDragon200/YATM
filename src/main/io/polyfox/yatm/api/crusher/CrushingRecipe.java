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

import javax.annotation.Nonnull;

import io.polyfox.yatm.api.core.util.PossibleItemList;
import growthcraft.api.core.definition.IMultiItemStacks;

import net.minecraft.item.ItemStack;

public class CrushingRecipe
{
	public final String id;
	public final int time;
	public final PossibleItemList items;
	private final IMultiItemStacks input;

	public CrushingRecipe(@Nonnull String p_id, @Nonnull IMultiItemStacks p_input, @Nonnull PossibleItemList p_items, int p_time)
	{
		this.id = p_id;
		this.input = p_input;
		this.items = p_items;
		this.time = p_time;
	}

	public IMultiItemStacks getInput()
	{
		return input;
	}

	public boolean matchesRecipe(ItemStack stack)
	{
		return input.containsItemStack(stack);
	}
}
