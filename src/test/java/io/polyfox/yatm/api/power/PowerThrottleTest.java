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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PowerThrottleTest
{
	@Test
	public void test_receive()
	{
		final PowerStorage storage = new PowerStorage(4000);
		final PowerThrottle throttle = new PowerThrottle(storage, 40, 40);
		assertEquals(0, storage.getAmount());
		// receive a throttled amount, non-simulated
		throttle.receive(50, false);
		assertEquals(40, storage.getAmount());
		// receive a throttled amount, simulated
		throttle.receive(50, true);
		assertEquals(40, storage.getAmount());
	}

	@Test
	public void test_consume()
	{
		final PowerStorage storage = new PowerStorage(4000);
		final PowerThrottle throttle = new PowerThrottle(storage, 40, 40);
		assertEquals(0, storage.getAmount());
		storage.setAmountUnsafe(4000);
		assertEquals(4000, storage.getAmount());
		// consume a throttled amount, non-simulated
		throttle.consume(50, false);
		assertEquals(3960, storage.getAmount());
		// consume a throttled amount, simulated
		throttle.consume(50, true);
		assertEquals(3960, storage.getAmount());
	}
}
