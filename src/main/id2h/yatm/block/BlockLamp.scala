package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister

class BlockLamp extends Block(Material.glass)
{
	this.setStepSound(Block.soundTypeGlass)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setLightLevel(1.0f)
	this.setBlockName("yatm.BlockLamp")
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		this.blockIcon = reg.registerIcon("yatm:BlockLamp.On")
	}
}
