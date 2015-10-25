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
package id2h.yatm.common.item;

import java.util.EnumSet;

import appeng.core.features.AEFeature;
import appeng.items.AEBaseItem;

public class ItemDust extends AEBaseItem
{
	public ItemDust(int dustType)
	{
		super();
		switch (dustType)
		{
			case Dusts.UraniumDust:
				setUnlocalizedName("yatm.ItemUraniumDust");
				setTextureName("yatm:ItemMaterial.UraniumDust");
				break;
			case Dusts.PurifiedUraniumDust:
				setUnlocalizedName("yatm.ItemPurifiedUraniumDust");
				setTextureName("yatm:ItemMaterial.PurifiedUraniumDust");
				break;
			default:
				break;
		}
		setFeature(EnumSet.of(AEFeature.Core));
	}
}
