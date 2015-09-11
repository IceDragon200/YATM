package id2h.yatm.block

import java.util.EnumSet

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.tileentity.TileEntityAutoGrinder

import appeng.core.sync.GuiBridge
import appeng.util.Platform

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

class BlockAutoGrinder extends Block(Material.rock) with ITileEntityProvider
{
	val icons = new Array[IIcon](3)
	this.setStepSound(Block.soundTypeStone)
	this.setHardness(3.2F)
	this.setBlockName("yatm.BlockAutoGrinder")
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockAutoGrinderBottom")
		icons(1) = reg.registerIcon("yatm:BlockAutoGrinderTop")
		icons(2) = reg.registerIcon("yatm:BlockAutoGrinderSide")
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(side: Int, meta: Int): IIcon = {
		if (side == 0) {
			icons(0)
		} else if (side == 1) {
			icons(1)
		} else {
			icons(2)
		}
	}

	def getTileEntity(world: World, x: Int, y: Int, z: Int): TileEntityAutoGrinder = {
		world.getTileEntity(x, y, z).asInstanceOf[TileEntityAutoGrinder]
	}

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, p: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		val tg: TileEntityAutoGrinder = this.getTileEntity(world, x, y, z)
		if (tg != null && !p.isSneaking())
		{

			Platform.openGUI(p, tg, ForgeDirection.getOrientation( side ), GuiBridge.GUI_GRINDER)
			return true
		}
		return false
	}

	override def createNewTileEntity(world: World, unused: Int): TileEntity = {
		new TileEntityAutoGrinder()
	}
}
