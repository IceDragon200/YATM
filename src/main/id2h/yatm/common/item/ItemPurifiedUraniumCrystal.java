package id2h.yatm.common.item;

import java.util.EnumSet;

import appeng.core.features.AEFeature;
import appeng.items.AEBaseItem;

public class ItemPurifiedUraniumCrystal extends AEBaseItem
{
	public ItemPurifiedUraniumCrystal()
	{
		super();
		this.setFeature(EnumSet.of(AEFeature.Core));
		this.setTextureName("yatm:ItemMaterial.PurifiedUraniumCrystal");
		this.setUnlocalizedName("yatm.ItemPurifiedUraniumCrystal");
	}
}
