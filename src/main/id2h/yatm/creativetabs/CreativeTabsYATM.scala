package id2h.yatm.creativetabs

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import id2h.yatm.Blocks
import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs

class CreativeTabsYATM(name: String) extends CreativeTabs(name) {
	@SideOnly(Side.CLIENT)
	override def getTabIconItem(): Item = Item.getItemFromBlock(Blocks.chassis)
}

object CreativeTabsYATM {
	private val INSTANCE: CreativeTabs = new CreativeTabsYATM("yatm")

	def instance() = INSTANCE
}
