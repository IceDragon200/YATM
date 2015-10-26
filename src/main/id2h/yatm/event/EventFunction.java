/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package id2h.yatm.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;

import id2h.yatm.common.tileentity.YATMBaseTile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventFunction
{
	private Method method;

	public EventFunction(@Nonnull Method m)
	{
		this.method = m;
	}

	public void writeToStream(YATMBaseTile tile, ByteBuf data)
	{
		try
		{
			this.method.invoke(tile, data);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException(e);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalStateException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalStateException(e);
		}
	}

	@SideOnly (Side.CLIENT)
	public boolean readFromStream(YATMBaseTile tile, ByteBuf data)
	{
		try
		{
			return (Boolean)this.method.invoke(tile, data);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException(e);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalStateException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
