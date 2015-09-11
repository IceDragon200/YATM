package id2h.yatm.block

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.IBlockAccess
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon

class BlockWarningStripes extends Block(Material.rock)
{
	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](3)

	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockWarningStripes")

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockWarningStripes.2x")
		icons(1) = reg.registerIcon("yatm:BlockWarningStripes.4x")
		icons(2) = reg.registerIcon("yatm:BlockWarningStripes.8x")
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(w: IBlockAccess, x: Int, y: Int, z: Int, s: Int): IIcon = {
		return this.getIcon(0, w.getBlockMetadata( x, y, z ))
	}

	@SideOnly( Side.CLIENT )
	override def getIcon(direction: Int, metadata: Int): IIcon = {
		return icons(metadata)
	}
}
