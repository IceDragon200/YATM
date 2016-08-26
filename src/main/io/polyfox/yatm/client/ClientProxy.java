/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package io.polyfox.yatm.client;

import io.polyfox.yatm.client.gui.GuiAutoCrafter;
import io.polyfox.yatm.client.gui.GuiAutoGrinder;
import io.polyfox.yatm.client.gui.GuiCoalGenerator;
import io.polyfox.yatm.client.gui.GuiCompactor;
import io.polyfox.yatm.client.gui.GuiCrate;
import io.polyfox.yatm.client.gui.GuiCrusher;
import io.polyfox.yatm.client.gui.GuiElectrolyser;
import io.polyfox.yatm.client.gui.GuiFluxFurnace;
import io.polyfox.yatm.client.gui.GuiFluxSwitch;
import io.polyfox.yatm.client.gui.GuiFuelGenerator;
import io.polyfox.yatm.client.gui.GuiHeater;
import io.polyfox.yatm.client.gui.GuiMiniBlastFurnace;
import io.polyfox.yatm.client.gui.GuiMixer;
import io.polyfox.yatm.client.gui.GuiRoller;
import io.polyfox.yatm.client.gui.GuiWirelessRedstoneEmitter;
import io.polyfox.yatm.client.gui.GuiWirelessRedstoneReceiver;
import io.polyfox.yatm.client.renderer.RenderCagedMachine;
import io.polyfox.yatm.client.renderer.RenderCompactor;
import io.polyfox.yatm.client.renderer.RenderEnergyCell;
import io.polyfox.yatm.client.renderer.RenderSpringWoundCrank;
import io.polyfox.yatm.common.CommonProxy;
import io.polyfox.yatm.YATM;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		YATM.guiProvider.register("yatm:auto_crafter", GuiAutoCrafter.class);
		YATM.guiProvider.register("yatm:auto_grinder", GuiAutoGrinder.class);
		YATM.guiProvider.register("yatm:coal_generator", GuiCoalGenerator.class);
		YATM.guiProvider.register("yatm:compactor", GuiCompactor.class);
		YATM.guiProvider.register("yatm:crate", GuiCrate.class);
		YATM.guiProvider.register("yatm:crusher", GuiCrusher.class);
		YATM.guiProvider.register("yatm:electrolyser", GuiElectrolyser.class);
		YATM.guiProvider.register("yatm:flux_furnace", GuiFluxFurnace.class);
		YATM.guiProvider.register("yatm:flux_switch", GuiFluxSwitch.class);
		YATM.guiProvider.register("yatm:fuel_generator", GuiFuelGenerator.class);
		YATM.guiProvider.register("yatm:heater",GuiHeater.class);
		YATM.guiProvider.register("yatm:mini_blast_furnace", GuiMiniBlastFurnace.class);
		YATM.guiProvider.register("yatm:mixer", GuiMixer.class);
		YATM.guiProvider.register("yatm:roller", GuiRoller.class);
		YATM.guiProvider.register("yatm:wireless_redstone_emitter", GuiWirelessRedstoneEmitter.class);
		YATM.guiProvider.register("yatm:wireless_redstone_receiver", GuiWirelessRedstoneReceiver.class);
		RenderingRegistry.registerBlockHandler(new RenderCagedMachine());
		RenderingRegistry.registerBlockHandler(new RenderCompactor());
		RenderingRegistry.registerBlockHandler(new RenderEnergyCell());
		RenderingRegistry.registerBlockHandler(new RenderSpringWoundCrank());
	}
}
