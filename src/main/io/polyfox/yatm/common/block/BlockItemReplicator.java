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
import io.polyfox.yatm.common.tileentity.TileEntityItemReplicator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockItemReplicator extends YATMBlockBaseTile
{
	public BlockItemReplicator()
	{
		super(Material.rock, TileEntityItemReplicator.class);
		setStepSound(Block.soundTypeMetal);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.item_replicator");
		setBlockTextureName("yatm:item_replicator");
	}

	@SideOnly(Side.CLIENT)
	public int getIconOffset(int meta)
	{
		final boolean isIdle = (meta & 8) == 8;
		if (isOnline(meta))
		{
			if (isIdle) return 12;
			return 6;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	protected IconVariant[] getIconVariants()
	{
		return new IconVariant[] {
			new IconVariant("off", null),
			new IconVariant("on", "off"),
			new IconVariant("idle", "on")
		};
	}
}
