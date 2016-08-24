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

import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.BlockTypeDefinition;
import growthcraft.core.common.GrcModuleBlocks;
import io.polyfox.yatm.common.block.BlockAutoCrafter;
import io.polyfox.yatm.common.block.BlockAutoGrinder;
import io.polyfox.yatm.common.block.BlockChassis;
import io.polyfox.yatm.common.block.BlockCoalGenerator;
import io.polyfox.yatm.common.block.BlockCompactor;
import io.polyfox.yatm.common.block.BlockCrusher;
import io.polyfox.yatm.common.block.BlockDecorative8;
import io.polyfox.yatm.common.block.BlockDecorative;
import io.polyfox.yatm.common.block.BlockDecorativeBase;
import io.polyfox.yatm.common.block.BlockElectrolyser;
import io.polyfox.yatm.common.block.BlockEnergyCell;
import io.polyfox.yatm.common.block.BlockEnergyCellCreative;
import io.polyfox.yatm.common.block.BlockFluidReplicator;
import io.polyfox.yatm.common.block.BlockFluxFurnace;
import io.polyfox.yatm.common.block.BlockFluxSwitch;
import io.polyfox.yatm.common.block.BlockHeater;
import io.polyfox.yatm.common.block.BlockItemReplicator;
import io.polyfox.yatm.common.block.BlockLamp;
import io.polyfox.yatm.common.block.BlockMetalCrate;
import io.polyfox.yatm.common.block.BlockMiniBlastFurnace;
import io.polyfox.yatm.common.block.BlockMixer;
import io.polyfox.yatm.common.block.BlockRoller;
import io.polyfox.yatm.common.block.BlockSolarPanel;
import io.polyfox.yatm.common.block.BlockWindow;
import io.polyfox.yatm.common.block.BlockWirelessRedstoneEmitter;
import io.polyfox.yatm.common.block.BlockWirelessRedstoneReceiver;
import io.polyfox.yatm.common.item.ItemBlockEnergyCell;
import io.polyfox.yatm.common.item.ItemBlockMachine;
import io.polyfox.yatm.common.tileentity.TileEntityAutoCrafter;
import io.polyfox.yatm.common.tileentity.TileEntityAutoGrinder;
import io.polyfox.yatm.common.tileentity.TileEntityCoalGenerator;
import io.polyfox.yatm.common.tileentity.TileEntityCompactor;
import io.polyfox.yatm.common.tileentity.TileEntityCrate;
import io.polyfox.yatm.common.tileentity.TileEntityCreativeEnergyCellBasic;
import io.polyfox.yatm.common.tileentity.TileEntityCreativeEnergyCellDense;
import io.polyfox.yatm.common.tileentity.TileEntityCreativeEnergyCellNormal;
import io.polyfox.yatm.common.tileentity.TileEntityCrusher;
import io.polyfox.yatm.common.tileentity.TileEntityElectrolyser;
import io.polyfox.yatm.common.tileentity.TileEntityEnergyCellBasic;
import io.polyfox.yatm.common.tileentity.TileEntityEnergyCellDense;
import io.polyfox.yatm.common.tileentity.TileEntityEnergyCellNormal;
import io.polyfox.yatm.common.tileentity.TileEntityFluidReplicator;
import io.polyfox.yatm.common.tileentity.TileEntityFluxFurnace;
import io.polyfox.yatm.common.tileentity.TileEntityFluxSwitch;
import io.polyfox.yatm.common.tileentity.TileEntityFuelGenerator;
import io.polyfox.yatm.common.tileentity.TileEntityHeater;
import io.polyfox.yatm.common.tileentity.TileEntityItemReplicator;
import io.polyfox.yatm.common.tileentity.TileEntityMiniBlastFurnace;
import io.polyfox.yatm.common.tileentity.TileEntityMixer;
import io.polyfox.yatm.common.tileentity.TileEntityRoller;
import io.polyfox.yatm.common.tileentity.TileEntitySolarPanel;
import io.polyfox.yatm.common.tileentity.TileEntityWirelessRedstoneEmitter;
import io.polyfox.yatm.common.tileentity.TileEntityWirelessRedstoneReceiver;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInstances extends GrcModuleBlocks
{
	public BlockTypeDefinition<? extends BlockDecorativeBase> reinforcedGlass;
	public BlockTypeDefinition<? extends BlockDecorativeBase> ventedMesh2x;
	public BlockTypeDefinition<? extends BlockDecorativeBase> ventedMesh4x;
	public BlockTypeDefinition<? extends BlockDecorativeBase> ventedMesh8x;
	public BlockTypeDefinition<? extends BlockDecorativeBase> warningStripes2x;
	public BlockTypeDefinition<? extends BlockDecorativeBase> warningStripes4x;
	public BlockTypeDefinition<? extends BlockDecorativeBase> warningStripes8x;
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
	public BlockDefinition fluidReplicator;
	public BlockDefinition fluxFurnace;
	public BlockDefinition fluxSwitch;
	public BlockDefinition heater;
	public BlockDefinition itemReplicator;
	public BlockDefinition lamp;
	public BlockDefinition metalCrate;
	public BlockDefinition miniBlastFurnace;
	public BlockDefinition mixer;
	public BlockDefinition roller;
	public BlockDefinition solarPanel;
	public BlockDefinition window;
	public BlockDefinition wirelessRedstoneEmitter;
	public BlockDefinition wirelessRedstoneReceiver;

	public BlockInstances() {}

	@Override
	public void preInit()
	{
		chassis = newDefinition(new BlockChassis());

		solarPanel = newDefinition(new BlockSolarPanel());

		energyCellBasic = newDefinition(new BlockEnergyCell("basic", TileEntityEnergyCellBasic.class));
		energyCellBasicCreative = newDefinition(new BlockEnergyCellCreative("basic", TileEntityCreativeEnergyCellBasic.class));
		energyCellNormal = newDefinition(new BlockEnergyCell("normal", TileEntityEnergyCellNormal.class));
		energyCellNormalCreative = newDefinition(new BlockEnergyCellCreative("normal", TileEntityCreativeEnergyCellNormal.class));
		energyCellDense = newDefinition(new BlockEnergyCell("dense", TileEntityEnergyCellDense.class));
		energyCellDenseCreative = newDefinition(new BlockEnergyCellCreative("dense", TileEntityCreativeEnergyCellDense.class));

		autoCrafter = newDefinition(new BlockAutoCrafter());
		autoGrinder = newDefinition(new BlockAutoGrinder());
		coalGenerator = newDefinition(new BlockCoalGenerator());
		compactor = newDefinition(new BlockCompactor());
		crusher = newDefinition(new BlockCrusher());
		electrolyser = newDefinition(new BlockElectrolyser());
		fluidReplicator = newDefinition(new BlockFluidReplicator());
		fluxFurnace = newDefinition(new BlockFluxFurnace());
		fluxSwitch = newDefinition(new BlockFluxSwitch());
		heater = newDefinition(new BlockHeater());
		itemReplicator = newDefinition(new BlockItemReplicator());
		miniBlastFurnace = newDefinition(new BlockMiniBlastFurnace());
		mixer = newDefinition(new BlockMixer());
		roller = newDefinition(new BlockRoller());
		wirelessRedstoneEmitter = newDefinition(new BlockWirelessRedstoneEmitter());
		wirelessRedstoneReceiver = newDefinition(new BlockWirelessRedstoneReceiver());

		metalCrate = newDefinition(new BlockMetalCrate());

		floorEngraving1 = newDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_engraving1")
			.setBlockName("yatm.floor_engraving1"));

		floorEngraving2 = newDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_engraving2")
			.setBlockName("yatm.floor_engraving2"));

		floorWarning1 = newDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_warning1")
			.setBlockName("yatm.floor_warning1"));

		floorWarning2 = newDefinition(new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:floor_warning2")
			.setBlockName("yatm.floor_warning2"));

		lamp = newDefinition(new BlockLamp());

		reinforcedGlass = newTypedDefinition(new BlockDecorative8(Material.glass));
		reinforcedGlass.getBlock().setStepSound(Block.soundTypeGlass)
			.setBlockName("yatm.reinforced_glass")
			.setBlockTextureName("yatm:reinforced_glass");
		reinforcedGlass.getBlock().setOpaque(false);


		ventedMesh2x = newTypedDefinition(new BlockDecorative8(Material.rock));
		ventedMesh2x.getBlock().setBlockTextureName("yatm:vented_mesh_2x")
			.setBlockName("yatm.vented_mesh_2x");

		ventedMesh2x.getBlock().setOpaque(false);

		ventedMesh4x = newTypedDefinition(new BlockDecorative8(Material.rock));
		ventedMesh4x.getBlock().setBlockTextureName("yatm:vented_mesh_4x")
			.setBlockName("yatm.vented_mesh_4x");

		ventedMesh4x.getBlock().setOpaque(false);

		ventedMesh8x = newTypedDefinition(new BlockDecorative8(Material.rock));
		ventedMesh8x.getBlock().setBlockTextureName("yatm:vented_mesh_8x")
			.setBlockName("yatm.vented_mesh_8x");

		ventedMesh8x.getBlock().setOpaque(false);

		warningStripes2x = newTypedDefinition(new BlockDecorative(Material.rock));
		warningStripes2x.getBlock().setBlockTextureName("yatm:warning_stripes_2x")
			.setBlockName("yatm.warning_stripes_2x");

		warningStripes4x = newTypedDefinition(new BlockDecorative(Material.rock));
		warningStripes4x.getBlock().setBlockTextureName("yatm:warning_stripes_4x")
			.setBlockName("yatm.warning_stripes_4x");

		warningStripes8x = newTypedDefinition(new BlockDecorative(Material.rock));
		warningStripes8x.getBlock().setBlockTextureName("yatm:warning_stripes_8x")
			.setBlockName("yatm.warning_stripes_8x");

		window = newDefinition(new BlockWindow());
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
		fluidReplicator.register("yatm.fluid_replicator", ItemBlockMachine.class);
		fluxFurnace.register("yatm.flux_furnace", ItemBlockMachine.class);
		fluxSwitch.register("yatm.flux_switch", ItemBlockMachine.class);
		heater.register("yatm.heater", ItemBlockMachine.class);
		itemReplicator.register("yatm.item_replicator", ItemBlockMachine.class);
		miniBlastFurnace.register("yatm.mini_blast_furnace", ItemBlockMachine.class);
		mixer.register("yatm.mixer", ItemBlockMachine.class);
		roller.register("yatm.roller", ItemBlockMachine.class);
		solarPanel.register("yatm.solar_panel", ItemBlockMachine.class);
		wirelessRedstoneEmitter.register("yatm.wireless_redstone_emitter", ItemBlockMachine.class);
		wirelessRedstoneReceiver.register("yatm.wireless_redstone_receiver", ItemBlockMachine.class);

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
		GameRegistry.registerTileEntity(TileEntityFluidReplicator.class, "yatm.tileentity.fluid_replicator");
		GameRegistry.registerTileEntity(TileEntityFluxFurnace.class, "yatm.tileentity.flux_furnace");
		GameRegistry.registerTileEntity(TileEntityFluxSwitch.class, "yatm.tileentity.flux_switch");
		GameRegistry.registerTileEntity(TileEntityFuelGenerator.class, "yatm.tileentity.fuel_generator");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "yatm.tileentity.heater");
		GameRegistry.registerTileEntity(TileEntityItemReplicator.class, "yatm.tileentity.item_replicator");
		GameRegistry.registerTileEntity(TileEntityMiniBlastFurnace.class, "yatm.tileentity.mini_blast_furnace");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "yatm.tileentity.mixer");
		GameRegistry.registerTileEntity(TileEntityRoller.class, "yatm.tileentity.roller");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "yatm.tileentity.solar_panel");
		GameRegistry.registerTileEntity(TileEntityWirelessRedstoneEmitter.class, "yatm.tileentity.wireless_redstone_emitter");
		GameRegistry.registerTileEntity(TileEntityWirelessRedstoneReceiver.class, "yatm.tileentity.wireless_redstone_receiver");
	}
}
