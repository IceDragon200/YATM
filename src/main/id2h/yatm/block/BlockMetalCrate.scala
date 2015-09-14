package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM

import cpw.mods.fml.relauncher.{Side, SideOnly}

//import net.minecraft.block.BlockContainer
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister

//class BlockCrate extends BlockContainer(Material.rock) {
class BlockMetalCrate extends Block(Material.rock)
{
	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockMetalCrate")
	this.setBlockTextureName("yatm:BlockMetalCrate")
	this.setCreativeTab(CreativeTabsYATM.instance())
}
