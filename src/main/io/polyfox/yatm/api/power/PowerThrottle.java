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

public class PowerThrottle
{
	protected PowerStorage storage;
	protected long maxConsume;
	protected long maxProduce;

	public PowerThrottle(PowerStorage p_storage, long p_maxConsume, long p_maxProduce)
	{
		this.storage = p_storage;
		this.maxConsume = p_maxConsume;
		this.maxProduce = p_maxProduce;
	}

	public PowerThrottle(PowerStorage p_storage, long p_maxTransmit)
	{
		this(p_storage, p_maxTransmit, p_maxTransmit);
	}

	public long getMaxReceive()
	{
		return maxProduce;
	}

	public long getMaxConsume()
	{
		return maxConsume;
	}

	public long receive(long p_amount, boolean simulate)
	{
		return storage.receive(MathI64.clamp(p_amount, 0, maxProduce), simulate);
	}

	public long consume(long p_amount, boolean simulate)
	{
		return storage.consume(MathI64.clamp(p_amount, 0, maxConsume), simulate);
	}
}
