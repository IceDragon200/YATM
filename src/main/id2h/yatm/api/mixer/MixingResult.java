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

import javax.annotation.Nonnull;

import growthcraft.core.common.definition.IItemStackFactory;

import net.minecraft.item.ItemStack;

public class MixingResult implements IItemStackFactory
{
	public final int time;
	private final ItemStack[] inputs;
	private final ItemStack output;

	public MixingResult(@Nonnull ItemStack result, @Nonnull ItemStack[] inps, int t)
	{
		this.output = result;
		this.inputs = inps;
		this.time = t;
	}

	public boolean checkInputsMatch(ItemStack[] given)
	{
		if (given.length != inputs.length) return false;

		// some really nutty comparison
		for (int i = 0; i < inputs.length; ++i)
		{
			// first we grab the input values
			final ItemStack expected = inputs[i];
			boolean found = false;
			// check it against the given
			for (int j = i; j < given.length; ++j)
			{
				final ItemStack temp = given[j];
				// if we found a match
				if (expected.isItemEqual(temp) && expected.stackSize <= temp.stackSize)
				{
					// swap it with the current outer index, since the
					// search length gets shorter as we reach the end.
					// This algorithm sorts AND verifies that an array
					// contains all the elements.

					// the starting element is switched with the current index
					given[j] = given[i];
					// this is the current element, now retried as "SEEN"
					given[i] = temp;

					found = true;
					break;
				}
			}
			// if we didn't find a valid item this time around, then
			// its not in the array and therefore this does not match
			if (!found) return false;
		}
		// all was fine, the arrays match
		return true;
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
