package id2h.yatm

import id2h.yatm.network.CommonProxy

import appeng.core.Api
import appeng.core.api.definitions.DefinitionConstructor

import cpw.mods.fml.common.event.FMLInterModComms
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.Mod.Instance
import cpw.mods.fml.common.SidedProxy

@Mod(modid = "yatm", name = "Yet Another Tech Mod", version = "@VERSION@", modLanguage = "scala")
object YATM {
	@EventHandler
	def preInit(event: FMLPreInitializationEvent) {
		val constructor = new DefinitionConstructor(Api.INSTANCE.definitions().getFeatureRegistry(), Api.INSTANCE.definitions().getFeatureHandlerRegistry)
	  	Blocks.register(constructor)
	  	Items.register(constructor)
	}

	@EventHandler
	def init(event: FMLInitializationEvent) {
		FMLInterModComms.sendMessage("Waila", "register", "id2h.yatm.integration.WailaIntegration.register")
	}
}
