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
package id2h.yatm.api.blastfurnace;

import javax.annotation.Nonnull;

import growthcraft.core.common.definition.IItemStackFactory;

import net.minecraft.item.ItemStack;

public class BlastingResult implements IItemStackFactory
{
	public final int time;
	public final int heatRequirement;
	private final ItemStack input1;
	private final ItemStack input2;
	private final ItemStack[] inputs;
	private final ItemStack output;

	public BlastingResult(@Nonnull ItemStack result, @Nonnull ItemStack i1, @Nonnull ItemStack i2, int t, int h)
	{
		this.output = result;
		this.input1 = i1;
		this.input2 = i2;
		this.time = t;
		this.heatRequirement = h;
		this.inputs = new ItemStack[] { input1, input2 };
	}

	public ItemStack getInput1()
	{
		return input1;
	}

	public ItemStack getInput2()
	{
		return input2;
	}

	public ItemStack[] getInputs()
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
}
