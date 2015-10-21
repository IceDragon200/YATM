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
	modLanguage = "scala",
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
