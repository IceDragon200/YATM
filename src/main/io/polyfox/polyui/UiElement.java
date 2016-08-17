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

import javax.annotation.Nonnull;

import io.polyfox.polyui.input.IInputHandler;
import io.polyfox.polyui.input.NullInputHandler;
import io.polyfox.polyui.util.Point2;
import io.polyfox.polyui.util.Resolution;

public abstract class UiElement
{
	protected String id;
	protected Point2 position = new Point2();
	protected float zLevel;
	protected Resolution resolution = new Resolution();
	protected boolean visible = true;
	protected boolean focused;
	protected boolean updatable = true;
	protected IInputHandler input;

	public IInputHandler getInputHandler()
	{
		if (input == null)
		{
			this.input = new NullInputHandler(this);
		}
		return input;
	}

	public Point2 getPosition()
	{
		return position;
	}

	public void setPosition(@Nonnull Point2 p_position)
	{
		this.position = p_position;
	}

	public float getZLevel()
	{
		return zLevel;
	}

	public void setZLevel(float level)
	{
		this.zLevel = level;
	}

	public Resolution getResolution()
	{
		return resolution;
	}

	public void setResolution(@Nonnull Resolution p_resolution)
	{
		this.resolution = p_resolution;
	}

	public UiElement setID(String p_id)
	{
		this.id = p_id;
		return this;
	}

	public String getID()
	{
		return id;
	}

	public UiElement setVisible(boolean state)
	{
		this.visible = state;
		return this;
	}

	public UiElement show()
	{
		return setVisible(true);
	}

	public UiElement hide()
	{
		return setVisible(false);
	}

	public UiElement setFocus(boolean state)
	{
		this.focused = state;
		return this;
	}

	public UiElement focus()
	{
		return setFocus(true);
	}

	public UiElement unfocus()
	{
		return setFocus(false);
	}

	public boolean isVisible()
	{
		return visible;
	}

	public boolean isFocused()
	{
		return focused;
	}

	public UiElement setUpdatable(boolean state)
	{
		this.updatable = state;
		return this;
	}

	public boolean isUpdatable()
	{
		return updatable;
	}

	public boolean containsCoord(int x, int y)
	{
		return x >= position.x && x <= (position.x + resolution.width) &&
			y >= position.y && y <= (position.y + resolution.height);
	}

	protected abstract void updateContent();

	public final void update()
	{
		if (isUpdatable())
		{
			updateContent();
		}
	}

	protected abstract void renderContent(int x, int y, float z, float scale);

	public final void render(int x, int y, float z, float scale)
	{
		if (isVisible())
		{
			final int rx = x + position.x;
			final int ry = y + position.y;
			final float rz = z + zLevel;
			renderContent(rx, ry, rz, scale);
		}
	}
}
