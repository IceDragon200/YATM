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

public class MathI64Test
{
	@Test
	public void test_clamp()
	{
		assertEquals(50, MathI64.clamp(78, 0, 50));
		assertEquals(-50, MathI64.clamp(-78, -50, 0));
		assertEquals(50, MathI64.clamp(50, 0, 100));
	}

	@Test
	public void max()
	{
		assertEquals(78, MathI64.max(78, 0));
		assertEquals(78, MathI64.max(4, 78));
	}

	@Test
	public void min()
	{
		assertEquals(0, MathI64.min(78, 0));
		assertEquals(4, MathI64.min(4, 78));
	}
}
