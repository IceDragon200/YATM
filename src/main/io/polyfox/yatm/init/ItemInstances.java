/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package io.polyfox.yatm.init;

import growthcraft.api.core.item.ItemKey;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemSubtypeDefinition;
import growthcraft.core.common.GrcModuleItems;
import io.polyfox.yatm.common.item.EnumDust;
import io.polyfox.yatm.common.item.EnumPlate;
import io.polyfox.yatm.common.item.ItemCapacitor;
import io.polyfox.yatm.common.item.ItemCrystal;
import io.polyfox.yatm.common.item.ItemCrystalSeed;
import io.polyfox.yatm.common.item.ItemDust;
import io.polyfox.yatm.common.item.ItemGrenade;
import io.polyfox.yatm.common.item.ItemIngot;
import io.polyfox.yatm.common.item.ItemMesh;
import io.polyfox.yatm.common.item.ItemPlate;
import io.polyfox.yatm.common.item.ItemVacuumTube;

import appeng.api.AEApi;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

public class ItemInstances extends GrcModuleItems
{
	public ItemDefinition crystalSeed;
	public ItemSubtypeDefinition crystalSeedUranium;
	public ItemSubtypeDefinition crystalSeedRedstone;

	public ItemDefinition dust;

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

	public ItemDefinition grenade;

	public ItemInstances() {}

	private void initSubtypes()
	{
		crystalSeedUranium = new ItemSubtypeDefinition(crystalSeed, ItemCrystalSeed.LEVEL_OFFSETS[0]);
		crystalSeedRedstone = new ItemSubtypeDefinition(crystalSeed, ItemCrystalSeed.LEVEL_OFFSETS[1]);

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
		capacitor = newDefinition(new ItemCapacitor());
		crystalSeed = newDefinition(new ItemCrystalSeed());
		dust = newDefinition(new ItemDust());
		ingot = newDefinition(new ItemIngot());
		crystal = newDefinition(new ItemCrystal());
		vacuumTube = newDefinition(new ItemVacuumTube());
		mesh = newDefinition(new ItemMesh());
		plate = newDefinition(new ItemPlate());
		grenade = newDefinition(new ItemGrenade());

		initSubtypes();
	}

	@Override
	public void register()
	{
		capacitor.register("yatm.capacitor");
		crystalSeed.register("yatm.crystal_seed");
		dust.register("yatm.dust");
		ingot.register("yatm.ingot");
		crystal.register("yatm.crystal");
		vacuumTube.register("yatm.vacuum_tube");
		mesh.register("yatm.mesh");
		plate.register("yatm.plate");
		grenade.register("yatm.grenade");

		AEApi.instance().registries().grinder().addRecipe(crystalUranium.asStack(), EnumDust.URANIUM.asStack(1), 4);
		AEApi.instance().registries().grinder().addRecipe(crystalRedstone.asStack(), EnumDust.PURE_REDSTONE.asStack(1), 4);

		GameRegistry.addSmelting(EnumDust.URANIUM.asStack(), ingotUranium.asStack(), 0);
		GameRegistry.addSmelting(EnumDust.PURE_URANIUM.asStack(), ingotPureUranium.asStack(), 0);

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

		for (EnumPlate eplate : EnumPlate.values())
		{
			OreDictionary.registerOre(eplate.getOreName(), eplate.asStack());
		}

		for (EnumDust edust : EnumDust.values())
		{
			OreDictionary.registerOre(edust.getOreName(), edust.asStack());
		}

		OreDictionary.registerOre("ingotUranium", ingotUranium.asStack());
		OreDictionary.registerOre("ingotPureUranium", ingotPureUranium.asStack());
		OreDictionary.registerOre("ingotSteel", ingotCarbonSteel.asStack());
		OreDictionary.registerOre("ingotCarbonSteel", ingotCarbonSteel.asStack());
		OreDictionary.registerOre("ingotCrystalSteel", ingotCrystalSteel.asStack());
	}
}
