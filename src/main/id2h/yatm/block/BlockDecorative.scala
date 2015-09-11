package id2h.yatm.block

import id2h.yatm.client.util.ConnectedTextureSolver
import id2h.yatm.creativetabs.CreativeTabsYATM

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockDecorative(material: Material) extends Block(material)
{
	this.opaque = true
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](16)
	var noIcon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		val basename: String = getTextureName()
		var i: Int = 0
		for (i <- 0 to 15) {
			val name: String = basename + "." + i
			icons(i) = reg.registerIcon(name)
		}
		noIcon = reg.registerIcon("yatm:blank")
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
		//val meta: Int = world.getBlockMetadata(x, y, z)
		val value: Int = ConnectedTextureSolver.solveForSide(world, x, y, z, this, side)

		if (!this.opaque) {
			if ((value & ConnectedTextureSolver.OBSTRUCTED) == ConnectedTextureSolver.OBSTRUCTED) {
				return noIcon
			}
		}

		icons(value & ConnectedTextureSolver.MASK)
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(side: Int, meta: Int): IIcon = {
		icons(0)
	}

	def setOpaque(state: Boolean): Block = {
		this.opaque = state
		return this
	}

	override def isOpaqueCube(): Boolean = opaque
}
