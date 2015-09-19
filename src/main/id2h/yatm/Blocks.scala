package id2h.yatm

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.block.BlockDecorative
import id2h.yatm.block.BlockDecorative8
import id2h.yatm.tileentity.TileEntityAutoCrafter
import id2h.yatm.tileentity.TileEntityAutoGrinder
import id2h.yatm.tileentity.TileEntityDryer
import id2h.yatm.tileentity.TileEntityElectrolyser
import id2h.yatm.tileentity.TileEntitySolarPanel

import appeng.block.AEBaseBlock
import appeng.core.api.definitions.DefinitionConstructor
import appeng.core.CommonHelper
import appeng.util.Platform

import cpw.mods.fml.common.registry.GameRegistry

import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

object Blocks {
	var autoCrafter: Block = _
	var autoGrinder: Block = _
	var chassis: Block = _
	var floorEngraving1: Block = _
	var floorEngraving2: Block = _
	var floorWarning1: Block = _
	var floorWarning2: Block = _
	var dryer: Block = _
	var electrolyser: Block = _
	var lamp: Block = _
	var metalCrate: Block = _
	var mixer: Block = _
	var reinforcedGlass: Block = _
	var solarPanel: Block = _
	var ventedMesh2x: Block = _
	var ventedMesh4x: Block = _
	var ventedMesh8x: Block = _
	var warningStripes2x: Block = _
	var warningStripes4x: Block = _
	var warningStripes8x: Block = _
	var window: Block = _

	def register(constructor: DefinitionConstructor) {
		autoCrafter = new block.BlockAutoCrafter()
		autoGrinder = new block.BlockAutoGrinder()
		chassis = new block.BlockChassis()
		floorEngraving1 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving1")
			.setBlockName("yatm.BlockFloorEngraving1")
		floorEngraving2 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorEngraving2")
			.setBlockName("yatm.BlockFloorEngraving2")
		floorWarning1 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning1")
			.setBlockName("yatm.BlockFloorWarning1")
		floorWarning2 = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockFloorWarning2")
			.setBlockName("yatm.BlockFloorWarning2")

		dryer = new block.BlockDryer()
		electrolyser = new block.BlockElectrolyser()
		lamp = new block.BlockLamp()
		metalCrate = new block.BlockMetalCrate()
		mixer = new block.BlockMixer()

		reinforcedGlass = new BlockDecorative8(Material.glass)
			.setStepSound(Block.soundTypeGlass)
			.setBlockTextureName("yatm:BlockReinforcedGlass")
			.setBlockName("yatm.BlockReinforcedGlass")
		reinforcedGlass.asInstanceOf[BlockDecorative8].setOpaque(false)

		solarPanel = new block.BlockSolarPanel()

		ventedMesh2x = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockVentedMesh.2x")
			.setBlockName("yatm.BlockVentedMesh.2x")
		ventedMesh2x.asInstanceOf[BlockDecorative8].setOpaque(false)

		ventedMesh4x = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockVentedMesh.4x")
			.setBlockName("yatm.BlockVentedMesh.4x")
		ventedMesh4x.asInstanceOf[BlockDecorative8].setOpaque(false)

		ventedMesh8x = new BlockDecorative8(Material.rock)
			.setBlockTextureName("yatm:BlockVentedMesh.8x")
			.setBlockName("yatm.BlockVentedMesh.8x")
		ventedMesh8x.asInstanceOf[BlockDecorative8].setOpaque(false)

		warningStripes2x = new BlockDecorative(Material.rock)
			.setBlockTextureName("yatm:BlockWarningStripes.2x")
			.setBlockName("yatm.BlockWarningStripes.2x")

		warningStripes4x = new BlockDecorative(Material.rock)
			.setBlockTextureName("yatm:BlockWarningStripes.4x")
			.setBlockName("yatm.BlockWarningStripes.4x")

		warningStripes8x = new BlockDecorative(Material.rock)
			.setBlockTextureName("yatm:BlockWarningStripes.8x")
			.setBlockName("yatm.BlockWarningStripes.8x")

		window = new block.BlockWindow()


		GameRegistry.registerBlock(autoCrafter, "BlockAutoCrafter")
		GameRegistry.registerBlock(autoGrinder, "BlockAutoGrinder")
		GameRegistry.registerBlock(chassis, "BlockChassis")
		GameRegistry.registerBlock(floorEngraving1, "BlockFloorEngraving1")
		GameRegistry.registerBlock(floorEngraving2, "BlockFloorEngraving2")
		GameRegistry.registerBlock(floorWarning1, "BlockFloorWarning1")
		GameRegistry.registerBlock(floorWarning2, "BlockFloorWarning2")
		GameRegistry.registerBlock(dryer, "BlockDryer")
		GameRegistry.registerBlock(electrolyser, "BlockElectrolyser")
		GameRegistry.registerBlock(lamp, "BlockLamp")
		GameRegistry.registerBlock(metalCrate, "BlockMetalCrate")
		GameRegistry.registerBlock(mixer, "BlockMixer")
		GameRegistry.registerBlock(reinforcedGlass, "BlockReinforcedGlass")
		GameRegistry.registerBlock(solarPanel, "BlockSolarPanel")
		GameRegistry.registerBlock(ventedMesh2x, "BlockVentedMesh.2x.tiled")
		GameRegistry.registerBlock(ventedMesh4x, "BlockVentedMesh.4x.tiled")
		GameRegistry.registerBlock(ventedMesh8x, "BlockVentedMesh.8x.tiled")
		GameRegistry.registerBlock(warningStripes2x, "BlockWarningStripes.2x.tiled")
		GameRegistry.registerBlock(warningStripes4x, "BlockWarningStripes.4x.tiled")
		GameRegistry.registerBlock(warningStripes8x, "BlockWarningStripes.8x.tiled")
		GameRegistry.registerBlock(window, "BlockWindow")


		GameRegistry.registerTileEntity(classOf[TileEntityAutoCrafter], "yatm.tileentity.TileEntityAutoCrafter")
		GameRegistry.registerTileEntity(classOf[TileEntityAutoGrinder], "yatm.tileentity.TileEntityAutoGrinder")
		GameRegistry.registerTileEntity(classOf[TileEntityDryer], "yatm.tileentity.TileEntityDryer")
		GameRegistry.registerTileEntity(classOf[TileEntityElectrolyser], "yatm.tileentity.TileEntityElectrolyser")
		GameRegistry.registerTileEntity(classOf[TileEntitySolarPanel], "yatm.tileentity.TileEntitySolarPanel")
	}
}
