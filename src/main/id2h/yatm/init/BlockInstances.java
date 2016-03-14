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

import id2h.yatm.common.block.BlockAutoCrafter;
import id2h.yatm.common.block.BlockAutoGrinder;
import id2h.yatm.common.block.BlockChassis;
import id2h.yatm.common.block.BlockCoalGenerator;
import id2h.yatm.common.block.BlockCompactor;
import id2h.yatm.common.block.BlockCrusher;
import id2h.yatm.common.block.BlockDecorative8;
import id2h.yatm.common.block.BlockDecorative;
import id2h.yatm.common.block.BlockElectrolyser;
import id2h.yatm.common.block.BlockEnergyCell;
import id2h.yatm.common.block.BlockEnergyCellCreative;
import id2h.yatm.common.block.BlockFluxFurnace;
import id2h.yatm.common.block.BlockHeater;
import id2h.yatm.common.block.BlockLamp;
import id2h.yatm.common.block.BlockMetalCrate;
import id2h.yatm.common.block.BlockMiniBlastFurnace;
import id2h.yatm.common.block.BlockMixer;
import id2h.yatm.common.block.BlockRoller;
import id2h.yatm.common.block.BlockSolarPanel;
import id2h.yatm.common.block.BlockWindow;
import id2h.yatm.common.item.ItemBlockEnergyCell;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;
import id2h.yatm.common.tileentity.TileEntityCoalGenerator;
import id2h.yatm.common.tileentity.TileEntityCompactor;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityCrusher;
import id2h.yatm.common.tileentity.TileEntityElectrolyser;
import id2h.yatm.common.tileentity.TileEntityEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityFluxFurnace;
import id2h.yatm.common.tileentity.TileEntityFuelGenerator;
import id2h.yatm.common.tileentity.TileEntityHeater;
import id2h.yatm.common.tileentity.TileEntityMiniBlastFurnace;
import id2h.yatm.common.tileentity.TileEntityMixer;
import id2h.yatm.common.tileentity.TileEntityRoller;
import id2h.yatm.common.tileentity.TileEntitySolarPanel;

import growthcraft.core.common.definition.BlockDefinition;
import id2h.yatm.common.definition.BlockDecorativeDefinition;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInstances
{
	public BlockDecorativeDefinition reinforcedGlass;
	public BlockDecorativeDefinition ventedMesh2x;
	public BlockDecorativeDefinition ventedMesh4x;
	public BlockDecorativeDefinition ventedMesh8x;
	public BlockDecorativeDefinition warningStripes2x;
	public BlockDecorativeDefinition warningStripes4x;
	public BlockDecorativeDefinition warningStripes8x;
	public BlockDefinition autoCrafter;
	public BlockDefinition autoGrinder;
	public BlockDefinition chassis;
	public BlockDefinition coalGenerator;
	public BlockDefinition compactor;
	public BlockDefinition crusher;
	public BlockDefinition electrolyser;
	public BlockDefinition energyCellBasic;
	public BlockDefinition energyCellBasicCreative;
	public BlockDefinition energyCellDense;
	public BlockDefinition energyCellDenseCreative;
	public BlockDefinition energyCellNormal;
	public BlockDefinition energyCellNormalCreative;
	public BlockDefinition floorEngraving1;
	public BlockDefinition floorEngraving2;
	public BlockDefinition floorWarning1;
	public BlockDefinition floorWarning2;
	public BlockDefinition fluxFurnace;
	public BlockDefinition heater;
	public BlockDefinition lamp;
	public BlockDefinition metalCrate;
	public BlockDefinition miniBlastFurnace;
	public BlockDefinition mixer;
	public BlockDefinition roller;
	public BlockDefinition solarPanel;
	public BlockDefinition window;

	public BlockInstances() {}

	public void preInit()
	{
		chassis = new BlockDefinition(new BlockChassis());

		solarPanel = new BlockDefinition(new BlockSolarPanel());

		energyCellBasic = new BlockDefinition(new BlockEnergyCell("Basic", TileEntityEnergyCellBasic.class));
		energyCellBasicCreative = new BlockDefinition(new BlockEnergyCellCreative("Basic", TileEntityCreativeEnergyCellBasic.class));
		energyCellNormal = new BlockDefinition(new BlockEnergyCell("Normal", TileEntityEnergyCellNormal.class));
		energyCellNormalCreative = new BlockDefinition(new BlockEnergyCellCreative("Normal", TileEntityCreativeEnergyCellNormal.class));
		energyCellDense = new BlockDefinition(new BlockEnergyCell("Dense", TileEntityEnergyCellDense.class));
		energyCellDenseCreative = new BlockDefinition(new BlockEnergyCellCreative("Dense", TileEntityCreativeEnergyCellDense.class));

		autoCrafter = new BlockDefinition(new BlockAutoCrafter());
		autoGrinder = new BlockDefinition(new BlockAutoGrinder());
		coalGenerator = new BlockDefinition(new BlockCoalGenerator());
		compactor = new BlockDefinition(new BlockCompactor());
		crusher = new BlockDefinition(new BlockCrusher());
		electrolyser = new BlockDefinition(new BlockElectrolyser());
		fluxFurnace = new BlockDefinition(new BlockFluxFurnace());
		heater = new BlockDefinition(new BlockHeater());
		miniBlastFurnace = new BlockDefinition(new BlockMiniBlastFurnace());
		mixer = new BlockDefinition(new BlockMixer());
		roller = new BlockDefinition(new BlockRoller());

		metalCrate = new BlockDefinition(new BlockMetalCrate());

		floorEngraving1 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving1")
			.setBlockName("yatm.BlockFloorEngraving1"));

		floorEngraving2 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving2")
			.setBlockName("yatm.BlockFloorEngraving2"));

		floorWarning1 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning1")
			.setBlockName("yatm.BlockFloorWarning1"));

		floorWarning2 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning2")
			.setBlockName("yatm.BlockFloorWarning2"));

		lamp = new BlockDefinition(new BlockLamp());

		reinforcedGlass = new BlockDecorativeDefinition(new BlockDecorative8(Material.glass));
		reinforcedGlass.getBlock().setStepSound(Block.soundTypeGlass)
			.setBlockName("yatm.BlockReinforcedGlass")
			.setBlockTextureName("yatm:BlockReinforcedGlass");
		reinforcedGlass.getBlock().setOpaque(false);


		ventedMesh2x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh2x.getBlock().setBlockTextureName("yatm:BlockVentedMesh.2x")
			.setBlockName("yatm.BlockVentedMesh.2x");

		ventedMesh2x.getBlock().setOpaque(false);

		ventedMesh4x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh4x.getBlock().setBlockTextureName("yatm:BlockVentedMesh.4x")
			.setBlockName("yatm.BlockVentedMesh.4x");

		ventedMesh4x.getBlock().setOpaque(false);

		ventedMesh8x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh8x.getBlock().setBlockTextureName("yatm:BlockVentedMesh.8x")
			.setBlockName("yatm.BlockVentedMesh.8x");

		ventedMesh8x.getBlock().setOpaque(false);

		warningStripes2x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes2x.getBlock().setBlockTextureName("yatm:BlockWarningStripes.2x")
			.setBlockName("yatm.BlockWarningStripes.2x");

		warningStripes4x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes4x.getBlock().setBlockTextureName("yatm:BlockWarningStripes.4x")
			.setBlockName("yatm.BlockWarningStripes.4x");

		warningStripes8x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes8x.getBlock().setBlockTextureName("yatm:BlockWarningStripes.8x")
			.setBlockName("yatm.BlockWarningStripes.8x");

		window = new BlockDefinition(new BlockWindow());
	}

	public void register()
	{
		chassis.register("yatm.BlockChassis");

		solarPanel.register("yatm.BlockSolarPanel");

		energyCellBasic.register("yatm.BlockEnergyCellBasic", ItemBlockEnergyCell.class);
		energyCellBasicCreative.register("yatm.BlockEnergyCellBasicCreative");
		energyCellNormal.register("yatm.BlockEnergyCellNormal", ItemBlockEnergyCell.class);
		energyCellNormalCreative.register("yatm.BlockEnergyCellNormalCreative");
		energyCellDense.register("yatm.BlockEnergyCellDense", ItemBlockEnergyCell.class);
		energyCellDenseCreative.register("yatm.BlockEnergyCellDenseCreative");

		autoCrafter.register("yatm.BlockAutoCrafter");
		autoGrinder.register("yatm.BlockAutoGrinder");
		coalGenerator.register("yatm.BlockCoalGenerator");
		compactor.register("yatm.BlockCompactor");
		crusher.register("yatm.BlockCrusher");
		electrolyser.register("yatm.BlockElectrolyser");
		fluxFurnace.register("yatm.BlockFluxFurnace");
		heater.register("yatm.BlockHeater");
		miniBlastFurnace.register("yatm.BlockMiniBlastFurnace");
		mixer.register("yatm.BlockMixer");
		roller.register("yatm.BlockRoller");

		floorEngraving1.register("yatm.BlockFloorEngraving1");
		floorEngraving2.register("yatm.BlockFloorEngraving2");
		floorWarning1.register("yatm.BlockFloorWarning1");
		floorWarning2.register("yatm.BlockFloorWarning2");
		lamp.register("yatm.BlockLamp");
		metalCrate.register("yatm.BlockMetalCrate");
		reinforcedGlass.register("yatm.BlockReinforcedGlass");
		ventedMesh2x.register("yatm.BlockVentedMesh.2x.tiled");
		ventedMesh4x.register("yatm.BlockVentedMesh.4x.tiled");
		ventedMesh8x.register("yatm.BlockVentedMesh.8x.tiled");
		warningStripes2x.register("yatm.BlockWarningStripes.2x.tiled");
		warningStripes4x.register("yatm.BlockWarningStripes.4x.tiled");
		warningStripes8x.register("yatm.BlockWarningStripes.8x.tiled");
		window.register("yatm.BlockWindow");

		GameRegistry.registerTileEntity(TileEntityAutoCrafter.class, "yatm.tileentity.TileEntityAutoCrafter");
		GameRegistry.registerTileEntity(TileEntityAutoGrinder.class, "yatm.tileentity.TileEntityAutoGrinder");
		GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, "yatm.tileentity.TileEntityCoalGenerator");
		GameRegistry.registerTileEntity(TileEntityCompactor.class, "yatm.tileentity.TileEntityCompactor");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellBasic.class, "yatm.tileentity.TileEntityCreativeEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellDense.class, "yatm.tileentity.TileEntityCreativeEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellNormal.class, "yatm.tileentity.TileEntityCreativeEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "yatm.tileentity.TileEntityCrusher");
		GameRegistry.registerTileEntity(TileEntityElectrolyser.class, "yatm.tileentity.TileEntityElectrolyser");
		GameRegistry.registerTileEntity(TileEntityEnergyCellBasic.class, "yatm.tileentity.TileEntityEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityEnergyCellDense.class, "yatm.tileentity.TileEntityEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityEnergyCellNormal.class, "yatm.tileentity.TileEntityEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityFluxFurnace.class, "yatm.tileentity.TileEntityFluxFurnace");
		GameRegistry.registerTileEntity(TileEntityFuelGenerator.class, "yatm.tileentity.TileEntityFuelGenerator");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "yatm.tileentity.TileEntityHeater");
		GameRegistry.registerTileEntity(TileEntityMiniBlastFurnace.class, "yatm.tileentity.TileEntityMiniBlastFurnace");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "yatm.tileentity.TileEntityMixer");
		GameRegistry.registerTileEntity(TileEntityRoller.class, "yatm.tileentity.TileEntityRoller");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "yatm.tileentity.TileEntitySolarPanel");
	}
}
