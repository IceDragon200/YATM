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

import org.lwjgl.opengl.GL11;

import growthcraft.core.util.RenderUtils;
import io.polyfox.yatm.common.block.BlockCrystalVat;
import io.polyfox.yatm.common.tileentity.TileEntityCrystalVat;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;

public class RenderCrystalVat implements ISimpleBlockRenderingHandler
{
	public static int id = RenderingRegistry.getNextAvailableRenderId();

	// original 0.71875F
	private static final float FLUID_HEIGHT = 0.6875F;

	@Override
	public int getRenderId()
	{
		return id;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == getRenderId())
		{
			final BlockCrystalVat vat = (BlockCrystalVat)block;
			final Tessellator tes = Tessellator.instance;
			final IIcon[] icon  = {vat.getIconByIndex(0), vat.getIconByIndex(3), vat.getIconByIndex(2), vat.getIconByIndex(2), vat.getIconByIndex(2), vat.getIconByIndex(2)};
			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			// Outer Kettle
			RenderUtils.renderInventoryBlockOverride(block, renderer, icon, tes);

			final float f = 0.125F;

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			// Inner Kettle
			RenderUtils.drawFace(RenderUtils.Face.ZNEG, block, renderer, tes, vat.getIconByIndex(2), 0.0D, 0.0D, (double)(0.0F + 1.0F - f));
			RenderUtils.drawFace(RenderUtils.Face.ZPOS, block, renderer, tes, vat.getIconByIndex(2), 0.0D, 0.0D, (double)(0.0F - 1.0F + f));
			RenderUtils.drawFace(RenderUtils.Face.XNEG, block, renderer, tes, vat.getIconByIndex(2), (double)(0.0F + 1.0F - f), 0.0D, 0.0D);
			RenderUtils.drawFace(RenderUtils.Face.XPOS, block, renderer, tes, vat.getIconByIndex(2), (double)(0.0F - 1.0F + f), 0.0D, 0.0D);
			RenderUtils.drawFace(RenderUtils.Face.YPOS, block, renderer, tes, vat.getIconByIndex(1), 0.0D, (double)(0.0F - 1.0F + 0.25F), 0.0D);
			RenderUtils.drawFace(RenderUtils.Face.YNEG, block, renderer, tes, vat.getIconByIndex(1), 0.0D, (double)(0.0F + 1.0F - 0.75F), 0.0D);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == getRenderId())
		{
			final BlockCrystalVat vat = (BlockCrystalVat)block;
			final double d = 0.0625D;
			float f = 1.0F;
			renderer.renderStandardBlock(block, x, y, z);
			final Tessellator tes = Tessellator.instance;
			tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
			int color = block.colorMultiplier(world, x, y, z);
			float r = (float)(color >> 16 & 255) / 255.0F;
			float g = (float)(color >> 8 & 255) / 255.0F;
			float b = (float)(color & 255) / 255.0F;
			float f4;

			if (EntityRenderer.anaglyphEnable)
			{
				final float f5 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
				f4 = (r * 30.0F + g * 70.0F) / 100.0F;
				final float f6 = (r * 30.0F + b * 70.0F) / 100.0F;
				r = f5;
				g = f4;
				b = f6;
			}

			tes.setColorOpaque_F(f * r, f * g, f * b);
			f4 = 0.125F;
			renderer.renderFaceXPos(block, (double)(x - 1.0F + f4), (double)y, (double)z, vat.getIconByIndex(4));
			renderer.renderFaceXNeg(block, (double)(x + 1.0F - f4), (double)y, (double)z, vat.getIconByIndex(4));
			renderer.renderFaceZPos(block, (double)x, (double)y, (double)(z - 1.0F + f4), vat.getIconByIndex(4));
			renderer.renderFaceZNeg(block, (double)x, (double)y, (double)(z + 1.0F - f4), vat.getIconByIndex(4));
			renderer.renderFaceYPos(block, (double)x, (double)(y - 1.0F + 0.25F), (double)z, vat.getIconByIndex(3));
			renderer.renderFaceYNeg(block, (double)x, (double)(y + 1.0F - 0.75F), (double)z, vat.getIconByIndex(3));

			// Render Liquid
			final TileEntityCrystalVat te = (TileEntityCrystalVat)world.getTileEntity(x, y, z);
			if (te != null)
			{
				for (int i = 0; i < te.getTankCount(); ++i)
				{
					if (te.isFluidTankFilled(i))
					{
						final Fluid fluid = te.getFluid(i);
						color = fluid.getColor();
						r = (float)(color >> 16 & 255) / 255.0F;
						g = (float)(color >> 8 & 255) / 255.0F;
						b = (float)(color & 255) / 255.0F;
						f = 1.0F;
						tes.setColorOpaque_F(f * r, f * g, f * b);
						f = te.getFluidAmount(i) * FLUID_HEIGHT / te.getFluidTank(i).getCapacity();
						renderer.setRenderBounds(2 * d, 0.0D, 2 * d, 14 * d, (double)(0.25F + f), 14 * d);
						final IIcon icon = fluid.getIcon();
						if (icon != null)
						{
							renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
						}
					}
				}
			}

			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		}
		return true;
	}
}
