package id2h.yatm.init;

import id2h.yatm.common.block.BlockAutoCrafter;
import id2h.yatm.common.block.BlockAutoGrinder;
import id2h.yatm.common.block.BlockChassis;
import id2h.yatm.common.block.BlockCrusher;
import id2h.yatm.common.block.BlockDecorative8;
import id2h.yatm.common.block.BlockDecorative;
import id2h.yatm.common.block.BlockDryer;
import id2h.yatm.common.block.BlockElectrolyser;
import id2h.yatm.common.block.BlockEnergyCell;
import id2h.yatm.common.block.BlockEnergyCellCreative;
import id2h.yatm.common.block.BlockLamp;
import id2h.yatm.common.block.BlockMetalCrate;
import id2h.yatm.common.block.BlockMixer;
import id2h.yatm.common.block.BlockSolarPanel;
import id2h.yatm.common.block.BlockWindow;
import id2h.yatm.common.tileentity.TileEntityAutoCrafter;
import id2h.yatm.common.tileentity.TileEntityAutoGrinder;
import id2h.yatm.common.tileentity.TileEntityCrusher;
import id2h.yatm.common.tileentity.TileEntityDryer;
import id2h.yatm.common.tileentity.TileEntityElectrolyser;
import id2h.yatm.common.tileentity.TileEntityEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellBasic;
import id2h.yatm.common.tileentity.TileEntityEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellNormal;
import id2h.yatm.common.tileentity.TileEntityEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCellDense;
import id2h.yatm.common.tileentity.TileEntityMixer;
import id2h.yatm.common.tileentity.TileEntitySolarPanel;

import appeng.core.api.definitions.DefinitionConstructor;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Blocks
{
	public static Block autoCrafter;
	public static Block autoGrinder;
	public static Block energyCellBasic;
	public static Block energyCellBasicCreative;
	public static Block energyCellNormal;
	public static Block energyCellNormalCreative;
	public static Block energyCellDense;
	public static Block energyCellDenseCreative;
	public static Block chassis;
	public static Block crusher;
	public static Block floorEngraving1;
	public static Block floorEngraving2;
	public static Block floorWarning1;
	public static Block floorWarning2;
	public static Block dryer;
	public static Block electrolyser;
	public static Block lamp;
	public static Block metalCrate;
	public static Block mixer;
	public static BlockDecorative8 reinforcedGlass;
	public static Block solarPanel;
	public static BlockDecorative8 ventedMesh2x;
	public static BlockDecorative8 ventedMesh4x;
	public static BlockDecorative8 ventedMesh8x;
	public static BlockDecorative warningStripes2x;
	public static BlockDecorative warningStripes4x;
	public static BlockDecorative warningStripes8x;
	public static Block window;

	private Blocks() {}

	public static void preInit(DefinitionConstructor constructor)
	{
		autoCrafter = new BlockAutoCrafter();
		autoGrinder = new BlockAutoGrinder();
		chassis = new BlockChassis();
		crusher = new BlockCrusher();
		floorEngraving1 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving1")
			.setBlockName("yatm.BlockFloorEngraving1");

		floorEngraving2 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving2")
			.setBlockName("yatm.BlockFloorEngraving2");

		floorWarning1 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning1")
			.setBlockName("yatm.BlockFloorWarning1");

		floorWarning2 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning2")
			.setBlockName("yatm.BlockFloorWarning2");

		dryer = new BlockDryer();
		electrolyser = new BlockElectrolyser();
		lamp = new BlockLamp();
		metalCrate = new BlockMetalCrate();
		mixer = new BlockMixer();

		reinforcedGlass = new BlockDecorative8(Material.glass);
		reinforcedGlass.setStepSound(Block.soundTypeGlass)
			.setBlockName("yatm.BlockReinforcedGlass")
			.setBlockTextureName("yatm:BlockReinforcedGlass");
		reinforcedGlass.setOpaque(false);

		solarPanel = new BlockSolarPanel();

		ventedMesh2x = new BlockDecorative8(Material.rock);
		ventedMesh2x.setBlockTextureName("yatm:BlockVentedMesh.2x")
			.setBlockName("yatm.BlockVentedMesh.2x");

		ventedMesh2x.setOpaque(false);

		ventedMesh4x = new BlockDecorative8(Material.rock);
		ventedMesh4x.setBlockTextureName("yatm:BlockVentedMesh.4x")
			.setBlockName("yatm.BlockVentedMesh.4x");

		ventedMesh4x.setOpaque(false);

		ventedMesh8x = new BlockDecorative8(Material.rock);
		ventedMesh8x.setBlockTextureName("yatm:BlockVentedMesh.8x")
			.setBlockName("yatm.BlockVentedMesh.8x");

		ventedMesh8x.setOpaque(false);

		warningStripes2x = new BlockDecorative(Material.rock);
		warningStripes2x.setBlockTextureName("yatm:BlockWarningStripes.2x")
			.setBlockName("yatm.BlockWarningStripes.2x");

		warningStripes4x = new BlockDecorative(Material.rock);
		warningStripes4x.setBlockTextureName("yatm:BlockWarningStripes.4x")
			.setBlockName("yatm.BlockWarningStripes.4x");

		warningStripes8x = new BlockDecorative(Material.rock);
		warningStripes8x.setBlockTextureName("yatm:BlockWarningStripes.8x")
			.setBlockName("yatm.BlockWarningStripes.8x");

		window = new BlockWindow();

		energyCellBasic = new BlockEnergyCell("Basic", TileEntityEnergyCellBasic.class);
		energyCellBasicCreative = new BlockEnergyCellCreative("Basic", TileEntityCreativeEnergyCellBasic.class);
		energyCellNormal = new BlockEnergyCell("Normal", TileEntityEnergyCellNormal.class);
		energyCellNormalCreative = new BlockEnergyCellCreative("Normal", TileEntityCreativeEnergyCellNormal.class);
		energyCellDense = new BlockEnergyCell("Dense", TileEntityEnergyCellDense.class);
		energyCellDenseCreative = new BlockEnergyCellCreative("Dense", TileEntityCreativeEnergyCellDense.class);
	}

	public static void register()
	{
		GameRegistry.registerBlock(autoCrafter, "yatm.BlockAutoCrafter");
		GameRegistry.registerBlock(autoGrinder, "yatm.BlockAutoGrinder");
		GameRegistry.registerBlock(chassis, "yatm.BlockChassis");
		GameRegistry.registerBlock(crusher, "yatm.BlockCrusher");
		GameRegistry.registerBlock(floorEngraving1, "yatm.BlockFloorEngraving1");
		GameRegistry.registerBlock(floorEngraving2, "yatm.BlockFloorEngraving2");
		GameRegistry.registerBlock(floorWarning1, "yatm.BlockFloorWarning1");
		GameRegistry.registerBlock(floorWarning2, "yatm.BlockFloorWarning2");
		GameRegistry.registerBlock(dryer, "yatm.BlockDryer");
		GameRegistry.registerBlock(electrolyser, "yatm.BlockElectrolyser");
		GameRegistry.registerBlock(lamp, "yatm.BlockLamp");
		GameRegistry.registerBlock(metalCrate, "yatm.BlockMetalCrate");
		GameRegistry.registerBlock(mixer, "yatm.BlockMixer");
		GameRegistry.registerBlock(reinforcedGlass, "yatm.BlockReinforcedGlass");
		GameRegistry.registerBlock(solarPanel, "yatm.BlockSolarPanel");
		GameRegistry.registerBlock(ventedMesh2x, "yatm.BlockVentedMesh.2x.tiled");
		GameRegistry.registerBlock(ventedMesh4x, "yatm.BlockVentedMesh.4x.tiled");
		GameRegistry.registerBlock(ventedMesh8x, "yatm.BlockVentedMesh.8x.tiled");
		GameRegistry.registerBlock(warningStripes2x, "yatm.BlockWarningStripes.2x.tiled");
		GameRegistry.registerBlock(warningStripes4x, "yatm.BlockWarningStripes.4x.tiled");
		GameRegistry.registerBlock(warningStripes8x, "yatm.BlockWarningStripes.8x.tiled");
		GameRegistry.registerBlock(window, "yatm.BlockWindow");
		GameRegistry.registerBlock(energyCellBasic, "yatm.BlockEnergyCellBasic");
		GameRegistry.registerBlock(energyCellBasicCreative, "yatm.BlockEnergyCellBasicCreative");
		GameRegistry.registerBlock(energyCellNormal, "yatm.BlockEnergyCellNormal");
		GameRegistry.registerBlock(energyCellNormalCreative, "yatm.BlockEnergyCellNormalCreative");
		GameRegistry.registerBlock(energyCellDense, "yatm.BlockEnergyCellDense");
		GameRegistry.registerBlock(energyCellDenseCreative, "yatm.BlockEnergyCellDenseCreative");

		GameRegistry.registerTileEntity(TileEntityAutoCrafter.class, "yatm.tileentity.TileEntityAutoCrafter");
		GameRegistry.registerTileEntity(TileEntityAutoGrinder.class, "yatm.tileentity.TileEntityAutoGrinder");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellBasic.class, "yatm.tileentity.TileEntityCreativeEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellNormal.class, "yatm.tileentity.TileEntityCreativeEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyCellDense.class, "yatm.tileentity.TileEntityCreativeEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityEnergyCellBasic.class, "yatm.tileentity.TileEntityEnergyCellBasic");
		GameRegistry.registerTileEntity(TileEntityEnergyCellNormal.class, "yatm.tileentity.TileEntityEnergyCellNormal");
		GameRegistry.registerTileEntity(TileEntityEnergyCellDense.class, "yatm.tileentity.TileEntityEnergyCellDense");
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "yatm.tileentity.TileEntityCrusher");
		GameRegistry.registerTileEntity(TileEntityDryer.class, "yatm.tileentity.TileEntityDryer");
		GameRegistry.registerTileEntity(TileEntityElectrolyser.class, "yatm.tileentity.TileEntityElectrolyser");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "yatm.tileentity.TileEntityMixer");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "yatm.tileentity.TileEntitySolarPanel");
	}
}
