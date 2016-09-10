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

import net.minecraft.entity.player.EntityPlayer;

public class SecuritySystem
{
	private static final SecuritySystem inst = new SecuritySystem();
	private SecurityWorldData worldData;

	private boolean testSharedAccess(SecurityHeader header, EntityPlayer player)
	{
		if (worldData == null) return false;
		return worldData.hasSharedAccessTo(player.getPersistentID(), header.ownerUUID);
	}

	public boolean isOwner(SecurityHeader header, EntityPlayer player)
	{
		if (header.ownerUUID == null) return false;
		return header.ownerUUID.equals(player.getPersistentID());
	}

	/**
	 * Can the player access the content protected by this security header via shared access?
	 */
	public boolean hasSharedAccessTo(SecurityHeader header, EntityPlayer player)
	{
		switch (header.sharing)
		{
			case EVERYONE:
				return true;
			case SHARED:
				if (isOwner(header, player)) return true;
				return testSharedAccess(header, player);
			default:
				return false;
		}
	}

	/**
	 * Can the player access the content protected by this security header?
	 *
	 * @param header the header to check against
	 *
	 */
	public boolean canAccess(SecurityHeader header, EntityPlayer player)
	{
		switch (header.sharing)
		{
			case OWNER:
				return isOwner(header, player);
			default:
				return hasSharedAccessTo(header, player);
		}
	}

	public void onLoadData(SecurityWorldData data)
	{
		this.worldData = data;
		YATM.getLogger().debug("Security World Data has changed.");
	}

	public static final SecuritySystem instance()
	{
		return inst;
	}
}
