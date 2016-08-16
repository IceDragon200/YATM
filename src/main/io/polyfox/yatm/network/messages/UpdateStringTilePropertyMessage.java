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
package io.polyfox.yatm.network.messages;

import io.polyfox.yatm.util.YATMStreamUtils;

import io.netty.buffer.ByteBuf;

public class UpdateStringTilePropertyMessage extends AbstractUpdateTilePropertyMessage<String>
{
	public UpdateStringTilePropertyMessage() {}

	public UpdateStringTilePropertyMessage(int p_xCoord, int p_yCoord, int p_zCoord, int p_code, String str)
	{
		super(p_xCoord, p_yCoord, p_zCoord, p_code, str);
	}


	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.payload = YATMStreamUtils.readString(buf, payload);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		YATMStreamUtils.writeString(buf, payload);
	}
}
