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
package id2h.yatm.api.crusher;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Nonnull;

import id2h.yatm.api.core.util.WeightedItem;
import id2h.yatm.api.core.util.WeightedItemMap;
import id2h.yatm.api.core.util.WeightedItemList;

import growthcraft.api.core.util.ItemKey;

import net.minecraft.item.ItemStack;

public class CrusherRegistry
{
	private WeightedItemMap crushingResults = new WeightedItemMap();

	public void addCrushing(@Nonnull ItemStack src, @Nonnull List<WeightedItem> items)
	{
		crushingResults.touch(new ItemKey(src)).addAll(items);
	}

	public WeightedItemList getCrushingResults(@Nonnull ItemStack src)
	{
		return crushingResults.get(new ItemKey(src));
	}

	public boolean canCrush(@Nonnull ItemStack src)
	{
		return crushingResults.containsKey(new ItemKey(src));
	}
}
