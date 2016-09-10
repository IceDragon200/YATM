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
package io.polyfox.yatm.security;

import io.polyfox.yatm.YATM;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class SecurityWorldEventHandler
{
	private SecuritySystem securitySystem;

	public SecurityWorldEventHandler(SecuritySystem sys)
	{
		this.securitySystem = sys;
	}

	@SubscribeEvent
	public void save(WorldEvent.Save save)
	{
		// unusued
	}

	@SubscribeEvent
	@SuppressWarnings({"unchecked"})
	public void load(WorldEvent.Load event)
	{
		YATM.getLogger().debug("Loading `%s` world data", SecurityWorldData.WORLD_DATA_NAME);
		SecurityWorldData wd = (SecurityWorldData)event.world.perWorldStorage.loadData(SecurityWorldData.class, SecurityWorldData.WORLD_DATA_NAME);
		if (wd == null)
		{
			YATM.getLogger().debug("Creating `%s` world data", SecurityWorldData.WORLD_DATA_NAME);
			wd = new SecurityWorldData();
			event.world.perWorldStorage.setData(SecurityWorldData.WORLD_DATA_NAME, wd);
		}
		securitySystem.onLoadData(wd);
	}
}
