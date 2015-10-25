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
package id2h.yatm.client.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class RenderUtils
{
	public static enum Face
	{
		XPOS,
		XNEG,
		YPOS,
		YNEG,
		ZPOS,
		ZNEG;

		public static final Face[] FACES = {YNEG, YPOS, ZNEG, ZPOS, XNEG, XPOS};
	}

	private RenderUtils() {}

	public static void resetColor()
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static void resetScale()
	{
		GL11.glScalef(1.0f, 1.0f, 1.0f);
	}

	public static void renderCubeFace(Face face, Block block, RenderBlocks renderer, Tessellator tes, IIcon icon, double i, double j, double k)
	{
		final float f = 0.0F;
		switch (face)
		{
			case XPOS:
			{
				tes.addTranslation(-f, 0.0F, 0.0F);
				tes.setNormal(1.0F, 0.0F, 0.0F);
				tes.addTranslation(f, 0.0F, 0.0F);
				renderer.renderFaceXPos(block, i, j, k, icon);
				break;
			}
			case XNEG:
			{
				tes.addTranslation(f, 0.0F, 0.0F);
				tes.setNormal(-1.0F, 0.0F, 0.0F);
				tes.addTranslation(-f, 0.0F, 0.0F);
				renderer.renderFaceXNeg(block, i, j, k, icon);
				break;
			}
			case YPOS:
			{
				tes.addTranslation(0.0F, -f, 0.0F);
				tes.setNormal(0.0F, 1.0F, 0.0F);
				tes.addTranslation(0.0F, f, 0.0F);
				renderer.renderFaceYPos(block, i, j, k, icon);
				break;
			}
			case YNEG:
			{
				tes.addTranslation(0.0F, f, 0.0F);
				tes.setNormal(0.0F, -1.0F, 0.0F);
				tes.addTranslation(0.0F, -f, 0.0F);
				renderer.renderFaceYNeg(block, i, j, k, icon);
				break;
			}
			case ZPOS:
			{
				tes.addTranslation(0.0F, 0.0F, -f);
				tes.setNormal(0.0F, 0.0F, 1.0F);
				tes.addTranslation(0.0F, 0.0F, f);
				renderer.renderFaceZPos(block, i, j, k, icon);
				break;
			}
			case ZNEG:
			{
				tes.addTranslation(0.0F, 0.0F, f);
				tes.setNormal(0.0F, 0.0F, -1.0F);
				tes.addTranslation(0.0F, 0.0F, -f);
				renderer.renderFaceZNeg(block, i, j, k, icon);
				break;
			}
			default:
			{
				throw new IllegalArgumentException("Invalid face value " + face + ".");
			}
		}
	}

	public static void renderFace(Face face, Block block, RenderBlocks renderer, Tessellator tes, IIcon icon, double i, double j, double k)
	{
		tes.startDrawingQuads();
		renderCubeFace(face, block, renderer, tes, icon, i, j, k);
		tes.draw();
	}

	public static void beginInventoryRender()
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}

	public static void endInventoryRender()
	{
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	public static void renderInventoryBlockOverride(Block block, RenderBlocks renderer, IIcon[] icon, Tessellator tessellator)
	{
		beginInventoryRender();
		tessellator.startDrawingQuads();
		renderCubeFace(Face.YNEG, block, renderer, tessellator, icon[0], 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.YPOS, block, renderer, tessellator, icon[1], 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.ZNEG, block, renderer, tessellator, icon[2], 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.ZPOS, block, renderer, tessellator, icon[3], 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.XNEG, block, renderer, tessellator, icon[4], 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.XPOS, block, renderer, tessellator, icon[5], 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		endInventoryRender();
	}

	public static void renderInventoryBlockFaces(Block block, int meta, RenderBlocks renderer, Tessellator tessellator)
	{
		tessellator.startDrawingQuads();
		renderCubeFace(Face.YNEG, block, renderer, tessellator, block.getIcon(0, meta), 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.YPOS, block, renderer, tessellator, block.getIcon(1, meta), 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.ZNEG, block, renderer, tessellator, block.getIcon(2, meta), 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.ZPOS, block, renderer, tessellator, block.getIcon(3, meta), 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.XNEG, block, renderer, tessellator, block.getIcon(4, meta), 0.0D, 0.0D, 0.0D);
		renderCubeFace(Face.XPOS, block, renderer, tessellator, block.getIcon(5, meta), 0.0D, 0.0D, 0.0D);
		tessellator.draw();
	}

	public static void renderInventoryBlock(Block block, int meta, RenderBlocks renderer, Tessellator tessellator)
	{
		beginInventoryRender();
		renderInventoryBlockFaces(block, meta, renderer, tessellator);
		endInventoryRender();
	}
}
