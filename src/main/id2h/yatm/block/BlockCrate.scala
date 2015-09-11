package id2h.yatm.block

import cpw.mods.fml.relauncher.{Side, SideOnly}
//import net.minecraft.block.BlockContainer
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister

//class BlockCrate extends BlockContainer(Material.rock) {
class BlockCrate extends Block(Material.rock)
{
	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockTank")

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		this.blockIcon = reg.registerIcon("yatm:BlockTankSide")
	}
}
