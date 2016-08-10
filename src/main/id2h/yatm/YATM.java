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

import growthcraft.api.cellar.CellarRegistry;
import growthcraft.api.core.item.EnumDye;
import growthcraft.api.core.item.MultiItemStacks;
import growthcraft.api.core.item.OreItemStacks;
import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.api.core.util.TickUtils;
import id2h.yatm.api.core.util.PossibleItem;
import id2h.yatm.api.core.util.PossibleItemList;
import id2h.yatm.api.crusher.CrushingRegistry;
import id2h.yatm.api.YATMApi;
import id2h.yatm.common.CommonProxy;
import id2h.yatm.common.item.EnumPlate;
import id2h.yatm.common.YATMGuiProvider;
import id2h.yatm.init.BlockInstances;
import id2h.yatm.init.ItemInstances;
import id2h.yatm.integration.growthcraft.HeatSourceHeater;
import id2h.yatm.network.handlers.WirelessMessageHandler;
import id2h.yatm.network.messages.WirelessMessage;
import id2h.yatm.system.WirelessSystem;
import id2h.yatm.util.YATMDebug;

import appeng.api.util.AEColor;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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

	public static SimpleNetworkWrapper network = new SimpleNetworkWrapper("yatm");
	public static WirelessSystem wireless = new WirelessSystem();
	public static BlockInstances blocks;
	public static ItemInstances items;

	@Instance(MOD_ID)
	private static YATM INSTANCE;

	private final ILogger logger = new GrcLogger(MOD_ID);
	private final ModuleContainer modules = new ModuleContainer();

	public static YATM instance()
	{
		return INSTANCE;
	}

	public static ILogger getLogger()
	{
		return INSTANCE.logger;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blocks = new BlockInstances();
		items = new ItemInstances();
		modules.add(blocks);
		modules.add(items);
		modules.add(new id2h.yatm.integration.ThaumcraftModule());
		modules.setLogger(logger);
		modules.freeze();
		modules.preInit();
		modules.register();
		network.registerMessage(WirelessMessageHandler.class, WirelessMessage.class, 200, Side.SERVER);
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
		YATMApi.instance().blasting().addRecipe(
			"blasting/ingots/carbon_steel/coal+ingot-iron",
			items.ingotCarbonSteel.asStack(1),
			new ItemStack(Items.coal, 1, 0),
			new OreItemStacks("ingotIron", 1),
			TickUtils.minutes(1),
			600
		);

		YATMApi.instance().blasting().addRecipe(
			"blasting/ingots/carbon_steel/charcoal+ingot-iron",
			items.ingotCarbonSteel.asStack(1),
			new ItemStack(Items.coal, 4, 1),
			new OreItemStacks("ingotIron", 1),
			TickUtils.minutes(1),
			600
		);

		YATMApi.instance().blasting().addRecipe(
			"blasting/ingots/crystal_steel/diamdon+ingot-iron",
			items.ingotCrystalSteel.asStack(1),
			new ItemStack(Items.diamond),
			new OreItemStacks("ingotIron", 1),
			TickUtils.minutes(3),
			600
		);

		YATMApi.instance().blasting().addRecipe(
			"blasting/plates/energized/dust.pure_redsteon+plate-iron",
			EnumPlate.ENERGIZED.asStack(1),
			new OreItemStacks("dustPureRedstone", 4),
			new OreItemStacks("materialPlateIron", 1),
			TickUtils.seconds(10),
			400
		);

		YATMApi.instance().blasting().addRecipe(
			"blasting/plates/photovoltaic/dye.blue+plate-energized",
			EnumPlate.PHOTOVOLTAIC.asStack(1),
			new OreItemStacks(EnumDye.BLUE.getOreName(), 4),
			new OreItemStacks("materialPlateEnergized", 1),
			TickUtils.seconds(10),
			400
		);

		// Reinforced Glass
		final MultiItemStacks sandStacks = new MultiItemStacks(
			new ItemStack(Blocks.sand, 1, 0),
			new ItemStack(Blocks.sand, 1, 1));
		YATMApi.instance().blasting().addRecipe(
			"blasting/blocks/reinforced_glass/sand+sand",
			blocks.reinforcedGlass.asStack(1),
			sandStacks,
			sandStacks,
			TickUtils.seconds(5),
			400
		);
	}

	private void registerMixingRecipes()
	{

	}

	private void registerRollingRecipes()
	{
		for (EnumPlate plate : EnumPlate.VALUES)
		{
			YATMApi.instance().rolling().addRecipe(
				String.format("rolling/plates/%s/ingot-%s*2", plate.underscoreName, plate.underscoreName),
				plate.asStack(1),
				new OreItemStacks(String.format("ingot%s", plate.getCamelName()), 2),
				TickUtils.seconds(10)
			);
		}
	}

	private void registerCraftingRecipes()
	{
		// Seed Recipes
		GameRegistry.addRecipe(new ShapelessOreRecipe(items.crystalSeedUranium.asStack(2),
			"dustUranium",
			Blocks.sand
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(items.crystalSeedRedstone.asStack(2),
			"dustRedstone",
			Blocks.sand
		));

		// Capacitor Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(items.capacitorIron.asStack(3),
			" x ",
			"xrx",
			" n ",
			'r', "dustRedstone",
			'x', "ingotIron",
			'n', "nuggetIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.capacitorGold.asStack(3),
			" x ",
			"xrx",
			" n ",
			'r', "dustPureRedstone",
			'x', "ingotGold",
			'n', "nuggetGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.capacitorDiamond.asStack(3),
			" x ",
			"xrx",
			" n ",
			'r', "dustPureRedstone",
			'x', "gemDiamond",
			'n', "nuggetGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.capacitorObsidian.asStack(3),
			" x ",
			"xrx",
			" n ",
			'r', "dustPureRedstone",
			'x', Blocks.obsidian,
			'n', "nuggetGold"
		));

		// Vacuum Tube Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(items.vacuumTubeIron.asStack(3),
			" g ",
			"grg",
			" x ",
			'g', Blocks.glass,
			'r', "dustRedstone",
			'x', "ingotIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.vacuumTubeGold.asStack(3),
			" g ",
			"grg",
			" x ",
			'g', Blocks.glass,
			'r', "dustPureRedstone",
			'x', "ingotGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.vacuumTubeDiamond.asStack(3),
			" g ",
			"grg",
			"nxn",
			'g', Blocks.glass,
			'r', "dustPureRedstone",
			'x', "gemDiamond",
			'n', "nuggetGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(items.vacuumTubeObsidian.asStack(3),
			" g ",
			"grg",
			"nxn",
			'g', Blocks.glass,
			'r', "dustPureRedstone",
			'x', Blocks.obsidian,
			'n', "nuggetGold"
		));


		// Metal Crate
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.metalCrate.asStack(),
			" I ",
			"I I",
			" I ",
			'I', "materialPlateIron"
		));

		// Machine Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.chassis.asStack(),
			" I ",
			"IRI",
			" I ",
			'I', "materialPlateIron",
			'R', "dustRedstone"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.coalGenerator.asStack(),
			" I ",
			"IFI",
			" R ",
			'R', "dustRedstone",
			'I', "ingotIron",
			'F', Blocks.furnace
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.roller.asStack(),
			"III",
			"G G",
			"IAI",
			'A', "materialCapacitorIron",
			'G', "gearStone",
			'I', "ingotIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.mixer.asStack(),
			"IGI",
			"ICI",
			"IAI",
			'A', items.vacuumTubeIron.asStack(),
			'C', blocks.chassis.asStack(),
			'G', "gearIron",
			'I', "materialPlateIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.autoCrafter.asStack(),
			"IWI",
			"ICI",
			"AIA",
			'A', "materialCapacitorIron",
			'C', blocks.chassis.asStack(),
			'W', Blocks.crafting_table,
			'I', "materialPlateIron"
		));

		{
			final Block grindstone = GameRegistry.findBlock("appliedenergistics2", "tile.BlockGrinder");
			if (grindstone != null)
			{
				YATMDebug.write("Adding Auto Grindstone recipe");
				GameRegistry.addRecipe(new ShapedOreRecipe(blocks.autoGrinder.asStack(),
					"IPI",
					"PWP",
					"IAI",
					'A', "materialCapacitorIron",
					'W', grindstone,
					'I', "ingotIron",
					'P', "materialPlateIron"
				));
			}
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.crusher.asStack(),
			"   ",
			"WCW",
			" A ",
			'A', "materialCapacitorIron",
			'C', blocks.chassis.asStack(),
			'W', Blocks.piston
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.compactor.asStack(),
			"III",
			"WCW",
			"IAI",
			'A', "materialCapacitorDiamond",
			'C', blocks.chassis.asStack(),
			'W', Blocks.piston,
			'I', "materialPlateCarbonSteel"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.heater.asStack(),
			" I ",
			"IFI",
			" A ",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'I', "materialPlateIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.fluxFurnace.asStack(),
			"FIF",
			"I I",
			"FAF",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'I', "ingotIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.miniBlastFurnace.asStack(),
			"BBB",
			"BFB",
			"PAP",
			'A', items.vacuumTubeIron.asStack(),
			'F', Blocks.furnace,
			'P', "materialPlateIron",
			'B', Blocks.brick_block
		));

		// Solar Panels
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.solarPanel.asStack(),
			"YYY",
			"III",
			'Y', "materialPlatePhotovoltaic",
			'I', "materialPlateIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.solarPanel.asStack(),
			"YYY",
			"III",
			'Y', "materialPlatePhotovoltaic",
			'I', "materialPlateAluminum"
		));

		// Energy Cell Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.energyCellBasic.asStack(),
			"YIY",
			"ICI",
			"YIY",
			'Y', items.crystalRedstone.asStack(),
			'C', blocks.chassis.asStack(),
			'I', "ingotIron"
		));

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
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.ventedMesh8x.asStack(8),
			" I ",
			"IMI",
			" I ",
			'M', items.mesh.asStack(),
			'I', "ingotIron"
		));

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
		YATMApi.instance().compacting().addRecipe(
			"compacting/gems/diamond/coal*64",
			new ItemStack(Items.diamond, 1),
			new ItemStack(Items.coal, 64), TickUtils.minutes(5));

		// if you're willing to lose a bit of extra coal, the compacting can be faster by using coal blocks
		YATMApi.instance().compacting().addRecipe(
			"compacting/gems/diamond/coal_block*8",
			new ItemStack(Items.diamond, 1),
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
		registerRecipes();
		registerHeatSources();

		FMLInterModComms.sendMessage("Waila", "register", "id2h.yatm.integration.WailaIntegration.register");

		CommonProxy.instance.initRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new YATMGuiProvider());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(wireless);
		modules.postInit();
	}
}
