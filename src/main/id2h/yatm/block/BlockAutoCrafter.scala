package id2h.yatm.block

import id2h.yatm.creativetabs.CreativeTabsYATM
import id2h.yatm.tileentity.TileEntityAutoCrafter

import cpw.mods.fml.relauncher.{Side, SideOnly}

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockAutoCrafter extends Block(Material.rock) with ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	val icons = new Array[IIcon](3)

	this.setStepSound(Block.soundTypeStone)
	this.setHardness(2.0F)
	this.setResistance(5.0F)
	this.setBlockName("yatm.BlockAutoCrafter")
	this.setCreativeTab(CreativeTabsYATM.instance())

	@SideOnly(Side.CLIENT)
	override def registerBlockIcons(reg: IIconRegister) {
		icons(0) = reg.registerIcon("yatm:BlockAutocrafterBottom")
		icons(1) = reg.registerIcon("yatm:BlockAutocrafterTop")
		icons(2) = reg.registerIcon("yatm:BlockAutocrafterSide")
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

	def getTileEntity(world: World, x: Int, y: Int, z: Int): TileEntityAutoCrafter = {
		world.getTileEntity(x, y, z).asInstanceOf[TileEntityAutoCrafter]
	}

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, p: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		val tg: TileEntityAutoCrafter = this.getTileEntity(world, x, y, z)
		if (tg != null && !p.isSneaking())
		{

			//Platform.openGUI(p, tg, ForgeDirection.getOrientation( side ), GuiBridge.GUI_GRINDER)
			return true
		}
		return false
	}

	override def createNewTileEntity(world: World, unused: Int): TileEntity = {
		new TileEntityAutoCrafter()
	}
}
