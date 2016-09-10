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

public class PowerStorageTest
{
	@Test
	public void test_constructor()
	{
		final PowerStorage storage = new PowerStorage(4000);
		assertEquals(4000, storage.getCapacity());
	}

	@Test
	public void test_amount()
	{
		final PowerStorage storage = new PowerStorage(4000);
		assertEquals(0, storage.getAmount());
		// It should clamp the amount
		storage.setAmount(4001);
		assertEquals(4000, storage.getAmount());
		// It should not clamp the amount
		storage.setAmountUnsafe(8001);
		assertEquals(8001, storage.getAmount());
	}

	@Test
	public void test_receive()
	{
		final PowerStorage storage = new PowerStorage(4000);
		assertEquals(0, storage.getAmount());
		// It will increase the amount stored, when not simulated
		storage.receive(200, false);
		assertEquals(200, storage.getAmount());
		// It will NOT increase the amount stored, when simulated
		storage.receive(200, true);
		assertEquals(200, storage.getAmount());
		// It will add the amount to the existing
		storage.receive(200, false);
		assertEquals(400, storage.getAmount());
	}

	@Test
	public void test_consume()
	{
		final PowerStorage storage = new PowerStorage(4000);
		long temp;
		assertEquals(0, storage.getAmount());
		// Set the amount to 3000
		storage.setAmountUnsafe(3000);
		assertEquals(3000, storage.getAmount());
		// Consume some of the energy, not simulated
		temp = storage.consume(2000, false);
		assertEquals(2000, temp);
		assertEquals(1000, storage.getAmount());
		// Consume some of the energy, simulated
		storage.consume(200, true);
		assertEquals(1000, storage.getAmount());
		// Consume more energy than available
		storage.consume(5000, false);
		assertEquals(0, storage.getAmount());
	}
}
