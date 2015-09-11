package id2h.yatm.item

import java.util.EnumSet

import appeng.api.recipes.ResolverResult
import appeng.core.features.AEFeature
import appeng.items.AEBaseItem

class ItemDust(val dustType: Int) extends AEBaseItem() {
	dustType match {
		case Dusts.UraniumDust =>
			this.setUnlocalizedName("yatm.ItemUraniumDust")
			this.setTextureName("yatm:ItemMaterial.UraniumDust")
		case Dusts.PurifiedUraniumDust =>
			this.setUnlocalizedName("yatm.ItemPurifiedUraniumDust")
			this.setTextureName("yatm:ItemMaterial.PurifiedUraniumDust")
		case _ => "null"
	}
	this.setFeature(EnumSet.of(AEFeature.Core));
}
