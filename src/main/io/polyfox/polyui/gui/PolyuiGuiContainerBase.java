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
package io.polyfox.polyui.gui;

import io.polyfox.polyui.UiContainer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class PolyuiGuiContainerBase extends GuiContainer
{
	protected UiContainer elements;

	public PolyuiGuiContainerBase(Container con)
	{
		super(con);
		this.elements = new UiContainer();
		elements.setID("gui.root");
	}

	@Override
	public void handleKeyboardInput()
	{
		final int keyID = Keyboard.getEventKey();
		final char keyChar = Keyboard.getEventCharacter();
		if (Keyboard.getEventKeyState())
		{
			elements.getInputHandler().onKeyDown(keyChar, keyID);
			if (Keyboard.areRepeatEventsEnabled())
			{
				if (Keyboard.isRepeatEvent())
				{
					elements.getInputHandler().onKeyRepeat(keyChar, keyID);
				}
			}
		}
		else
		{
			elements.getInputHandler().onKeyUp(keyChar, keyID);
		}
		super.handleKeyboardInput();
	}

	@Override
	public void handleMouseInput()
	{
		final int buttonID = Mouse.getEventButton();
		if (Mouse.getEventButtonState())
		{
			elements.getInputHandler().onMouseDown(buttonID);
		}
		else
		{
			elements.getInputHandler().onMouseUp(buttonID);
		}
		super.handleMouseInput();
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		elements.update();
	}

	@Override
	public void drawScreen(int x, int y, float scale)
	{
		super.updateScreen();
		RenderHelper.enableGUIStandardItemLighting();
		elements.render(x, y, zLevel, scale);
		RenderHelper.enableStandardItemLighting();
	}
}
