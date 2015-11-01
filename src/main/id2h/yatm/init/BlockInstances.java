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
import id2h.yatm.common.block.BlockMixer;
import id2h.yatm.common.block.BlockSolarPanel;
import id2h.yatm.common.block.BlockWindow;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityCrusher;
import id2h.yatm.common.tileentity.TileEntityCompactor;
import id2h.yatm.common.tileentity.TileEntityHeater;
import id2h.yatm.common.tileentity.TileEntityElectrolyser;
import id2h.yatm.common.tileentity.TileEntityEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityFluxFurnace;
import id2h.yatm.common.tileentity.TileEntityMixer;
import id2h.yatm.common.tileentity.TileEntitySolarPanel;

import growthcraft.core.common.definition.BlockDefinition;
import id2h.yatm.common.definition.BlockDecorativeDefinition;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInstances
{
	public BlockDefinition autoCrafter;
	public BlockDefinition autoGrinder;
	public BlockDefinition energyCellBasic;
	public BlockDefinition energyCellBasicCreative;
	public BlockDefinition energyCellNormal;
	public BlockDefinition energyCellNormalCreative;
	public BlockDefinition energyCellDense;
	public BlockDefinition energyCellDenseCreative;
	public BlockDefinition chassis;
	public BlockDefinition crusher;
	public BlockDefinition compactor;
	public BlockDefinition floorEngraving1;
	public BlockDefinition floorEngraving2;
	public BlockDefinition floorWarning1;
	public BlockDefinition floorWarning2;
	public BlockDefinition fluxFurnace;
	public BlockDefinition heater;
	public BlockDefinition electrolyser;
	public BlockDefinition lamp;
	public BlockDefinition metalCrate;
	public BlockDefinition mixer;
	public BlockDecorativeDefinition reinforcedGlass;
	public BlockDefinition solarPanel;
	public BlockDecorativeDefinition ventedMesh2x;
	public BlockDecorativeDefinition ventedMesh4x;
	public BlockDecorativeDefinition ventedMesh8x;
	public BlockDecorativeDefinition warningStripes2x;
	public BlockDecorativeDefinition warningStripes4x;
	public BlockDecorativeDefinition warningStripes8x;
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
		compactor = new BlockDefinition(new BlockCompactor());
		crusher = new BlockDefinition(new BlockCrusher());
		heater = new BlockDefinition(new BlockHeater());
		electrolyser = new BlockDefinition(new BlockElectrolyser());
		fluxFurnace = new BlockDefinition(new BlockFluxFurnace());
		mixer = new BlockDefinition(new BlockMixer());

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
		GameRegistry.registerBlock(chassis.getBlock(), "yatm.BlockChassis");

		GameRegistry.registerBlock(solarPanel.getBlock(), "yatm.BlockSolarPanel");

		GameRegistry.registerBlock(energyCellBasic.getBlock(), "yatm.BlockEnergyCellBasic");
		GameRegistry.registerBlock(energyCellBasicCreative.getBlock(), "yatm.BlockEnergyCellBasicCreative");
		GameRegistry.registerBlock(energyCellNormal.getBlock(), "yatm.BlockEnergyCellNormal");
		GameRegistry.registerBlock(energyCellNormalCreative.getBlock(), "yatm.BlockEnergyCellNormalCreative");
		GameRegistry.registerBlock(energyCellDense.getBlock(), "yatm.BlockEnergyCellDense");
		GameRegistry.registerBlock(energyCellDenseCreative.getBlock(), "yatm.BlockEnergyCellDenseCreative");

		GameRegistry.registerBlock(autoCrafter.getBlock(), "yatm.BlockAutoCrafter");
		GameRegistry.registerBlock(autoGrinder.getBlock(), "yatm.BlockAutoGrinder");
		GameRegistry.registerBlock(compactor.getBlock(), "yatm.BlockCompactor");
		GameRegistry.registerBlock(crusher.getBlock(), "yatm.BlockCrusher");
		GameRegistry.registerBlock(heater.getBlock(), "yatm.BlockHeater");
		GameRegistry.registerBlock(electrolyser.getBlock(), "yatm.BlockElectrolyser");
		GameRegistry.registerBlock(fluxFurnace.getBlock(), "yatm.BlockFluxFurnace");
		GameRegistry.registerBlock(mixer.getBlock(), "yatm.BlockMixer");

		GameRegistry.registerBlock(floorEngraving1.getBlock(), "yatm.BlockFloorEngraving1");
		GameRegistry.registerBlock(floorEngraving2.getBlock(), "yatm.BlockFloorEngraving2");
		GameRegistry.registerBlock(floorWarning1.getBlock(), "yatm.BlockFloorWarning1");
		GameRegistry.registerBlock(floorWarning2.getBlock(), "yatm.BlockFloorWarning2");
		GameRegistry.registerBlock(lamp.getBlock(), "yatm.BlockLamp");
		GameRegistry.registerBlock(metalCrate.getBlock(), "yatm.BlockMetalCrate");
		GameRegistry.registerBlock(reinforcedGlass.getBlock(), "yatm.BlockReinforcedGlass");
		GameRegistry.registerBlock(ventedMesh2x.getBlock(), "yatm.BlockVentedMesh.2x.tiled");
		GameRegistry.registerBlock(ventedMesh4x.getBlock(), "yatm.BlockVentedMesh.4x.tiled");
		GameRegistry.registerBlock(ventedMesh8x.getBlock(), "yatm.BlockVentedMesh.8x.tiled");
		GameRegistry.registerBlock(warningStripes2x.getBlock(), "yatm.BlockWarningStripes.2x.tiled");
		GameRegistry.registerBlock(warningStripes4x.getBlock(), "yatm.BlockWarningStripes.4x.tiled");
		GameRegistry.registerBlock(warningStripes8x.getBlock(), "yatm.BlockWarningStripes.8x.tiled");
		GameRegistry.registerBlock(window.getBlock(), "yatm.BlockWindow");

		GameRegistry.registerTileEntity(TileEntityAutoCrafter.class, "yatm.tileentity.TileEntityAutoCrafter");
		GameRegistry.registerTileEntity(TileEntityAutoGrinder.class, "yatm.tileentity.TileEntityAutoGrinder");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellBasic.class, "yatm.tileentity.TileEntityCreativeEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellNormal.class, "yatm.tileentity.TileEntityCreativeEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellDense.class, "yatm.tileentity.TileEntityCreativeEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityEnergyCellBasic.class, "yatm.tileentity.TileEntityEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityEnergyCellNormal.class, "yatm.tileentity.TileEntityEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityEnergyCellDense.class, "yatm.tileentity.TileEntityEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "yatm.tileentity.TileEntityCrusher");
		GameRegistry.registerTileEntity(TileEntityCompactor.class, "yatm.tileentity.TileEntityCompactor");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "yatm.tileentity.TileEntityHeater");
		GameRegistry.registerTileEntity(TileEntityElectrolyser.class, "yatm.tileentity.TileEntityElectrolyser");
		GameRegistry.registerTileEntity(TileEntityFluxFurnace.class, "yatm.tileentity.TileEntityFluxFurnace");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "yatm.tileentity.TileEntityMixer");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "yatm.tileentity.TileEntitySolarPanel");
	}
}
