package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon

class BlockDryer extends Block(Material.rock)
{
	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](3)

	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockDryer")
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockDryerBottom")
		icons(1) = reg.registerIcon("yatm:BlockDryerTop")
		icons(2) = reg.registerIcon("yatm:BlockDryerSide")
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
}
