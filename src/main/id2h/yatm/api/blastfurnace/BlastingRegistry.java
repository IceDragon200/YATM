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

import java.util.HashMap;
import javax.annotation.Nonnull;

import growthcraft.api.core.item.ItemKey;

import net.minecraft.item.ItemStack;

public class BlastingRegistry
{
	static class BlastingEntries extends HashMap<ItemKey, BlastingResult>
	{
		public static final long serialVersionUID = 1L;
	}

	static class BlastingTree extends HashMap<ItemKey, BlastingEntries>
	{
		public static final long serialVersionUID = 1L;
	}

	private final BlastingTree blastingTree = new BlastingTree();

	public void addBlasting(@Nonnull ItemStack result, @Nonnull ItemStack i1, @Nonnull ItemStack i2, int time, int heatRequirement)
	{
		final ItemKey primaryKey = new ItemKey(i1);
		if (!blastingTree.containsKey(primaryKey))
		{
			blastingTree.put(primaryKey, new BlastingEntries());
		}
		blastingTree.get(primaryKey).put(new ItemKey(i2), new BlastingResult(result, i1, i2, time, heatRequirement));
	}

	public BlastingResult getBlasting(ItemStack i1, ItemStack i2)
	{
		if (i1 == null) return null;
		if (i2 == null) return null;

		final BlastingEntries be = blastingTree.get(new ItemKey(i1));
		if (be != null) return be.get(new ItemKey(i2));
		return null;
	}

	public boolean canBlast(ItemStack i1, ItemStack i2)
	{
		return getBlasting(i1, i2) != null;
	}

	public void displayDebug()
	{
		System.out.println("BlastingRegistry");
		for (ItemKey key : blastingTree.keySet())
		{
			System.out.println("	" + key + " item=" + key.item + " meta=" + key.meta);
			final BlastingEntries entries = blastingTree.get(key);
			for (ItemKey subkey : entries.keySet())
			{
				final BlastingResult result = entries.get(subkey);
				System.out.println("		" + subkey +
					" item=" + subkey.item +
					" meta=" + subkey.meta +
					" blasting.input1=" + result.getInput1() +
					" blasting.input2=" + result.getInput2() +
					" blasting.output=" + result.getOutput() +
					" blasting.time=" + result.time
				);
			}
		}
	}
}
