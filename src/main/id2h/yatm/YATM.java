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
package id2h.yatm;

import id2h.yatm.common.CommonProxy;
import id2h.yatm.init.Blocks;
import id2h.yatm.init.Items;

import appeng.core.Api;
import appeng.core.api.definitions.DefinitionConstructor;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;

@Mod(
	modid = YATM.MOD_ID,
	name = YATM.MOD_NAME,
	version = YATM.MOD_VERSION,
	dependencies = "required-after:appliedenergistics2"
)
public class YATM
{
	public static final String MOD_ID = "yatm";
	public static final String MOD_NAME = "Yet Another Tech Mod";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(MOD_ID)
	public static YATM instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		final DefinitionConstructor constructor = new DefinitionConstructor(Api.INSTANCE.definitions().getFeatureRegistry(), Api.INSTANCE.definitions().getFeatureHandlerRegistry());
		Blocks.preInit(constructor);
		Items.preInit(constructor);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Blocks.register();
		Items.register();

		FMLInterModComms.sendMessage("Waila", "register", "id2h.yatm.integration.WailaIntegration.register");

		CommonProxy.instance.initRenders();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//
	}
}
