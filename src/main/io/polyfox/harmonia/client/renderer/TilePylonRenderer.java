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
package io.polyfox.harmonia.client.renderer;

import io.polyfox.harmonia.client.model.ModelPylonCube;
import io.polyfox.harmonia.common.tile.TilePylon;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class TilePylonRenderer extends TileEntitySpecialRenderer
{
	protected final ModelPylonCube pylonCubeModel = new ModelPylonCube();

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
	{
		if (te instanceof TilePylon)
		{
			final TilePylon pylon = (TilePylon)te;
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
				//GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				this.bindTexture(pylonCubeModel.getTextureResource());
				pylonCubeModel.render(null, 0f, 0f, 0f, f, 0f, ModelPylonCube.SCALE);
			}
			GL11.glPopMatrix();
		}
	}
}
