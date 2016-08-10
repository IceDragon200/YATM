/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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

import java.util.HashMap;
import java.util.Map;

public enum EnumCrystal
{
	PURE_URANIUM(0, "pure_uranium"),
	PURE_REDSTONE(1, "pure_redstone");

	public static final Map<Integer, EnumCrystal> idToEnum = new HashMap<Integer, EnumCrystal>();
	static
	{
		for (EnumCrystal en : values())
		{
			idToEnum.put(en.id, en);
		}
	}

	public final int id;
	public final String unlocalizedName;

	private EnumCrystal(int p_id, String p_name)
	{
		this.id = p_id;
		this.unlocalizedName = p_name;
	}

	public static EnumCrystal getSafeByID(int id)
	{
		if (idToEnum.containsKey(id))
		{
			return idToEnum.get(id);
		}
		return PURE_URANIUM;
	}
}
