package id2h.yatm

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.block.BlockDecorative

import appeng.block.AEBaseBlock
import appeng.core.api.definitions.DefinitionConstructor
import appeng.core.CommonHelper
import appeng.util.Platform

import cpw.mods.fml.common.registry.GameRegistry

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

object Blocks {
	var autocrafter: Block = _
	var autoGrinder: Block = _
	var chassis: Block = _
	var crate: Block = _
	var dryer: Block = _
	var electrolyser: Block = _
	var lamp: Block = _
	var mixer: Block = _
	var solarPanel: Block = _
	var warningStripes: Block = _
	var window: Block = _
	var warningStripes2x: Block = _
	var warningStripes4x: Block = _
	var warningStripes8x: Block = _
	var ventedMesh2x: Block = _
	var ventedMesh4x: Block = _
	var ventedMesh8x: Block = _

	def register(constructor: DefinitionConstructor) {
		autocrafter = new block.BlockAutocrafter()
		autoGrinder = new block.BlockAutoGrinder()
		chassis = new block.BlockChassis()
		crate = new block.BlockCrate()
		dryer = new block.BlockDryer()
		electrolyser = new block.BlockElectrolyser()
		lamp = new block.BlockLamp()
		mixer = new block.BlockMixer()
		solarPanel = new block.BlockSolarPanel()
		warningStripes = new block.BlockWarningStripes()
		window = new block.BlockWindow()

		warningStripes2x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockWarningStripes.2x.tiled")
			.setBlockName("yatm.BlockWarningStripes.2x")

		warningStripes4x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockWarningStripes.4x.tiled")
			.setBlockName("yatm.BlockWarningStripes.4x")

		warningStripes8x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockWarningStripes.8x.tiled")
			.setBlockName("yatm.BlockWarningStripes.8x")

		ventedMesh2x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockVentedMesh.2x.tiled")
			.setBlockName("yatm.BlockVentedMesh.2x")
		ventedMesh2x.asInstanceOf[BlockDecorative].setOpaque(false)

		ventedMesh4x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockVentedMesh.4x.tiled")
			.setBlockName("yatm.BlockVentedMesh.4x")
		ventedMesh4x.asInstanceOf[BlockDecorative].setOpaque(false)

		ventedMesh8x = new BlockDecorative(Material.rock)
			.setStepSound(Block.soundTypeStone)
			.setHardness(2.0F)
			.setResistance(5.0F)
			.setBlockTextureName("yatm:BlockVentedMesh.8x.tiled")
			.setBlockName("yatm.BlockVentedMesh.8x")
		ventedMesh8x.asInstanceOf[BlockDecorative].setOpaque(false)

		autocrafter.setCreativeTab(CreativeTabsYATM.instance())
		chassis.setCreativeTab(CreativeTabsYATM.instance())
		crate.setCreativeTab(CreativeTabsYATM.instance())
		dryer.setCreativeTab(CreativeTabsYATM.instance())
		electrolyser.setCreativeTab(CreativeTabsYATM.instance())
		lamp.setCreativeTab(CreativeTabsYATM.instance())
		mixer.setCreativeTab(CreativeTabsYATM.instance())
		solarPanel.setCreativeTab(CreativeTabsYATM.instance())
		warningStripes.setCreativeTab(CreativeTabsYATM.instance())
		window.setCreativeTab(CreativeTabsYATM.instance())

		GameRegistry.registerBlock(autocrafter, "BlockAutocrafter")
		GameRegistry.registerBlock(autoGrinder, "BlockAutoGrinder")
		GameRegistry.registerBlock(chassis, "BlockChassis")
		GameRegistry.registerBlock(crate, "BlockCrate")
		GameRegistry.registerBlock(dryer, "BlockDryer")
		GameRegistry.registerBlock(electrolyser, "BlockElectrolyser")
		GameRegistry.registerBlock(lamp, "BlockLamp")
		GameRegistry.registerBlock(mixer, "BlockMixer")
		GameRegistry.registerBlock(solarPanel, "BlockSolarPanel")
		GameRegistry.registerBlock(warningStripes, "BlockWarningStripes")
		GameRegistry.registerBlock(window, "BlockWindow")

		GameRegistry.registerBlock(warningStripes2x, "BlockWarningStripes.2x.tiled")
		GameRegistry.registerBlock(warningStripes4x, "BlockWarningStripes.4x.tiled")
		GameRegistry.registerBlock(warningStripes8x, "BlockWarningStripes.8x.tiled")

		GameRegistry.registerBlock(ventedMesh2x, "BlockVentedMesh.2x.tiled")
		GameRegistry.registerBlock(ventedMesh4x, "BlockVentedMesh.4x.tiled")
		GameRegistry.registerBlock(ventedMesh8x, "BlockVentedMesh.8x.tiled")

		GameRegistry.registerTileEntity(tileentity.TileEntityAutoGrinder, "yatm.tileentity.TileAutoGrinder")
	}
}
