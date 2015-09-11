package id2h.yatm

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.Mod.Instance
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import id2h.yatm.network.CommonProxy
import appeng.core.Api
import appeng.core.api.definitions.DefinitionConstructor

@Mod(modid = "yatm", name = "Yet Another Tech Mod", version = "1.0.0", modLanguage = "scala")
object YATM {
	@EventHandler
	def preInit(event: FMLPreInitializationEvent) {
		val constructor = new DefinitionConstructor(Api.INSTANCE.definitions().getFeatureRegistry(), Api.INSTANCE.definitions().getFeatureHandlerRegistry)
	  	Blocks.register(constructor)
	  	Items.register(constructor)
	}
}
