package id2h.yatm.common.item;

import java.util.EnumSet;

import appeng.core.features.AEFeature;
import appeng.items.AEBaseItem;

public class ItemDust extends AEBaseItem
{
	public ItemDust(int dustType)
	{
		super();
		switch (dustType)
		{
			case Dusts.UraniumDust:
				setUnlocalizedName("yatm.ItemUraniumDust");
				setTextureName("yatm:ItemMaterial.UraniumDust");
				break;
			case Dusts.PurifiedUraniumDust:
				setUnlocalizedName("yatm.ItemPurifiedUraniumDust");
				setTextureName("yatm:ItemMaterial.PurifiedUraniumDust");
				break;
			default:
				break;
		}
		setFeature(EnumSet.of(AEFeature.Core));
	}
}
