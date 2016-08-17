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
package io.polyfox.polyui;

import java.util.LinkedList;
import java.util.List;

import io.polyfox.polyui.input.ContainerInputHandler;
import io.polyfox.polyui.util.Resolution;

public class UiContainer extends UiElement
{
	protected List<UiElement> elements = new LinkedList<UiElement>();

	public UiContainer()
	{
		super();
		this.input = new ContainerInputHandler(this);
	}

	public List<UiElement> getElements()
	{
		return elements;
	}

	public void refreshResolution()
	{
		if (elements.isEmpty())
		{
			setResolution(new Resolution(0, 0));
		}
		else
		{
			int minX = 1 << 16;
			int maxX = -(1 << 16);
			int minY = 1 << 16;
			int maxY = -(1 << 16);
			for (UiElement element : elements)
			{
				final int x = element.getPosition().x;
				final int y = element.getPosition().y;
				final int x2 = x + element.getResolution().width;
				final int y2 = y + element.getResolution().height;
				if (x < minX)
				{
					minX = x;
				}
				if (y < minY)
				{
					minY = y;
				}
				if (x2 > maxX)
				{
					maxX = x2;
				}
				if (y2 > maxY)
				{
					maxY = y2;
				}
			}
			final int width = maxX - minX;
			final int height = maxY - minY;
			setResolution(new Resolution(width, height));
		}
	}

	public UiContainer addElement(UiElement element)
	{
		elements.add(element);
		refreshResolution();
		return this;
	}

	public UiElement getElement(int index)
	{
		return elements.get(index);
	}

	public UiContainer removeElement(UiElement element)
	{
		elements.remove(element);
		refreshResolution();
		return this;
	}

	@Override
	protected void updateContent()
	{
		for (UiElement element : elements)
		{
			element.update();
		}
	}

	@Override
	protected void renderContent(int x, int y, float z, float scale)
	{
		for (UiElement element : elements)
		{
			element.render(x, y, z, scale);
		}
	}
}
