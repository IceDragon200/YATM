package id2h.yatm.init;

import id2h.yatm.creativetab.CreativeTabsYATM;
import id2h.yatm.common.item.Dusts;
import id2h.yatm.common.item.Ingots;
import id2h.yatm.common.item.ItemDust;
import id2h.yatm.common.item.ItemIngot;
import id2h.yatm.common.item.ItemPurifiedUraniumCrystal;
import id2h.yatm.common.item.ItemUraniumSeed;

import appeng.api.definitions.IItemDefinition;
import appeng.api.AEApi;
import appeng.core.api.definitions.DefinitionConstructor;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

public class Items
{
	public static IItemDefinition uraniumSeed;
	public static IItemDefinition purifiedUraniumCrystal;
	public static IItemDefinition uraniumDust;
	public static IItemDefinition purifiedUraniumDust;
	public static IItemDefinition uraniumIngot;
	public static IItemDefinition purifiedUraniumIngot;

	private Items() {}

	public static void preInit(DefinitionConstructor constructor)
	{
		uraniumDust = constructor.registerItemDefinition(new ItemDust(Dusts.UraniumDust));
		uraniumIngot = constructor.registerItemDefinition(new ItemIngot(Ingots.UraniumIngot));

		uraniumSeed = constructor.registerItemDefinition(new ItemUraniumSeed());
		purifiedUraniumCrystal = constructor.registerItemDefinition(new ItemPurifiedUraniumCrystal());
		purifiedUraniumDust = constructor.registerItemDefinition(new ItemDust(Dusts.PurifiedUraniumDust));
		purifiedUraniumIngot = constructor.registerItemDefinition(new ItemIngot(Ingots.PurifiedUraniumIngot));

		uraniumDust.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());
		uraniumIngot.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());

		uraniumSeed.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());
		purifiedUraniumCrystal.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());
		purifiedUraniumDust.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());
		purifiedUraniumIngot.maybeItem().get().setCreativeTab(CreativeTabsYATM.instance());
	}

	public static void register()
	{
		GameRegistry.registerItem(uraniumDust.maybeItem().get(), "uraniumDust");
		GameRegistry.registerItem(uraniumIngot.maybeItem().get(), "uraniumIngot");

		GameRegistry.registerItem(uraniumSeed.maybeItem().get(), "uraniumSeed");
		GameRegistry.registerItem(purifiedUraniumCrystal.maybeItem().get(), "purifiedUraniumCrystal");
		GameRegistry.registerItem(purifiedUraniumDust.maybeItem().get(), "purifiedUraniumDust");
		GameRegistry.registerItem(purifiedUraniumIngot.maybeItem().get(), "purifiedUraniumIngot");

		AEApi.instance().registries().grinder().addRecipe(purifiedUraniumCrystal.maybeStack(1).get(), purifiedUraniumDust.maybeStack(2).get(), 4);
		GameRegistry.addSmelting(uraniumDust.maybeItem().get(), uraniumIngot.maybeStack(1).get(), 0);
		GameRegistry.addSmelting(purifiedUraniumDust.maybeItem().get(), purifiedUraniumIngot.maybeStack(1).get(), 0);

		OreDictionary.registerOre("dustUranium", uraniumDust.maybeItem().get());
		OreDictionary.registerOre("dustPurifiedUranium", purifiedUraniumDust.maybeItem().get());
		OreDictionary.registerOre("ingotUranium", uraniumIngot.maybeItem().get());
		OreDictionary.registerOre("ingotPurifiedUranium", purifiedUraniumIngot.maybeItem().get());
	}
}
