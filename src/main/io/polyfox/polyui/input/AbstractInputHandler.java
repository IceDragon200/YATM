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

import io.polyfox.polyui.UiElement;

public abstract class AbstractInputHandler<T extends UiElement> implements IInputHandler
{
	protected T element;

	public AbstractInputHandler(T p_element)
	{
		this.element = p_element;
	}

	protected void handleKeyUp(char key, int keyID)
	{
	}

	public void onKeyUp(char key, int keyID)
	{
		handleKeyUp(key, keyID);
	}

	protected void handleKeyDown(char key, int keyID)
	{
	}

	public void onKeyDown(char key, int keyID)
	{
		handleKeyDown(key, keyID);
	}

	protected void handleKeyRepeat(char key, int keyID)
	{
	}

	public void onKeyRepeat(char key, int keyID)
	{
		handleKeyRepeat(key, keyID);
	}

	protected void handleMouseUp(int buttonID)
	{
	}

	public void onMouseUp(int buttonID)
	{
		handleMouseUp(buttonID);
	}

	protected void handleMouseDown(int buttonID)
	{
	}

	public void onMouseDown(int buttonID)
	{
		handleMouseDown(buttonID);
	}

	protected void handleMouseMove(int x, int y)
	{
	}

	public void onMouseMove(int x, int y)
	{
		handleMouseMove(x, y);
	}
}
