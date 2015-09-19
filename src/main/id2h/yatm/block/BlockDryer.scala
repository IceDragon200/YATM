package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.tileentity.TileEntityDryer

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraftforge.common.util.ForgeDirection

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockDryer extends Block(Material.rock) with ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](4)

	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockDryer")
	this.setCreativeTab(CreativeTabsYATM.instance())

	def getTileEntity(world: IBlockAccess, x: Int, y: Int, z: Int): TileEntityDryer = {
		world.getTileEntity(x, y, z).asInstanceOf[TileEntityDryer]
	}

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockDryerBottom")
		icons(1) = reg.registerIcon("yatm:BlockDryerTop")
		icons(2) = reg.registerIcon("yatm:BlockDryerSide.Offline")
		icons(3) = reg.registerIcon("yatm:BlockDryerSide")
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
		if (side == 0) {
			icons(0)
		} else if (side == 1) {
			icons(1)
		} else {
			val te = getTileEntity(world, x, y, z)
			//if (te != null && te.isOnline(ForgeDirection.getOrientation(side))) {
			//	return icons(3)
			//}
			icons(3)
		}
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

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, p: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		val te = this.getTileEntity(world, x, y, z)
		if (te != null && !p.isSneaking())
		{
			//
			return true
		}
		return false
	}

	override def createNewTileEntity(world: World, unused: Int): TileEntity = {
		new TileEntityDryer()
	}
}
