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
package id2h.yatm.api.blastfurnace;

import javax.annotation.Nonnull;

import growthcraft.api.core.definition.IItemStackFactory;
import growthcraft.api.core.definition.IMultiItemStacks;

import net.minecraft.item.ItemStack;

public class BlastingResult implements IItemStackFactory
{
	public final int time;
	public final int heatRequirement;
	private final IMultiItemStacks input1;
	private final IMultiItemStacks input2;
	private final IMultiItemStacks[] inputs;
	private final ItemStack output;

	public BlastingResult(@Nonnull ItemStack result, @Nonnull IMultiItemStacks i1, @Nonnull IMultiItemStacks i2, int t, int h)
	{
		this.output = result;
		this.input1 = i1;
		this.input2 = i2;
		this.time = t;
		this.heatRequirement = h;
		this.inputs = new IMultiItemStacks[] { input1, input2 };
	}

	public IMultiItemStacks getInput1()
	{
		return input1;
	}

	public IMultiItemStacks getInput2()
	{
		return input2;
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

	public boolean matchesRecipe(ItemStack i1, ItemStack i2)
	{
		return input1.containsItemStack(i1) && input2.containsItemStack(i2);
	}
}
