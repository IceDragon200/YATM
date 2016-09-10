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

public class BytePack
{
	public static final long INT4_MASK = 0xF;
	public static final long INT8_MASK = 0xFF;
	public static final long INT16_MASK = 0xFFFF;
	public static final long INT32_MASK = 0xFFFFFFFF;
	public static final long INT64_MASK = (INT32_MASK << 32) | INT32_MASK;

	private BytePack() {}

	public static long replaceI16SegmentInI64(int segment, short replacement, long value)
	{
		if (segment >= 0 && segment < 4)
		{
			final int bits = segment * 16;
			final long mask = INT64_MASK ^ (INT16_MASK << bits);
			final long wos = value & mask;
			return wos | (replacement << bits);
		}
		return value;
	}

	public static void packI64ToI8(long value, byte[] target, int start)
	{
		target[start] = (byte)(value & INT8_MASK);
		target[start + 1] = (byte)((value >> 8) & INT8_MASK);
		target[start + 2] = (byte)((value >> 16) & INT8_MASK);
		target[start + 3] = (byte)((value >> 24) & INT8_MASK);
		target[start + 4] = (byte)((value >> 32) & INT8_MASK);
		target[start + 5] = (byte)((value >> 40) & INT8_MASK);
		target[start + 6] = (byte)((value >> 48) & INT8_MASK);
		target[start + 7] = (byte)((value >> 56) & INT8_MASK);
	}

	public static byte[] packI64aToI8a(long[] array)
	{
		final byte[] result = new byte[array.length * 8];
		for (int i = 0; i < array.length; ++i)
		{
			final long value = array[i];
			packI64ToI8(value, result, i * 8);
		}
		return result;
	}

	public static void packI64ToI16(long value, short[] target, int start)
	{
		target[start] = (short)(value & INT16_MASK);
		target[start + 1] = (short)((value >> 16) & INT16_MASK);
		target[start + 2] = (short)((value >> 32) & INT16_MASK);
		target[start + 3] = (short)((value >> 48) & INT16_MASK);
	}

	public static short[] packI64aToI16a(long[] array)
	{
		final short[] result = new short[array.length * 4];
		for (int i = 0; i < array.length; ++i)
		{
			final long value = array[i];
			packI64ToI16(value, result, i * 4);
		}
		return result;
	}

	public static void packI64ToI32a(long value, int[] target, int start)
	{
		target[start] = (int)(value & INT32_MASK);
		target[start + 1] = (int)((value >> 32) & INT32_MASK);
	}

	public static int[] packI64aToI32a(long[] array)
	{
		final int[] result = new int[array.length * 2];
		for (int i = 0; i < array.length; ++i)
		{
			final long value = array[i];
			packI64ToI32a(value, result, i * 2);
		}
		return result;
	}

	public static long unpackI64FromI32a(int[] array, int start)
	{
		return (long)array[start] | (long)(array[start + 1] << 32);
	}

	public static long[] unpackI64aFromI32a(long[] result, int[] array)
	{
		for (int i = 0; i < result.length; ++i)
		{
			result[i] = unpackI64FromI32a(array, i * 2);
		}
		return result;
	}

	public static long[] unpackI64aFromI32a(int[] array)
	{
		final long[] result = new long[array.length / 2];
		return unpackI64aFromI32a(result, array);
	}
}
