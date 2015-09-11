package id2h.yatm.item

import java.util.EnumSet

import appeng.api.recipes.ResolverResult
import appeng.core.features.AEFeature
import appeng.items.AEBaseItem

class ItemIngot(val ingotType: Int) extends AEBaseItem() {
	ingotType match {
		case Ingots.UraniumIngot =>
			this.setUnlocalizedName("yatm.ItemUraniumIngot")
			this.setTextureName("yatm:ItemMaterial.UraniumIngot")
		case Ingots.PurifiedUraniumIngot =>
			this.setUnlocalizedName("yatm.ItemPurifiedUraniumIngot")
			this.setTextureName("yatm:ItemMaterial.PurifiedUraniumIngot")
		case _ => "null"
	}
	this.setFeature(EnumSet.of(AEFeature.Core));
}
