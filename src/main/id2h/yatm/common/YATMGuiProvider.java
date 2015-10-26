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
package id2h.yatm.common;

import java.lang.reflect.Constructor;
import javax.annotation.Nonnull;

import id2h.yatm.exception.YATMInvalidGuiElement;
import id2h.yatm.util.GuiType;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Quite a bit of code was taken from Applied Energistics 2's GuiBridge
 */
public class YATMGuiProvider implements IGuiHandler
{
	private String typeName(Object inventory)
	{
		if (inventory == null)
		{
			return "NULL";
		}

		return inventory.getClass().getName();
	}

	private Constructor findConstructor(Constructor[] c, InventoryPlayer inventory, Object te)
	{
		final Class teClass = te.getClass();
		final Class invClass = inventory.getClass();
		for (Constructor con : c)
		{
			final Class[] types = con.getParameterTypes();
			if (types.length == 2)
			{
				if (types[0].isAssignableFrom(invClass) && types[1].isAssignableFrom(teClass))
				{
					return con;
				}
			}
		}
		return null;
	}

	private Object createContainerInstance(@Nonnull Class containerClass, @Nonnull InventoryPlayer inventory, Object te)
	{
		try
		{
			final Constructor[] c = containerClass.getConstructors();
			if (c.length == 0)
			{
				throw new YATMInvalidGuiElement("Invalid Gui Element Class " + containerClass.getName());
			}

			final Constructor target = findConstructor(c, inventory, te);

			if (target == null)
			{
				throw new IllegalStateException("Cannot find " + containerClass.getName() + "( " + this.typeName(inventory) + ", " + this.typeName(te) + " )");
			}

			return target.newInstance(inventory, te);
		}
		catch (Throwable t)
		{
			throw new IllegalStateException(t);
		}
	}

	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		final TileEntity te = world.getTileEntity(x, y, z);
		final GuiType gui = GuiType.get(id);
		if (gui != null)
		{
			return createContainerInstance(gui.getContainer(), player.inventory, te);
		}
		else
		{
			// LOG invalid GUI
		}
		return null;
	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		final TileEntity te = world.getTileEntity(x, y, z);
		final GuiType gui = GuiType.get(id);
		if (gui != null)
		{
			return createContainerInstance(gui.getGui(), player.inventory, te);
		}
		else
		{
			// LOG invalid GUI
		}
		return null;
	}
}
