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

// 8 direction solving
class BlockDecorative8(material: Material) extends BlockDecorativeBase(material)
{
	this.use8Dir = true

	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](47)

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		val basename: String = getTextureName()
		var i: Int = 0
		for (i <- 0 to 46) {
			val name: String = basename + "/" + i
			icons(i) = reg.registerIcon(name)
		}
		noIcon = reg.registerIcon("yatm:blank")
	}
}
