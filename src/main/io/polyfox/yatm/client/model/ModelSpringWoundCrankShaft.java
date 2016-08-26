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
package io.polyfox.yatm.client.model;

import io.polyfox.yatm.YATM;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModelSpringWoundCrankShaft extends ModelBase
{
	public static final float SCALE = 1f / 16f;
	ModelRenderer[] shafts;
	protected ResourceLocation textureResource = YATM.resources.create("textures/models/spring_wound_crank_shaft.png");

	public ModelSpringWoundCrankShaft()
	{
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.shafts = new ModelRenderer[2];
		shafts[0] = new ModelRenderer(this, 0, 0);
		shafts[0].addBox(-0.5F, 0, -1.5F, 1, 16, 3);
		shafts[0].setRotationPoint(0F, 0F, 0F);
		shafts[0].setTextureSize(16, 16);
		shafts[0].mirror = false;
		setRotation(shafts[0], 0F, 0F, 0F);
		shafts[1] = new ModelRenderer(this, 0, 0);
		shafts[1].addBox(-1.5F, 0, -0.5F, 3, 16, 1);
		shafts[1].setRotationPoint(0F, 0F, 0F);
		shafts[1].setTextureSize(16, 16);
		shafts[1].mirror = false;
		setRotation(shafts[1], 0F, 0F, 0F);
	}

	public ResourceLocation getTextureResource()
	{
		return textureResource;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float clicks, float scale)
	{
		GL11.glPushMatrix();
		{
			GL11.glRotatef(clicks, 0.0f, 1.0f, 0.0f);
			for (ModelRenderer shaft : shafts)
			{
				shaft.render(scale);
			}
		}
		GL11.glPopMatrix();
	}
}
