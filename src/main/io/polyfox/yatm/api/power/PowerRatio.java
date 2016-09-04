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
package io.polyfox.yatm.api.power;

public class PowerRatio
{
	protected long base;
	protected long target;

	public PowerRatio(long p_base, long p_target)
	{
		this.base = p_base;
		this.target = p_target;
	}

	public PowerRatio reverse()
	{
		return new PowerRatio(target, base);
	}

	public long getBase()
	{
		return base;
	}

	public long getTarget()
	{
		return target;
	}

	/**
	 * @param from the target value
	 */
	public long toBase(long from)
	{
		return from * base / target;
	}

	/**
	 * @param from the base value
	 */
	public long toTarget(long from)
	{
		return from * target / base;
	}

	public String toString()
	{
		return String.format("%d:%d", base, target);
	}
}
