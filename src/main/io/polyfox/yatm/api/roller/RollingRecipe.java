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

import javax.annotation.Nonnull;

import growthcraft.api.core.definition.IItemStackFactory;
import growthcraft.api.core.definition.IMultiItemStacks;

import net.minecraft.item.ItemStack;

public class RollingRecipe implements IItemStackFactory
{
	public final String id;
	public final int time;
	private final IMultiItemStacks input;
	private final IMultiItemStacks[] inputs;
	private final ItemStack output;

	public RollingRecipe(@Nonnull String p_id, @Nonnull IMultiItemStacks inp, @Nonnull ItemStack result, int t)
	{
		this.id = p_id;
		this.input = inp;
		this.output = result;
		this.inputs = new IMultiItemStacks[] { input };
		this.time = t;
	}

	public IMultiItemStacks getInput()
	{
		return input;
	}

	public IMultiItemStacks[] getInputs()
	{
		return inputs;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public ItemStack asStack(int size)
	{
		final ItemStack result = output.copy();
		result.stackSize = size;
		return result;
	}

	public ItemStack asStack()
	{
		return output.copy();
	}

	public boolean matchesRecipe(@Nonnull ItemStack stack)
	{
		return input.containsItemStack(stack);
	}
}
