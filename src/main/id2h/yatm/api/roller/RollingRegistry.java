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
package id2h.yatm.api.roller;

import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;

import growthcraft.api.core.util.ItemKey;

import net.minecraft.item.ItemStack;

public class RollingRegistry
{
	private final Map<ItemKey, RollingResult> rollingEntries = new HashMap<ItemKey, RollingResult>();
	private final Map<ItemKey, RollingResult> pressingEntries = new HashMap<ItemKey, RollingResult>();

	public void addRolling(@Nonnull ItemStack result, @Nonnull ItemStack input, int time)
	{
		rollingEntries.put(new ItemKey(input), new RollingResult(input, result, time));
	}

	public void addPressing(@Nonnull ItemStack result, @Nonnull ItemStack input, int time)
	{
		// TODO: implement different roller modes
		addRolling(result, input, time);
		//pressingEntries.put(new ItemKey(input), new RollingResult(result, input, time));
	}

	public RollingResult getRolling(ItemStack input)
	{
		if (input == null) return null;
		return rollingEntries.get(new ItemKey(input));
	}

	public RollingResult getPressing(ItemStack input)
	{
		// TODO: implement different roller modes
		//if (input == null) return null;
		//return pressingEntries.get(new ItemKey(input));
		return getRolling(input);
	}

	public boolean canRoll(ItemStack input)
	{
		return getRolling(input) != null;
	}

	public boolean canPress(ItemStack input)
	{
		return getPressing(input) != null;
	}

	public void displayDebug()
	{
		System.out.println("RollingRegistry");
		for (ItemKey key : rollingEntries.keySet())
		{
			final RollingResult result = rollingEntries.get(key);

			System.out.println("		" + key +
				" item=" + key.item +
				" meta=" + key.meta +
				" blasting.input=" + result.getInput() +
				" blasting.output=" + result.getOutput() +
				" blasting.time=" + result.time
			);
		}
	}
}
