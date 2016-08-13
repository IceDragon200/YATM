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
package id2h.yatm.client;

import id2h.yatm.client.gui.GuiAutoCrafter;
import id2h.yatm.client.gui.GuiAutoGrinder;
import id2h.yatm.client.gui.GuiCoalGenerator;
import id2h.yatm.client.gui.GuiCompactor;
import id2h.yatm.client.gui.GuiCrate;
import id2h.yatm.client.gui.GuiCrusher;
import id2h.yatm.client.gui.GuiElectrolyser;
import id2h.yatm.client.gui.GuiFluxFurnace;
import id2h.yatm.client.gui.GuiFuelGenerator;
import id2h.yatm.client.gui.GuiHeater;
import id2h.yatm.client.gui.GuiMiniBlastFurnace;
import id2h.yatm.client.gui.GuiMixer;
import id2h.yatm.client.gui.GuiRoller;
import id2h.yatm.client.gui.GuiWirelessRedstoneEmitter;
import id2h.yatm.client.gui.GuiWirelessRedstoneReceiver;
import id2h.yatm.client.renderer.RenderCagedMachine;
import id2h.yatm.client.renderer.RenderCompactor;
import id2h.yatm.client.renderer.RenderEnergyCell;
import id2h.yatm.common.CommonProxy;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		guiMap.put("yatm:auto_crafter", GuiAutoCrafter.class);
		guiMap.put("yatm:auto_grinder", GuiAutoGrinder.class);
		guiMap.put("yatm:coal_generator", GuiCoalGenerator.class);
		guiMap.put("yatm:compactor", GuiCompactor.class);
		guiMap.put("yatm:crate", GuiCrate.class);
		guiMap.put("yatm:crusher", GuiCrusher.class);
		guiMap.put("yatm:electrolyser", GuiElectrolyser.class);
		guiMap.put("yatm:flux_furnace", GuiFluxFurnace.class);
		guiMap.put("yatm:fuel_generator", GuiFuelGenerator.class);
		guiMap.put("yatm:heater",GuiHeater.class);
		guiMap.put("yatm:mini_blast_furnace", GuiMiniBlastFurnace.class);
		guiMap.put("yatm:mixer", GuiMixer.class);
		guiMap.put("yatm:roller", GuiRoller.class);
		guiMap.put("yatm:wireless_redstone_emitter", GuiWirelessRedstoneEmitter.class);
		guiMap.put("yatm:wireless_redstone_receiver", GuiWirelessRedstoneReceiver.class);
		RenderingRegistry.registerBlockHandler(new RenderCagedMachine());
		RenderingRegistry.registerBlockHandler(new RenderCompactor());
		RenderingRegistry.registerBlockHandler(new RenderEnergyCell());
	}
}
