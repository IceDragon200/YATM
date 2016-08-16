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
package io.polyfox.yatm.client.renderer;

import growthcraft.core.util.RenderUtils;
import growthcraft.api.core.util.BBox;

import io.polyfox.yatm.client.boxmodels.BoxModels;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderCagedMachine implements ISimpleBlockRenderingHandler
{
	public static final int id = RenderingRegistry.getNextAvailableRenderId();
	private static final BBox[] boxes = BoxModels.cagedMachine2;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (id == modelID)
		{
			final Tessellator tess = Tessellator.instance;
			RenderUtils.startInventoryRender();
			{
				tess.setColorOpaque_F(1.0f, 1.0f, 1.0f);

				for (BBox box : boxes)
				{
					renderer.setRenderBounds(box.x0(), box.y0(), box.z0(), box.x1(), box.y1(), box.z1());
					RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);
				}
			}
			RenderUtils.endInventoryRender();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		if (id == modelID)
		{
			final Tessellator tess = Tessellator.instance;
			tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
			tess.setColorOpaque_F(1.0f, 1.0f, 1.0f);

			for (BBox box : boxes)
			{
				renderer.setRenderBounds(box.x0(), box.y0(), box.z0(), box.x1(), box.y1(), box.z1());
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return id;
	}
}
