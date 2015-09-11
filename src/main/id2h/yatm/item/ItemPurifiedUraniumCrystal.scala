package id2h.yatm.item

import java.util.EnumSet

import appeng.api.recipes.ResolverResult
import appeng.core.features.AEFeature
import appeng.items.AEBaseItem

class ItemPurifiedUraniumCrystal extends AEBaseItem() {
	this.setFeature(EnumSet.of(AEFeature.Core));
	this.setTextureName("yatm:ItemMaterial.PurifiedUraniumCrystal")
	this.setUnlocalizedName("yatm.ItemPurifiedUraniumCrystal")
}
