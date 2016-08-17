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
package io.polyfox.polyui.input;

import io.polyfox.polyui.UiContainer;
import io.polyfox.polyui.UiElement;

public class ContainerInputHandler extends AbstractInputHandler<UiContainer>
{
	public ContainerInputHandler(UiContainer p_element)
	{
		super(p_element);
	}

	@Override
	protected void handleKeyUp(char keyChar, int keyID)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onKeyUp(keyChar, keyID);
		}
	}

	@Override
	protected void handleKeyDown(char keyChar, int keyID)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onKeyDown(keyChar, keyID);
		}
	}

	@Override
	protected void handleKeyRepeat(char keyChar, int keyID)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onKeyRepeat(keyChar, keyID);
		}
	}

	@Override
	protected void handleMouseUp(int buttonID)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onMouseUp(buttonID);
		}
	}

	@Override
	protected void handleMouseDown(int buttonID)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onMouseDown(buttonID);
		}
	}

	@Override
	protected void handleMouseMove(int x, int y)
	{
		for (UiElement elm : element.getElements())
		{
			elm.getInputHandler().onMouseMove(x, y);
		}
	}
}
