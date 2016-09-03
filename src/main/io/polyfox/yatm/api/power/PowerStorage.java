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

import io.polyfox.yatm.api.core.util.MathI64;

public class PowerStorage implements IPowerStorage
{
	protected long capacity;
	protected long amount;

	public PowerStorage(long p_capacity)
	{
		this.capacity = p_capacity;
		this.amount = 0;
	}

	public long isFull()
	{
		return amount >= capacity;
	}

	public long isOvercharged()
	{
		return amount > capacity;
	}

	public long isEmpty()
	{
		return amount == 0;
	}

	public void setAmountUnsafe(long p_amount)
	{
		this.amount = Math.max(p_amount, 0);
	}

	public void setAmount(long p_amount)
	{
		setAmountUnsafe(MathI64.clamp(p_amount, 0, capacity));
	}

	public long chargeDiff(long p_amount)
	{
		final long a = p_amount + amount;
		final long capped = MathI64.clamp(a, 0, capacity);
		return p_amount - a - capped;
	}

	public long produce(long p_amount)
	{
		final long used = chargeDiff(p_amount);
		setAmountUnsafe(amount + used);
		return used;
	}

	public long consume(long p_amount)
	{
		return -produce(-p_amount);
	}
}
