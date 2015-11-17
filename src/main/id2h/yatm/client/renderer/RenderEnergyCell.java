/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package id2h.yatm.client.renderer;

import growthcraft.core.util.RenderUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderEnergyCell implements ISimpleBlockRenderingHandler
{
	public static final int id = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (id == modelID)
		{
			final Tessellator tess = Tessellator.instance;
			RenderUtils.startInventoryRender();
			{
				tess.setColorOpaque_F(1.0f, 1.0f, 1.0f);

				final float coreScale = 12.0f / 16.0f;
				final float off = (1.0f - coreScale) / 2.0f;
				renderer.setRenderBounds(off, off, off, off + coreScale, off + coreScale, off + coreScale);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);

				final float coreW = 6.0f / 16.0f;
				final float coreH = 3.0f / 16.0f;
				final float coreL = 6.0f / 16.0f;

				final float bandW = 14.0f / 16.0f;
				final float bandH = 14.0f / 16.0f;
				final float bandL = 4.0f / 16.0f;

				final float x1 = (1.0f - coreW) / 2.0f;
				final float z1 = (1.0f - coreL) / 2.0f;

				final float bandX1 = (1.0f - bandW) / 2.0f;
				final float bandY1 = (1.0f - bandH) / 2.0f;
				final float bandZ1 = (1.0f - bandL) / 2.0f;

				renderer.setRenderBounds(bandX1, bandY1, bandZ1, bandX1 + bandW, bandY1 + bandH, bandZ1 + bandL);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);

				renderer.setRenderBounds(bandX1, bandZ1, bandY1, bandX1 + bandW, bandZ1 + bandL, bandY1 + bandH);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);

				renderer.setRenderBounds(bandZ1, bandY1, bandX1, bandZ1 + bandL, bandY1 + bandH, bandX1 + bandW);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);


				renderer.setRenderBounds(x1, 0.0f, z1, x1 + coreW, 1.0f, z1 + coreL);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);

				renderer.setRenderBounds(x1, z1, 0.0f, x1 + coreW, z1 + coreL, 1.0f);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);

				renderer.setRenderBounds(0.0f, z1, x1, 1.0f, z1 + coreL, x1 + coreW);
				RenderUtils.renderInventoryBlockFaces(block, metadata, renderer, tess);
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

			final float coreScale = 12.0f / 16.0f;
			final float off = (1.0f - coreScale) / 2.0f;
			renderer.setRenderBounds(off, off, off, off + coreScale, off + coreScale, off + coreScale);
			renderer.renderStandardBlock(block, x, y, z);

			final float coreW = 6.0f / 16.0f;
			final float coreH = 3.0f / 16.0f;
			final float coreL = 6.0f / 16.0f;

			final float bandW = 14.0f / 16.0f;
			final float bandH = 14.0f / 16.0f;
			final float bandL = 4.0f / 16.0f;

			final float x1 = (1.0f - coreW) / 2.0f;
			final float z1 = (1.0f - coreL) / 2.0f;

			final float bandX1 = (1.0f - bandW) / 2.0f;
			final float bandY1 = (1.0f - bandH) / 2.0f;
			final float bandZ1 = (1.0f - bandL) / 2.0f;

			renderer.setRenderBounds(bandX1, bandY1, bandZ1, bandX1 + bandW, bandY1 + bandH, bandZ1 + bandL);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(bandX1, bandZ1, bandY1, bandX1 + bandW, bandZ1 + bandL, bandY1 + bandH);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(bandZ1, bandY1, bandX1, bandZ1 + bandL, bandY1 + bandH, bandX1 + bandW);
			renderer.renderStandardBlock(block, x, y, z);


			renderer.setRenderBounds(x1, 0.0f, z1, x1 + coreW, 1.0f, z1 + coreL);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(x1, z1, 0.0f, x1 + coreW, z1 + coreL, 1.0f);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.0f, z1, x1, 1.0f, z1 + coreL, x1 + coreW);
			renderer.renderStandardBlock(block, x, y, z);
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
