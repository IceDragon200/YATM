package id2h.yatm.creativetab;

import id2h.yatm.init.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabsYATM extends CreativeTabs
{
	private static final CreativeTabs INSTANCE = new CreativeTabsYATM();

	public CreativeTabsYATM()
	{
		super("yatm");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(Blocks.chassis);
	}

	public static CreativeTabs instance()
	{
		return INSTANCE;
	}
}
