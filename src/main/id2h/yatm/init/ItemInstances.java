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
package id2h.yatm.init;

import growthcraft.api.core.item.ItemKey;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemSubtypeDefinition;
import growthcraft.core.common.GrcModuleBase;
import id2h.yatm.common.item.EnumPlate;
import id2h.yatm.common.item.ItemCapacitor;
import id2h.yatm.common.item.ItemCrystal;
import id2h.yatm.common.item.ItemCrystalSeed;
import id2h.yatm.common.item.ItemDust;
import id2h.yatm.common.item.ItemIngot;
import id2h.yatm.common.item.ItemMesh;
import id2h.yatm.common.item.ItemPlate;
import id2h.yatm.common.item.ItemVacuumTube;

import appeng.api.AEApi;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

public class ItemInstances extends GrcModuleBase
{
	public ItemDefinition crystalSeed;
	public ItemSubtypeDefinition crystalSeedUranium;
	public ItemSubtypeDefinition crystalSeedRedstone;

	public ItemDefinition dust;
	public ItemSubtypeDefinition dustUranium;
	public ItemSubtypeDefinition dustPureUranium;
	public ItemSubtypeDefinition dustPureRedstone;
	public ItemSubtypeDefinition dustCarbonSteel;
	public ItemSubtypeDefinition dustCrystalSteel;

	public ItemDefinition ingot;
	public ItemSubtypeDefinition ingotUranium;
	public ItemSubtypeDefinition ingotPureUranium;
	public ItemSubtypeDefinition ingotCarbonSteel;
	public ItemSubtypeDefinition ingotCrystalSteel;

	public ItemDefinition crystal;
	public ItemSubtypeDefinition crystalUranium;
	public ItemSubtypeDefinition crystalRedstone;

	public ItemDefinition capacitor;
	public ItemSubtypeDefinition capacitorIron;
	public ItemSubtypeDefinition capacitorGold;
	public ItemSubtypeDefinition capacitorDiamond;
	public ItemSubtypeDefinition capacitorObsidian;

	public ItemDefinition vacuumTube;
	public ItemSubtypeDefinition vacuumTubeIron;
	public ItemSubtypeDefinition vacuumTubeGold;
	public ItemSubtypeDefinition vacuumTubeDiamond;
	public ItemSubtypeDefinition vacuumTubeObsidian;

	public ItemDefinition plate;

	public ItemDefinition mesh;

	public ItemInstances() {}

	private void initSubtypes()
	{
		crystalSeedUranium = new ItemSubtypeDefinition(crystalSeed, ItemCrystalSeed.LEVEL_OFFSETS[0]);
		crystalSeedRedstone = new ItemSubtypeDefinition(crystalSeed, ItemCrystalSeed.LEVEL_OFFSETS[1]);

		dustUranium = new ItemSubtypeDefinition(dust, 0);
		dustPureUranium = new ItemSubtypeDefinition(dust, 1);
		dustPureRedstone = new ItemSubtypeDefinition(dust, 2);
		dustCarbonSteel = new ItemSubtypeDefinition(dust, 3);
		dustCrystalSteel = new ItemSubtypeDefinition(dust, 4);

		ingotUranium = new ItemSubtypeDefinition(ingot, 0);
		ingotPureUranium = new ItemSubtypeDefinition(ingot, 1);
		ingotCarbonSteel = new ItemSubtypeDefinition(ingot, 2);
		ingotCrystalSteel = new ItemSubtypeDefinition(ingot, 3);

		crystalUranium = new ItemSubtypeDefinition(crystal, 0);
		crystalRedstone = new ItemSubtypeDefinition(crystal, 1);

		capacitorIron = new ItemSubtypeDefinition(capacitor, 0);
		capacitorGold = new ItemSubtypeDefinition(capacitor, 1);
		capacitorDiamond = new ItemSubtypeDefinition(capacitor, 2);
		capacitorObsidian = new ItemSubtypeDefinition(capacitor, 3);

		vacuumTubeIron = new ItemSubtypeDefinition(vacuumTube, 0);
		vacuumTubeGold = new ItemSubtypeDefinition(vacuumTube, 1);
		vacuumTubeDiamond = new ItemSubtypeDefinition(vacuumTube, 2);
		vacuumTubeObsidian = new ItemSubtypeDefinition(vacuumTube, 3);
	}

	@Override
	public void preInit()
	{
		capacitor = new ItemDefinition(new ItemCapacitor());
		crystalSeed = new ItemDefinition(new ItemCrystalSeed());
		dust = new ItemDefinition(new ItemDust());
		ingot = new ItemDefinition(new ItemIngot());
		crystal = new ItemDefinition(new ItemCrystal());
		vacuumTube = new ItemDefinition(new ItemVacuumTube());
		mesh = new ItemDefinition(new ItemMesh());
		plate = new ItemDefinition(new ItemPlate());

		initSubtypes();
	}

	@Override
	public void register()
	{
		GameRegistry.registerItem(capacitor.getItem(), "yatm.ItemCapacitor");
		GameRegistry.registerItem(crystalSeed.getItem(), "yatm.ItemCrystalSeed");
		GameRegistry.registerItem(dust.getItem(), "yatm.ItemDust");
		GameRegistry.registerItem(ingot.getItem(), "yatm.ItemIngot");
		GameRegistry.registerItem(crystal.getItem(), "yatm.ItemCrystal");
		GameRegistry.registerItem(vacuumTube.getItem(), "yatm.ItemVacuumTube");
		GameRegistry.registerItem(mesh.getItem(), "yatm.ItemMesh");
		GameRegistry.registerItem(plate.getItem(), "yatm.ItemPlate");

		AEApi.instance().registries().grinder().addRecipe(crystalUranium.asStack(), dustPureUranium.asStack(1), 4);
		AEApi.instance().registries().grinder().addRecipe(crystalRedstone.asStack(), dustPureRedstone.asStack(1), 4);

		GameRegistry.addSmelting(dustUranium.asStack(), ingotUranium.asStack(), 0);
		GameRegistry.addSmelting(dustPureUranium.asStack(), ingotPureUranium.asStack(), 0);

		OreDictionary.registerOre("materialCapacitor", capacitorIron.asStack());
		OreDictionary.registerOre("materialCapacitor", capacitorGold.asStack());
		OreDictionary.registerOre("materialCapacitor", capacitorDiamond.asStack());
		OreDictionary.registerOre("materialCapacitor", capacitorObsidian.asStack());

		OreDictionary.registerOre("materialCapacitorIron", capacitorIron.asStack());
		OreDictionary.registerOre("materialCapacitorGold", capacitorGold.asStack());
		OreDictionary.registerOre("materialCapacitorDiamond", capacitorDiamond.asStack());
		OreDictionary.registerOre("materialCapacitorObsidian", capacitorObsidian.asStack());

		OreDictionary.registerOre("materialVacuumTube", vacuumTubeIron.asStack());
		OreDictionary.registerOre("materialVacuumTube", vacuumTubeGold.asStack());
		OreDictionary.registerOre("materialVacuumTube", vacuumTubeDiamond.asStack());
		OreDictionary.registerOre("materialVacuumTube", vacuumTubeObsidian.asStack());

		OreDictionary.registerOre("materialVacuumTubeIron", vacuumTubeIron.asStack());
		OreDictionary.registerOre("materialVacuumTubeGold", vacuumTubeGold.asStack());
		OreDictionary.registerOre("materialVacuumTubeDiamond", vacuumTubeDiamond.asStack());
		OreDictionary.registerOre("materialVacuumTubeObsidian", vacuumTubeObsidian.asStack());

		OreDictionary.registerOre("materialPlate", plate.asStack(1, ItemKey.WILDCARD_VALUE));

		for (EnumPlate eplate : EnumPlate.VALUES)
		{
			OreDictionary.registerOre(String.format("materialPlate%s", eplate.getCamelName()), eplate.asStack());
		}

		OreDictionary.registerOre("dustUranium", dustUranium.asStack());
		OreDictionary.registerOre("dustPureUranium", dustPureUranium.asStack());
		OreDictionary.registerOre("dustPureRedstone", dustPureRedstone.asStack());
		OreDictionary.registerOre("dustSteel", dustCarbonSteel.asStack());
		OreDictionary.registerOre("dustCarbonSteel", dustCarbonSteel.asStack());
		OreDictionary.registerOre("dustCrystalSteel", dustCrystalSteel.asStack());

		OreDictionary.registerOre("ingotUranium", ingotUranium.asStack());
		OreDictionary.registerOre("ingotPureUranium", ingotPureUranium.asStack());
		OreDictionary.registerOre("ingotSteel", ingotCarbonSteel.asStack());
		OreDictionary.registerOre("ingotCarbonSteel", ingotCarbonSteel.asStack());
		OreDictionary.registerOre("ingotCrystalSteel", ingotCrystalSteel.asStack());
	}
}
