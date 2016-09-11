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
package io.polyfox.harmonia

import growthcraft.core.common.GrcModuleBase
import growthcraft.api.core.module.ModuleContainer
import io.polyfox.harmonia.common.CommonProxy
import io.polyfox.harmonia.init.HarmoniaBlocks
import io.polyfox.harmonia.init.HarmoniaItems

/**
 * Harmonia consists of the `Magic` side to YATM, dealing with spirits, and the like
 */
class Harmonia extends GrcModuleBase
{
	val blocks: HarmoniaBlocks = new HarmoniaBlocks()
	val items: HarmoniaItems = new HarmoniaItems()
	private val modules: ModuleContainer = new ModuleContainer()

	override def preInit()
	{
		modules.add(blocks)
		modules.add(items)
		modules.add(CommonProxy.instance)
		modules.preInit()
	}

	override def register()
	{
		modules.register()
	}

	override def init()
	{
		modules.init()
	}

	override def postInit()
	{
		modules.postInit()
	}
}
