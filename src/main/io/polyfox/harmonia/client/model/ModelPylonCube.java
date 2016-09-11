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
package io.polyfox.harmonia.client.model;

import io.polyfox.yatm.YATM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModelPylonCube extends ModelBase
{
	public static final float SCALE = 1f / 16f;
	protected ModelRenderer cube;
	protected ResourceLocation textureResource = YATM.resources.create("textures/models/pylon.png");

	public ModelPylonCube()
	{
		this.textureWidth = 32;
		this.textureHeight = 16;
		this.cube = new ModelRenderer(this, 0, 0);
		cube.addBox(-4f, -4f, -4f, 8, 8, 8);
		cube.setRotationPoint(0F, 0F, 0F);
		cube.setTextureSize(32, 16);
		cube.mirror = false;
		setRotation(cube, 45F, 0F, 45F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public ResourceLocation getTextureResource()
	{
		return textureResource;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float clicks, float scale)
	{
		cube.render(scale);
	}
}
