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
package id2h.yatm.util;

import javax.annotation.Nonnull;

import id2h.yatm.common.inventory.ContainerAutoCrafter;
import id2h.yatm.common.inventory.ContainerAutoGrinder;
import id2h.yatm.common.inventory.ContainerCoalGenerator;
import id2h.yatm.common.inventory.ContainerCompactor;
import id2h.yatm.common.inventory.ContainerCrate;
import id2h.yatm.common.inventory.ContainerCrusher;
import id2h.yatm.common.inventory.ContainerElectrolyser;
import id2h.yatm.common.inventory.ContainerFluxFurnace;
import id2h.yatm.common.inventory.ContainerFuelGenerator;
import id2h.yatm.common.inventory.ContainerHeater;
import id2h.yatm.common.inventory.ContainerMiniBlastFurnace;
import id2h.yatm.common.inventory.ContainerMixer;
import id2h.yatm.common.inventory.ContainerRoller;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;
import id2h.yatm.common.tileentity.TileEntityCoalGenerator;
import id2h.yatm.common.tileentity.TileEntityCompactor;
import id2h.yatm.common.tileentity.TileEntityCrate;
import id2h.yatm.common.tileentity.TileEntityCrusher;
import id2h.yatm.common.tileentity.TileEntityElectrolyser;
import id2h.yatm.common.tileentity.TileEntityFluxFurnace;
import id2h.yatm.common.tileentity.TileEntityFuelGenerator;
import id2h.yatm.common.tileentity.TileEntityHeater;
import id2h.yatm.common.tileentity.TileEntityMiniBlastFurnace;
import id2h.yatm.common.tileentity.TileEntityMixer;
import id2h.yatm.common.tileentity.TileEntityRoller;

import appeng.util.Platform;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public enum GuiType
{
	//NULL,
	AUTO_CRAFTER(ContainerAutoCrafter.class, TileEntityAutoCrafter.class),
	AUTO_GRINDER(ContainerAutoGrinder.class, TileEntityAutoGrinder.class),
	CRATE(ContainerCrate.class, TileEntityCrate.class),
	COMPACTOR(ContainerCompactor.class, TileEntityCompactor.class),
	CRUSHER(ContainerCrusher.class, TileEntityCrusher.class),
	ELECTROLYSER(ContainerElectrolyser.class, TileEntityElectrolyser.class),
	FLUX_FURNACE(ContainerFluxFurnace.class, TileEntityFluxFurnace.class),
	HEATER(ContainerHeater.class, TileEntityHeater.class),
	MINI_BLAST_FURNACE(ContainerMiniBlastFurnace.class, TileEntityMiniBlastFurnace.class),
	MIXER(ContainerMixer.class, TileEntityMixer.class),
	ROLLER(ContainerRoller.class, TileEntityRoller.class),
	COAL_GENERATOR(ContainerCoalGenerator.class, TileEntityCoalGenerator.class),
	FUEL_GENERATOR(ContainerFuelGenerator.class, TileEntityFuelGenerator.class);

	public static final GuiType[] GUIS = values();

	private final Class containerClass;
	private final Class tileEntityClass;
	private Class guiClass;

	private GuiType(@Nonnull Class<? extends Container> container, @Nonnull Class<? extends TileEntity> tileentity)
	{
		this.containerClass = container;
		this.tileEntityClass = tileentity;
		initGuiClass();
	}

	private void initGuiClass()
	{
		if (Platform.isClient())
		{
			final String start = this.containerClass.getName();
			final String guiClassName = start.replaceFirst("common.inventory.", "client.gui.").replace(".Container", ".Gui");

			if (start.equals(guiClassName))
			{
				throw new IllegalStateException("Unable to find gui class");
			}

			this.guiClass = ReflectionHelper.getClass(getClass().getClassLoader(), guiClassName);

			if (this.guiClass == null)
			{
				throw new IllegalStateException("Cannot Load class: " + guiClassName);
			}
		}
	}

	public Class getContainer()
	{
		return containerClass;
	}

	@SideOnly(Side.CLIENT)
	public Class getGui()
	{
		return guiClass;
	}

	public Class getTileEntity()
	{
		return tileEntityClass;
	}

	public static GuiType get(int id)
	{
		if (id >= 0 && id < GUIS.length)
		{
			return GUIS[id];
		}
		return null;
	}
}
