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

abstract class BlockDecorativeBase(material: Material) extends Block(material)
{
	protected var use8Dir: Boolean = false

	this.opaque = true
	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setCreativeTab(CreativeTabsYATM.instance())

	val icons: Array[IIcon]
	@SideOnly(Side.CLIENT)
	var noIcon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
		//val meta: Int = world.getBlockMetadata(x, y, z)
		val value: Int = ConnectedTextureSolver.iconForSide(world, x, y, z, this, side, use8Dir)

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
