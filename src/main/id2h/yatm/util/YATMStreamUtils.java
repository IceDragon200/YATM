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
package id2h.yatm.util;

import java.io.UnsupportedEncodingException;

import growthcraft.api.core.stream.StreamUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class YATMStreamUtils
{
	private YATMStreamUtils() {}

	public static boolean writeString(ByteBuf buf, String str)
	{
		try
		{
			StreamUtils.writeStringASCII(buf, str);
		}
		catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static String readString(ByteBuf buf, String def)
	{
		String result = def;
		try
		{
			result = StreamUtils.readStringASCII(buf);
		}
		catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	public static String readString(ByteBuf buf)
	{
		return readString(buf, (String)null);
	}

	public static ByteBuf toByteBuf(String str)
	{
		final ByteBuf buf = Unpooled.buffer();
		writeString(buf, str);
		return buf;
	}
}
