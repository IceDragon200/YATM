package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.tileentity.TileEntityElectrolyser

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockElectrolyser extends Block(Material.rock) with ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](3)

	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockElectrolyser")
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockElectrolyserBottom")
		icons(1) = reg.registerIcon("yatm:BlockElectrolyserTop")
		icons(2) = reg.registerIcon("yatm:BlockElectrolyserSide")
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

	override def createNewTileEntity(world: World, unused: Int): TileEntity = {
		new TileEntityElectrolyser()
	}
}
