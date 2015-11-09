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
import id2h.yatm.api.crusher.CrushingRegistry;
import id2h.yatm.api.YATMApi;
import id2h.yatm.common.CommonProxy;
import id2h.yatm.common.YATMGuiProvider;
import id2h.yatm.init.BlockInstances;
import id2h.yatm.init.ItemInstances;
import id2h.yatm.util.TickUtils;
import id2h.yatm.util.YATMDebug;
import id2h.yatm.integration.growthcraft.HeatSourceHeater;

import growthcraft.api.cellar.CellarRegistry;

import appeng.api.util.AEColor;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
		blocks = new BlockInstances();
		items = new ItemInstances();
		blocks.preInit();
		items.preInit();
	}

	private void registerCrushingRecipes()
	{
		final CrushingRegistry c = YATMApi.instance().crushing();

		c.addCrushing(new ItemStack(Blocks.sandstone),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand, 4), 1.0f)
			),
			TickUtils.seconds(1)
		);

		c.addCrushing(new ItemStack(Blocks.sandstone, 1, 1),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand, 4), 1.0f)
			),
			TickUtils.seconds(1)
		);

		c.addCrushing(new ItemStack(Blocks.sandstone, 1, 2),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand, 4), 1.0f)
			),
			TickUtils.seconds(1)
		);

		c.addCrushing(new ItemStack(Blocks.sandstone_stairs, 1, 0),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand, 18), 1.0f),
				new PossibleItem(new ItemStack(Blocks.sand, 4), 0.7f),
				new PossibleItem(new ItemStack(Blocks.sand, 2), 0.5f)
			),
			TickUtils.seconds(1)
		);

		c.addCrushing(new ItemStack(Blocks.stone),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.cobblestone), 1.0f),
				new PossibleItem(new ItemStack(Blocks.gravel), 0.1f)
			),
			TickUtils.seconds(2)
		);

		// stone brick > cracked stone brick
		c.addCrushing(new ItemStack(Blocks.stonebrick, 1, 0),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.stonebrick, 1, 2), 1.0f),
				new PossibleItem(new ItemStack(Blocks.cobblestone), 0.1f)
			),
			TickUtils.seconds(2)
		);

		// mossy stonebrick > 2 mossy cobblestone (100%) + 1 cobblestone (20%) + 1 vine (10%)
		c.addCrushing(new ItemStack(Blocks.stonebrick, 1, 1),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.mossy_cobblestone, 2), 1.0f),
				new PossibleItem(new ItemStack(Blocks.cobblestone, 1), 0.2f),
				new PossibleItem(new ItemStack(Blocks.vine, 1), 0.1f)
			),
			TickUtils.seconds(2)
		);

		c.addCrushing(new ItemStack(Blocks.cobblestone),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.gravel), 1.0f),
				new PossibleItem(new ItemStack(Blocks.sand), 0.1f)
			),
			TickUtils.seconds(1)
		);

		c.addCrushing(new ItemStack(Blocks.gravel),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Blocks.sand), 1.0f)
			),
			TickUtils.seconds(1)
		);

		for (int i = 0; i < 3; ++i)
		{
			c.addCrushing(new ItemStack(Blocks.quartz_block, 1, i),
				PossibleItemList.create(
					new PossibleItem(new ItemStack(Items.quartz, 2), 1.0f),
					new PossibleItem(new ItemStack(Items.quartz, 1), 0.3f),
					new PossibleItem(new ItemStack(Items.quartz, 1), 0.1f)
				),
				TickUtils.seconds(2)
			);
		}

		c.addCrushing(new ItemStack(Blocks.quartz_stairs),
			PossibleItemList.create(
				new PossibleItem(new ItemStack(Items.quartz, 2), 1.0f),
				new PossibleItem(new ItemStack(Items.quartz, 1), 0.3f),
				new PossibleItem(new ItemStack(Items.quartz, 1), 0.1f),
				new PossibleItem(new ItemStack(Items.quartz, 1), 0.07f)
			),
			TickUtils.seconds(2)
		);

		//c.addCrushing(new ItemStack(Blocks.sand),
		//	WeightedItemList.create(
		//	)
		//);
	}

	private void registerBlastingRecipes()
	{
		YATMApi.instance().blasting().addBlasting(
			items.ingotCarbonSteel.asStack(1),
			new ItemStack(Items.coal, 1, 0),
			new ItemStack(Items.iron_ingot),
			TickUtils.minutes(1),
			600
		);

		YATMApi.instance().blasting().addBlasting(
			items.ingotCarbonSteel.asStack(1),
			new ItemStack(Items.coal, 4, 1),
			new ItemStack(Items.iron_ingot),
			TickUtils.minutes(1),
			600
		);

		YATMApi.instance().blasting().addBlasting(
			items.ingotCrystalSteel.asStack(1),
			new ItemStack(Items.diamond),
			new ItemStack(Items.iron_ingot),
			TickUtils.minutes(3),
			600
		);

		YATMApi.instance().blasting().addBlasting(
			items.plateEnergized.asStack(1),
			items.dustPureRedstone.asStack(4),
			items.plateIron.asStack(1),
			TickUtils.seconds(10),
			400
		);

		YATMApi.instance().blasting().addBlasting(
			items.platePhotovoltaic.asStack(1),
			new ItemStack(Items.dye, 4, 4),
			items.plateEnergized.asStack(1),
			TickUtils.seconds(10),
			400
		);

		// Reinforced Glass
		YATMApi.instance().blasting().addBlasting(
			blocks.reinforcedGlass.asStack(1),
			new ItemStack(Blocks.sand, 1, 0),
			new ItemStack(Blocks.sand, 1, 0),
			TickUtils.seconds(5),
			400
		);

		YATMApi.instance().blasting().addBlasting(
			blocks.reinforcedGlass.asStack(1),
			new ItemStack(Blocks.sand, 1, 1),
			new ItemStack(Blocks.sand, 1, 0),
			TickUtils.seconds(5),
			400
		);

		YATMApi.instance().blasting().addBlasting(
			blocks.reinforcedGlass.asStack(1),
			new ItemStack(Blocks.sand, 1, 1),
			new ItemStack(Blocks.sand, 1, 1),
			TickUtils.seconds(5),
			400
		);

		YATMApi.instance().blasting().addBlasting(
			blocks.reinforcedGlass.asStack(1),
			new ItemStack(Blocks.sand, 1, 1),
			new ItemStack(Blocks.sand, 1, 1),
			TickUtils.seconds(5),
			400
		);
	}

	private void registerMixingRecipes()
	{

	}

	private void registerRollingRecipes()
	{
		YATMApi.instance().rolling().addPressing(
			items.plateCarbonSteel.asStack(1),
			items.ingotCarbonSteel.asStack(2),
			TickUtils.seconds(10)
		);

		YATMApi.instance().rolling().addPressing(
			items.plateCrystalSteel.asStack(1),
			items.ingotCrystalSteel.asStack(2),
			TickUtils.seconds(20)
		);

		YATMApi.instance().rolling().addPressing(
			items.plateIron.asStack(1),
			new ItemStack(Items.iron_ingot, 2),
			TickUtils.seconds(5)
		);

		// gold is softer than iron
		YATMApi.instance().rolling().addPressing(
			items.plateGold.asStack(1),
			new ItemStack(Items.gold_ingot, 2),
			TickUtils.seconds(2)
		);
	}

	private void registerCraftingRecipes()
	{
		// Seed Recipes
		GameRegistry.addShapelessRecipe(items.crystalSeedUranium.asStack(2),
			items.dustUranium.asStack(),
			Blocks.sand
		);

		GameRegistry.addShapelessRecipe(items.crystalSeedRedstone.asStack(2),
			Items.redstone,
			Blocks.sand
		);

		// Capacitor Recipes
		GameRegistry.addShapedRecipe(items.capacitorIron.asStack(3),
			" x ",
			"xrx",
			" x ",
			'r', items.dustPureRedstone.asStack(),
			'x', Items.iron_ingot
		);

		GameRegistry.addShapedRecipe(items.capacitorGold.asStack(3),
			" x ",
			"xrx",
			" x ",
			'r', items.dustPureRedstone.asStack(),
			'x', Items.gold_ingot
		);

		GameRegistry.addShapedRecipe(items.capacitorDiamond.asStack(3),
			" x ",
			"xrx",
			"nxn",
			'r', items.dustPureRedstone.asStack(),
			'x', Items.diamond,
			'n', Items.gold_nugget
		);

		GameRegistry.addShapedRecipe(items.capacitorObsidian.asStack(3),
			" x ",
			"xrx",
			"nxn",
			'r', items.dustPureRedstone.asStack(),
			'x', Blocks.obsidian,
			'n', Items.gold_nugget
		);

		// Vacuum Tube Recipes
		GameRegistry.addShapedRecipe(items.vacuumTubeIron.asStack(3),
			" g ",
			"grg",
			" x ",
			'g', Blocks.glass,
			'r', items.dustPureRedstone.asStack(),
			'x', Items.iron_ingot
		);

		GameRegistry.addShapedRecipe(items.vacuumTubeGold.asStack(3),
			" g ",
			"grg",
			" x ",
			'g', Blocks.glass,
			'r', items.dustPureRedstone.asStack(),
			'x', Items.gold_ingot
		);

		GameRegistry.addShapedRecipe(items.vacuumTubeDiamond.asStack(3),
			" g ",
			"grg",
			"nxn",
			'g', Blocks.glass,
			'r', items.dustPureRedstone.asStack(),
			'x', Items.diamond,
			'n', Items.gold_nugget
		);

		GameRegistry.addShapedRecipe(items.vacuumTubeObsidian.asStack(3),
			" g ",
			"grg",
			"nxn",
			'g', Blocks.glass,
			'r', items.dustPureRedstone.asStack(),
			'x', Blocks.obsidian,
			'n', Items.gold_nugget
		);


		// Machine Recipes
		GameRegistry.addShapedRecipe(blocks.chassis.asStack(),
			" I ",
			"IRI",
			" I ",
			'I', items.plateIron.asStack(),
			'R', items.dustPureRedstone.asStack()
		);

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.roller.asStack(),
			"III",
			"G G",
			"IAI",
			'A', items.capacitorIron.asStack(),
			'G', "gearStone",
			'I', Items.iron_ingot
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.mixer.asStack(),
			"IGI",
			"ICI",
			"IAI",
			'A', items.vacuumTubeIron.asStack(),
			'C', blocks.chassis.asStack(),
			'G', "gearIron",
			'I', items.plateIron.asStack()
		));

		GameRegistry.addShapedRecipe(blocks.autoCrafter.asStack(),
			"IWI",
			"ICI",
			"AIA",
			'A', items.capacitorIron.asStack(),
			'C', blocks.chassis.asStack(),
			'W', Blocks.crafting_table,
			'I', items.plateIron.asStack()
		);

		{
			final Block grindstone = GameRegistry.findBlock("appliedenergistics2", "tile.BlockGrinder");
			if (grindstone != null)
			{
				YATMDebug.write("Adding Auto Grindstone recipe");
				GameRegistry.addShapedRecipe(blocks.autoGrinder.asStack(),
					"IPI",
					"PWP",
					"IAI",
					'A', items.capacitorIron.asStack(),
					'W', grindstone,
					'I', Items.iron_ingot,
					'P', items.plateIron.asStack()
				);
			}
		}

		GameRegistry.addShapedRecipe(blocks.crusher.asStack(),
			"   ",
			"WCW",
			" A ",
			'A', items.capacitorIron.asStack(),
			'C', blocks.chassis.asStack(),
			'W', Blocks.piston
		);

		GameRegistry.addShapedRecipe(blocks.compactor.asStack(),
			"III",
			"WCW",
			"IAI",
			'A', items.capacitorDiamond.asStack(),
			'C', blocks.chassis.asStack(),
			'W', Blocks.piston,
			'I', items.plateCarbonSteel.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.heater.asStack(),
			" I ",
			"IFI",
			" A ",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'I', items.plateIron.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.fluxFurnace.asStack(),
			"FIF",
			"I I",
			"FAF",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'I', Items.iron_ingot
		);

		GameRegistry.addShapedRecipe(blocks.miniBlastFurnace.asStack(),
			"BBB",
			"BFB",
			"PAP",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'P', items.plateIron.asStack(),
			'B', Blocks.brick_block
		);

		// Energy Cell Recipes
		GameRegistry.addShapedRecipe(blocks.solarPanel.asStack(),
			"YYY",
			"III",
			'Y', items.platePhotovoltaic.asStack(),
			'I', items.plateIron.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.energyCellBasic.asStack(),
			"YIY",
			"ICI",
			"YIY",
			'Y', items.crystalRedstone.asStack(),
			'C', blocks.chassis.asStack(),
			'I', Items.iron_ingot
		);

		GameRegistry.addShapedRecipe(blocks.energyCellNormal.asStack(),
			"CYC",
			"YCY",
			"CYC",
			'C', blocks.energyCellBasic.asStack(),
			'Y', items.crystalRedstone.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.energyCellDense.asStack(),
			"CBC",
			"BCB",
			"CBC",
			'C', blocks.energyCellNormal.asStack(),
			'B', blocks.energyCellBasic.asStack()
		);

		// Decorative Blocks
		GameRegistry.addRecipe(new ShapedOreRecipe(items.mesh.asStack(),
			"SSS",
			"SdS",
			"SSS",
			'd', "dustIron",
			'S', Items.string
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.mesh.asStack(),
			"SSS",
			"SdS",
			"SSS",
			'd', "dustAluminum",
			'S', Items.string
		));

		GameRegistry.addShapedRecipe(blocks.warningStripes2x.asStack(8),
			"BY",
			"YB",
			'B', new ItemStack(Blocks.wool, 1, AEColor.Black.ordinal()),
			'Y', new ItemStack(Blocks.wool, 1, AEColor.Yellow.ordinal())
		);

		GameRegistry.addShapedRecipe(blocks.warningStripes2x.asStack(8),
			"YB",
			"BY",
			'B', new ItemStack(Blocks.wool, 1, AEColor.Black.ordinal()),
			'Y', new ItemStack(Blocks.wool, 1, AEColor.Yellow.ordinal())
		);

		GameRegistry.addShapedRecipe(blocks.warningStripes4x.asStack(4),
			"xx",
			"xx",
			'x', blocks.warningStripes2x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.warningStripes2x.asStack(4),
			"x x",
			"   ",
			"x x",
			'x', blocks.warningStripes4x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.warningStripes8x.asStack(4),
			"xx",
			"xx",
			'x', blocks.warningStripes4x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.warningStripes4x.asStack(4),
			"x x",
			"   ",
			"x x",
			'x', blocks.warningStripes8x.asStack()
		);

		/// Vented Mesh
		GameRegistry.addShapedRecipe(blocks.ventedMesh8x.asStack(8),
			" I ",
			"IMI",
			" I ",
			'M', items.mesh.asStack(),
			'I', Items.iron_ingot
		);

		GameRegistry.addShapedRecipe(blocks.ventedMesh4x.asStack(4),
			"xx",
			"xx",
			'x', blocks.ventedMesh8x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.ventedMesh8x.asStack(4),
			"x x",
			"   ",
			"x x",
			'x', blocks.ventedMesh4x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.ventedMesh2x.asStack(4),
			"xx",
			"xx",
			'x', blocks.ventedMesh4x.asStack()
		);

		GameRegistry.addShapedRecipe(blocks.ventedMesh4x.asStack(4),
			"x x",
			"   ",
			"x x",
			'x', blocks.ventedMesh2x.asStack()
		);
	}

	private void registerCompactingRecipes()
	{
		// 1 Stack (64) of Coal == 1 Diamond
		YATMApi.instance().compacting().addCompacting(new ItemStack(Items.diamond, 1),
			new ItemStack(Items.coal, 64), TickUtils.minutes(5));

		// if you're willing to lose a bit of extra coal, the compacting can be faster by using coal blocks
		YATMApi.instance().compacting().addCompacting(new ItemStack(Items.diamond, 1),
			new ItemStack(Blocks.coal_block, 8), TickUtils.minutes(2) + TickUtils.seconds(30));
	}

	private void registerRecipes()
	{
		registerCrushingRecipes();
		registerCompactingRecipes();
		registerMixingRecipes();
		registerCraftingRecipes();
		registerBlastingRecipes();
		registerRollingRecipes();
		//YATMApi.instance().rolling().displayDebug();
		//YATMApi.instance().blasting().displayDebug();
	}

	private void registerHeatSources()
	{
		CellarRegistry.instance().heatSource().addHeatSource(blocks.heater.getBlock(), 4, new HeatSourceHeater());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		blocks.register();
		items.register();
		registerRecipes();
		registerHeatSources();

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
