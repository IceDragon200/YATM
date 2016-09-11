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
package io.polyfox.harmonia.common.block

import growthcraft.api.core.util.RenderType
import io.polyfox.harmonia.common.tile.TilePylon
import io.polyfox.yatm.common.block.YATMBlockBaseTile

import net.minecraft.block.Block
import net.minecraft.block.material.Material

class BlockPylon extends YATMBlockBaseTile(Material.iron, classOf[TilePylon])
{
	setStepSound(Block.soundTypeStone)
	setHardness(4.0F)
	setBlockName("yatm.pylon");

	override def getRenderType(): Int =
	{
		return RenderType.NONE;
	}

	override def renderAsNormalBlock(): Boolean = false
	override def isOpaqueCube(): Boolean = false
}
