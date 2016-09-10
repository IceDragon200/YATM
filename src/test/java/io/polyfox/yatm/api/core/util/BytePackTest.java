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
package io.polyfox.yatm.api.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BytePackTest
{
	@Test
	public void test_packI64ToI32a()
	{
		final int[] ary = new int[2];
		BytePack.packI64ToI32a(64, ary, 0);
		assertEquals(64, ary[0]);
		assertEquals(0, ary[1]);
		BytePack.packI64ToI32a((long)1 << 32, ary, 0);
		assertEquals(0, ary[0]);
		assertEquals(1, ary[1]);
	}

	@Test
	public void tets_unpackI64FromI32a()
	{
		final int[] ary = new int[2];
		BytePack.packI64ToI32a(64, ary, 0);
		final long result = BytePack.unpackI64FromI32a(ary, 0);
		assertEquals(64, result);
	}
}
