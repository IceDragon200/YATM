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
package id2h.yatm.api.compactor;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import growthcraft.core.common.definition.IItemStackFactory;

import id2h.yatm.api.core.util.PossibleItemList;

import net.minecraft.item.ItemStack;

public class CompactingResult implements IItemStackFactory
{
	public final int time;
	private final ItemStack input;
	private final ItemStack output;

	public CompactingResult(@Nonnull ItemStack inp, @Nonnull ItemStack result, int t)
	{
		this.input = inp;
		this.output = result;
		this.time = t;
	}

	public ItemStack getInput()
	{
		return input;
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