package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityCreativeEnergyCell;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockEnergyCellCreative extends BlockEnergyCell
{
	public BlockEnergyCellCreative(String basename, Class<? extends TileEntityCreativeEnergyCell> tileentity)
	{
		super(basename, tileentity);
		setBlockName("yatm.BlockEnergyCell" + basename + "Creative");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new FlippableIcon[1];
		icons[0] = optionalIcon(reg, getTextureName() + "/Creative", null);
	}
}
