/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.polyfox.yatm.common.block;

import io.polyfox.yatm.client.util.StateIconLoader.IconVariant;
import io.polyfox.yatm.common.tileentity.TileEntityFluxSwitch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFluxSwitch extends YATMBlockBaseTile
{
	public BlockFluxSwitch()
	{
		super(Material.rock, TileEntityFluxSwitch.class);
		setStepSound(Block.soundTypeMetal);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.flux_switch");
		setBlockTextureName("yatm:flux_switch");
	}

	@SideOnly(Side.CLIENT)
	public int getIconOffset(int meta)
	{
		final boolean powered = (meta & 8) == 8;
		final boolean online = isOnline(meta);
		if (powered && online)
		{
			return 6 * 3;
		}
		else if (powered && !online)
		{
			return 6 * 2;
		}
		else if (online)
		{
			return 6;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	protected IconVariant[] getIconVariants()
	{
		return new IconVariant[] {
			new IconVariant("uoff", null),
			new IconVariant("uon", "uoff"),
			new IconVariant("poff", null),
			new IconVariant("pon", "poff")
		};
	}
}
