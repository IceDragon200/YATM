package id2h.yatm

import appeng.api.definitions.IItemDefinition
import appeng.api.AEApi
import appeng.core.api.definitions.DefinitionConstructor
import cpw.mods.fml.common.registry.GameRegistry
import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.item.Dusts
import id2h.yatm.item.Ingots
import id2h.yatm.item.ItemUraniumSeed
import id2h.yatm.item.ItemPurifiedUraniumCrystal
import id2h.yatm.item.ItemDust
import id2h.yatm.item.ItemIngot
import net.minecraftforge.oredict.OreDictionary

object Items {
	var uraniumSeed: IItemDefinition = _
	var purifiedUraniumCrystal: IItemDefinition = _
	var uraniumDust: IItemDefinition = _
	var purifiedUraniumDust: IItemDefinition = _
	var uraniumIngot: IItemDefinition = _
	var purifiedUraniumIngot: IItemDefinition = _

	def register(constructor: DefinitionConstructor) {
	  	uraniumDust = constructor.registerItemDefinition(new ItemDust(Dusts.UraniumDust))
	  	uraniumIngot = constructor.registerItemDefinition(new ItemIngot(Ingots.UraniumIngot))

	  	uraniumSeed = constructor.registerItemDefinition(new ItemUraniumSeed())
	  	purifiedUraniumCrystal = constructor.registerItemDefinition(new ItemPurifiedUraniumCrystal())
	  	purifiedUraniumDust = constructor.registerItemDefinition(new ItemDust(Dusts.PurifiedUraniumDust))
	  	purifiedUraniumIngot = constructor.registerItemDefinition(new ItemIngot(Ingots.PurifiedUraniumIngot))

	  	uraniumDust.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())
	  	uraniumIngot.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())

	  	uraniumSeed.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())
	  	purifiedUraniumCrystal.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())
	  	purifiedUraniumDust.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())
	  	purifiedUraniumIngot.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance())

	  	GameRegistry.registerItem(uraniumDust.maybeItem().get(), "uraniumDust")
	  	GameRegistry.registerItem(uraniumIngot.maybeItem().get(), "uraniumIngot")

	  	GameRegistry.registerItem(uraniumSeed.maybeItem().get(), "uraniumSeed")
	  	GameRegistry.registerItem(purifiedUraniumCrystal.maybeItem().get(), "purifiedUraniumCrystal")
	  	GameRegistry.registerItem(purifiedUraniumDust.maybeItem().get(), "purifiedUraniumDust")
	  	GameRegistry.registerItem(purifiedUraniumIngot.maybeItem().get(), "purifiedUraniumIngot")

	  	AEApi.instance().registries().grinder().addRecipe(purifiedUraniumCrystal.maybeStack(1).get(), purifiedUraniumDust.maybeStack(2).get(), 4)
	  	GameRegistry.addSmelting(uraniumDust.maybeItem().get(), uraniumIngot.maybeStack(1).get(), 0)
	  	GameRegistry.addSmelting(purifiedUraniumDust.maybeItem().get(), purifiedUraniumIngot.maybeStack(1).get(), 0)

	  	OreDictionary.registerOre("dustUranium", uraniumDust.maybeItem().get())
	  	OreDictionary.registerOre("dustPurifiedUranium", purifiedUraniumDust.maybeItem().get())
	  	OreDictionary.registerOre("ingotUranium", uraniumIngot.maybeItem().get())
	  	OreDictionary.registerOre("ingotPurifiedUranium", purifiedUraniumIngot.maybeItem().get())
	}
}
