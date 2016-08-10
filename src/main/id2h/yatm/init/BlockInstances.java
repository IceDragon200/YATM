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
package id2h.yatm.init;

import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.GrcModuleBase;
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
import id2h.yatm.common.block.BlockWirelessEmitter;
import id2h.yatm.common.block.BlockWirelessReceiver;
import id2h.yatm.common.definition.BlockDecorativeDefinition;
import id2h.yatm.common.item.ItemBlockEnergyCell;
import id2h.yatm.common.item.ItemBlockMachine;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;
import id2h.yatm.common.tileentity.TileEntityCoalGenerator;
import id2h.yatm.common.tileentity.TileEntityCompactor;
import id2h.yatm.common.tileentity.TileEntityCrate;
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
import id2h.yatm.common.tileentity.TileEntityWirelessEmitter;
import id2h.yatm.common.tileentity.TileEntityWirelessReceiver;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInstances extends GrcModuleBase
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
	public BlockDefinition wirelessEmitter;
	public BlockDefinition wirelessReceiver;

	public BlockInstances() {}

	@Override
	public void preInit()
	{
		chassis = new BlockDefinition(new BlockChassis());

		solarPanel = new BlockDefinition(new BlockSolarPanel());

		energyCellBasic = new BlockDefinition(new BlockEnergyCell("basic", TileEntityEnergyCellBasic.class));
		energyCellBasicCreative = new BlockDefinition(new BlockEnergyCellCreative("basic", TileEntityCreativeEnergyCellBasic.class));
		energyCellNormal = new BlockDefinition(new BlockEnergyCell("normal", TileEntityEnergyCellNormal.class));
		energyCellNormalCreative = new BlockDefinition(new BlockEnergyCellCreative("normal", TileEntityCreativeEnergyCellNormal.class));
		energyCellDense = new BlockDefinition(new BlockEnergyCell("dense", TileEntityEnergyCellDense.class));
		energyCellDenseCreative = new BlockDefinition(new BlockEnergyCellCreative("dense", TileEntityCreativeEnergyCellDense.class));

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
		wirelessEmitter = new BlockDefinition(new BlockWirelessEmitter());
		wirelessReceiver = new BlockDefinition(new BlockWirelessReceiver());

		metalCrate = new BlockDefinition(new BlockMetalCrate());

		floorEngraving1 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_engraving1")
			.setBlockName("yatm.floor_engraving1"));

		floorEngraving2 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_engraving2")
			.setBlockName("yatm.floor_engraving2"));

		floorWarning1 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_warning1")
			.setBlockName("yatm.floor_warning1"));

		floorWarning2 = new BlockDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_warning2")
			.setBlockName("yatm.floor_warning2"));

		lamp = new BlockDefinition(new BlockLamp());

		reinforcedGlass = new BlockDecorativeDefinition(new BlockDecorative8(Material.glass));
		reinforcedGlass.getBlock().setStepSound(Block.soundTypeGlass)
			.setBlockName("yatm.reinforced_glass")
			.setBlockTextureName("yatm:reinforced_glass");
		reinforcedGlass.getBlock().setOpaque(false);


		ventedMesh2x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh2x.getBlock().setBlockTextureName("yatm:vented_mesh_2x")
			.setBlockName("yatm.vented_mesh_2x");

		ventedMesh2x.getBlock().setOpaque(false);

		ventedMesh4x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh4x.getBlock().setBlockTextureName("yatm:vented_mesh_4x")
			.setBlockName("yatm.vented_mesh_4x");

		ventedMesh4x.getBlock().setOpaque(false);

		ventedMesh8x = new BlockDecorativeDefinition(new BlockDecorative8(Material.rock));
		ventedMesh8x.getBlock().setBlockTextureName("yatm:vented_mesh_8x")
			.setBlockName("yatm.vented_mesh_8x");

		ventedMesh8x.getBlock().setOpaque(false);

		warningStripes2x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes2x.getBlock().setBlockTextureName("yatm:warning_stripes_2x")
			.setBlockName("yatm.warning_stripes_2x");

		warningStripes4x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes4x.getBlock().setBlockTextureName("yatm:warning_stripes_4x")
			.setBlockName("yatm.warning_stripes_4x");

		warningStripes8x = new BlockDecorativeDefinition(new BlockDecorative(Material.rock));
		warningStripes8x.getBlock().setBlockTextureName("yatm:warning_stripes_8x")
			.setBlockName("yatm.warning_stripes_8x");

		window = new BlockDefinition(new BlockWindow());
	}

	@Override
	public void register()
	{
		chassis.register("yatm.chassis");

		energyCellBasic.register("yatm.energy_cell_basic", ItemBlockEnergyCell.class);
		energyCellBasicCreative.register("yatm.energy_cell_basic_creative");
		energyCellNormal.register("yatm.energy_cell_normal", ItemBlockEnergyCell.class);
		energyCellNormalCreative.register("yatm.energy_cell_normal_creative");
		energyCellDense.register("yatm.energy_cell_dense", ItemBlockEnergyCell.class);
		energyCellDenseCreative.register("yatm.energy_cell_dense_creative");

		autoCrafter.register("yatm.auto_crafter", ItemBlockMachine.class);
		autoGrinder.register("yatm.auto_grinder", ItemBlockMachine.class);
		coalGenerator.register("yatm.coal_generator", ItemBlockMachine.class);
		compactor.register("yatm.compactor", ItemBlockMachine.class);
		crusher.register("yatm.crusher", ItemBlockMachine.class);
		electrolyser.register("yatm.electrolyser", ItemBlockMachine.class);
		fluxFurnace.register("yatm.flux_furnace", ItemBlockMachine.class);
		heater.register("yatm.heater", ItemBlockMachine.class);
		miniBlastFurnace.register("yatm.mini_blast_furnace", ItemBlockMachine.class);
		mixer.register("yatm.mixer", ItemBlockMachine.class);
		roller.register("yatm.roller", ItemBlockMachine.class);
		solarPanel.register("yatm.solar_panel", ItemBlockMachine.class);
		wirelessEmitter.register("yatm.wireless_emitter", ItemBlockMachine.class);
		wirelessReceiver.register("yatm.wireless_receiver", ItemBlockMachine.class);

		floorEngraving1.register("yatm.floor_engraving1");
		floorEngraving2.register("yatm.floor_engraving2");
		floorWarning1.register("yatm.floor_warning1");
		floorWarning2.register("yatm.floor_warning2");
		lamp.register("yatm.lamp");
		metalCrate.register("yatm.metal_crate");
		reinforcedGlass.register("yatm.reinforced_glass");
		ventedMesh2x.register("yatm.vented_mesh_2x");
		ventedMesh4x.register("yatm.vented_mesh_4x");
		ventedMesh8x.register("yatm.vented_mesh_8x");
		warningStripes2x.register("yatm.warning_stripes_2x");
		warningStripes4x.register("yatm.warning_stripes_4x");
		warningStripes8x.register("yatm.warning_stripes_8x");
		window.register("yatm.window");

		GameRegistry.registerTileEntity(TileEntityAutoCrafter.class, "yatm.tileentity.auto_crafter");
		GameRegistry.registerTileEntity(TileEntityAutoGrinder.class, "yatm.tileentity.auto_grinder");
		GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, "yatm.tileentity.coal_generator");
		GameRegistry.registerTileEntity(TileEntityCompactor.class, "yatm.tileentity.compactor");
		GameRegistry.registerTileEntity(TileEntityCrate.class, "yatm.tileentity.crate");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellBasic.class, "yatm.tileentity.creative_energy_cell_basic");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellDense.class, "yatm.tileentity.creative_energy_cell_dense");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellNormal.class, "yatm.tileentity.creative_energy_cell_normal");
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "yatm.tileentity.crusher");
		GameRegistry.registerTileEntity(TileEntityElectrolyser.class, "yatm.tileentity.electrolyser");
		GameRegistry.registerTileEntity(TileEntityEnergyCellBasic.class, "yatm.tileentity.energy_cell_basic");
		GameRegistry.registerTileEntity(TileEntityEnergyCellDense.class, "yatm.tileentity.energy_cell_dense");
		GameRegistry.registerTileEntity(TileEntityEnergyCellNormal.class, "yatm.tileentity.energy_cell_normal");
		GameRegistry.registerTileEntity(TileEntityFluxFurnace.class, "yatm.tileentity.flux_furnace");
		GameRegistry.registerTileEntity(TileEntityFuelGenerator.class, "yatm.tileentity.fuel_generator");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "yatm.tileentity.heater");
		GameRegistry.registerTileEntity(TileEntityMiniBlastFurnace.class, "yatm.tileentity.mini_blast_furnace");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "yatm.tileentity.mixer");
		GameRegistry.registerTileEntity(TileEntityRoller.class, "yatm.tileentity.roller");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "yatm.tileentity.solar_panel");
		GameRegistry.registerTileEntity(TileEntityWirelessReceiver.class, "yatm.tileentity.wireless_receiver");
		GameRegistry.registerTileEntity(TileEntityWirelessEmitter.class, "yatm.tileentity.wireless_emitter");
	}
}
