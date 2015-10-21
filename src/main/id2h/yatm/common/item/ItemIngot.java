package id2h.yatm.common.item;

import java.util.EnumSet;

import appeng.core.features.AEFeature;
import appeng.items.AEBaseItem;

public class ItemIngot extends AEBaseItem
{
	public ItemIngot(int ingotType)
	{
		super();
		switch (ingotType)
		{
			case Ingots.UraniumIngot:
				setUnlocalizedName("yatm.ItemUraniumIngot");
				setTextureName("yatm:ItemMaterial.UraniumIngot");
				break;
			case Ingots.PurifiedUraniumIngot:
				setUnlocalizedName("yatm.ItemPurifiedUraniumIngot");
				setTextureName("yatm:ItemMaterial.PurifiedUraniumIngot");
				break;
			default:
				break;
		}
		setFeature(EnumSet.of(AEFeature.Core));
	}
}
