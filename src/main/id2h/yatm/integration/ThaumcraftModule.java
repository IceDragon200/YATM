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
package id2h.yatm.integration;

import growthcraft.api.core.item.ItemKey;
import growthcraft.core.integration.ThaumcraftModuleBase;
import id2h.yatm.YATM;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.ThaumcraftApi;

import cpw.mods.fml.common.Optional;

public class ThaumcraftModule extends ThaumcraftModuleBase
{
	public ThaumcraftModule()
	{
		super(YATM.MOD_ID);
	}

	@Override
	@Optional.Method(modid="Thaumcraft")
	protected void integrate()
	{
		ThaumcraftApi.registerObjectTag(YATM.blocks.autoCrafter.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.METAL, 8).add(Aspect.CRAFT, 9).add(Aspect.MECHANISM, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.autoGrinder.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.METAL, 8).add(Aspect.CRAFT, 9).add(Aspect.MECHANISM, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.chassis.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.METAL, 4).add(Aspect.MECHANISM, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.coalGenerator.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ENERGY, 2).add(Aspect.METAL, 4).add(Aspect.MECHANISM, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.compactor.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.EARTH, 2).add(Aspect.METAL, 4).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.crusher.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.EARTH, 2).add(Aspect.METAL, 4).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.electrolyser.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 8).add(Aspect.METAL, 4).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellBasic.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellBasicCreative.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellNormal.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellNormalCreative.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 8));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellDense.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 16));
		ThaumcraftApi.registerObjectTag(YATM.blocks.energyCellDenseCreative.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 16));
		ThaumcraftApi.registerObjectTag(YATM.blocks.fluxFurnace.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.FIRE, 8).add(Aspect.METAL, 4).add(Aspect.CRAFT, 4).add(Aspect.MECHANISM, 2));
		ThaumcraftApi.registerObjectTag(YATM.blocks.heater.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.FIRE, 8).add(Aspect.METAL, 4).add(Aspect.MECHANISM, 2));
		ThaumcraftApi.registerObjectTag(YATM.blocks.lamp.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.LIGHT, 4).add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.miniBlastFurnace.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.FIRE, 12).add(Aspect.METAL, 4).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 2));
		ThaumcraftApi.registerObjectTag(YATM.blocks.mixer.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.MOTION, 4).add(Aspect.METAL, 8).add(Aspect.CRAFT, 4).add(Aspect.MECHANISM, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.roller.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.MOTION, 4).add(Aspect.METAL, 8).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 4));
		ThaumcraftApi.registerObjectTag(YATM.blocks.solarPanel.asStack(1, ItemKey.WILDCARD_VALUE), new AspectList().add(Aspect.ENERGY, 4).add(Aspect.LIGHT, 4).add(Aspect.MECHANISM, 4));
	}
}
