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
package io.polyfox.polyui.util;

public class Point2
{
	public final int x;
	public final int y;

	public Point2(int px, int py)
	{
		this.x = px;
		this.y = py;
	}

	public Point2(Point2 point)
	{
		this(point.x, point.y);
	}

	public Point2()
	{
		this(0, 0);
	}

	public Point2 dup()
	{
		return new Point2(x, y);
	}

	public Point2 identity()
	{
		return dup();
	}

	public Point2 invert()
	{
		return new Point2(-x, -y);
	}

	public Point2 add(int px, int py)
	{
		return new Point2(x + px, y + py);
	}

	public Point2 add(Point2 point)
	{
		return add(point.x, point.y);
	}

	public Point2 sub(int px, int py)
	{
		return new Point2(x - px, y - py);
	}

	public Point2 sub(Point2 point)
	{
		return sub(point.x, point.y);
	}

	public Point2 mul(int px, int py)
	{
		return new Point2(x * px, y * py);
	}

	public Point2 mul(float px, float py)
	{
		return new Point2(Math.round(x * px), Math.round(y * py));
	}

	public Point2 mul(Point2 point)
	{
		return mul(point.x, point.y);
	}

	@Override
	public String toString()
	{
		return String.format("(%d, %d)", x, y);
	}

	public int[] toArray()
	{
		return new int[]{x, y};
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj instanceof Point2)
		{
			final Point2 other = (Point2)obj;
			return (x == other.x) && (y == other.y);
		}
		return false;
	}
}
