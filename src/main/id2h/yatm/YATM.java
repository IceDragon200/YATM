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

import id2h.yatm.api.core.util.PossibleItem;
import id2h.yatm.api.core.util.PossibleItemList;
import id2h.yatm.api.YATMApi;
import id2h.yatm.common.YATMGuiProvider;
import id2h.yatm.common.CommonProxy;
import id2h.yatm.init.BlockInstances;
import id2h.yatm.init.ItemInstances;

import appeng.core.Api;
import appeng.core.api.definitions.DefinitionConstructor;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@Mod(
	modid = YATM.MOD_ID,
	name = YATM.MOD_NAME,
	version = YATM.MOD_VERSION,
	acceptedMinecraftVersions = "[1.7.10]",
	dependencies = "required-after:appliedenergistics2;required-after:Growthcraft"
)
public class YATM
{
	public static final String MOD_ID = "yatm";
	public static final String MOD_NAME = "Yet Another Tech Mod";
	public static final String MOD_VERSION = "@VERSION@";

	public static BlockInstances blocks;
	public static ItemInstances items;

	@Instance(MOD_ID)
	private static YATM INSTANCE;

	public static YATM instance()
	{
		return INSTANCE;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		final DefinitionConstructor constructor = new DefinitionConstructor(Api.INSTANCE.definitions().getFeatureRegistry(), Api.INSTANCE.definitions().getFeatureHandlerRegistry());
		blocks = new BlockInstances();
		items = new ItemInstances();
		blocks.preInit(constructor);
		items.preInit(constructor);
	}

	private void registerCrushingRecipes()
	{
		YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.stone),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.cobblestone), 1.0f),
				new PossibleItem(new ItemStack(Blocks.gravel), 0.1f)
			),
			60
		);

		// stone brick > cracked stone brick
		YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.stonebrick, 1, 0),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.stonebrick, 1, 2), 1.0f),
				new PossibleItem(new ItemStack(Blocks.cobblestone), 0.1f)
			),
			60
		);

		// mossy stonebrick > 2 mossy cobblestone (100%) + 1 cobblestone (20%) + 1 vine (10%)
		YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.stonebrick, 1, 1),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.mossy_cobblestone, 2), 1.0f),
				new PossibleItem(new ItemStack(Blocks.cobblestone, 1), 0.2f),
				new PossibleItem(new ItemStack(Blocks.vine, 1), 0.1f)
			),
			60
		);

		YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.cobblestone),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.gravel), 1.0f),
				new PossibleItem(new ItemStack(Blocks.sand), 0.1f)
			),
			90
		);

		YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.gravel),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand), 1.0f)
			),
			120
		);

		//YATMApi.instance.crusher.addCrushing(new ItemStack(Blocks.sand),
		//	WeightedItemList.create(
		//	)
		//);
	}

	private void registerMixingRecipes()
	{

	}

	private void registerRecipes()
	{
		registerCrushingRecipes();
		registerMixingRecipes();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		blocks.register();
		items.register();
		registerRecipes();

		FMLInterModComms.sendMessage("Waila", "register", "id2h.yatm.integration.WailaIntegration.register");

		CommonProxy.instance.initRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new YATMGuiProvider());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//
	}
}
