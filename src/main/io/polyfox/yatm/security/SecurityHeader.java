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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import growthcraft.api.core.nbt.INBTSerializable;
import io.polyfox.yatm.util.ServerUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SecurityHeader implements INBTSerializable
{
	public static enum SharingType
	{
		/**
		 * Anyone can access and use the block with this header
		 */
		EVERYONE("everyone"),
		/**
		 * Special sharing type, the target object must store the group ID
		 * and cross-check against the group table for the player in question
		 */
		SHARED("shared"),
		/**
		 * Only the owner may use the target object
		 */
		OWNER("owner");

		public static final Map<String, SharingType> TYPE_MAP = new HashMap<String, SharingType>();

		static
		{
			for (SharingType t : values())
			{
				TYPE_MAP.put(t.getBasename(), t);
			}
		}

		private final String basename;

		private SharingType(String p_name)
		{
			this.basename = p_name;
		}

		public String getBasename()
		{
			return basename;
		}

		public static SharingType byBasename(String name)
		{
			if (TYPE_MAP.containsKey(name))
			{
				return TYPE_MAP.get(name);
			}
			return EVERYONE;
		}
	}

	public UUID ownerUUID;
	public SharingType sharing = SharingType.EVERYONE;

	/**
	 * @return true, if there was an owner, false otherwise
	 */
	public boolean revokeOwnership()
	{
		if (ownerUUID != null)
		{
			this.ownerUUID = null;
			return true;
		}
		return false;
	}

	/**
	 * Takes ownership of the item, as long as the player isn't null
	 *
	 * @param player player attempting the take ownership
	 * @return true, the header was claimed by the given player, false it was already claimed
	 */
	public boolean takeOwnership(EntityPlayer player)
	{
		if (player != null)
		{
			this.ownerUUID = player.getPersistentID();
			return true;
		}
		return false;
	}

	/**
	 * Attempts to claim the header for the given player
	 *
	 * @param player player attempting the take ownership
	 * @return true, the header was claimed by the given player, false it was already claimed
	 */
	public boolean tryClaimOwnership(EntityPlayer player)
	{
		if (ownerUUID == null)
		{
			return takeOwnership(player);
		}
		return false;
	}

	/**
	 * The owner player, may be null even if the UUID is set
	 *
	 * @return player
	 */
	public EntityPlayer getOwnerPlayer()
	{
		if (ownerUUID == null) return null;
		return ServerUtils.findPlayerByUUID(ownerUUID);
	}

	/**
	 * Can the specified player access the item protected by this header?
	 *
	 * @return true, yes they can access it, false otherwise
	 */
	public boolean canAccess(EntityPlayer player)
	{
		return SecuritySystem.instance().canAccess(this, player);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		if (ownerUUID != null)
		{
			nbt.setString("owner_uuid", ownerUUID.toString());
		}
		nbt.setString("sharing", sharing.getBasename());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.ownerUUID = null;
		if (nbt.hasKey("owner_uuid"))
		{
			this.ownerUUID = UUID.fromString(nbt.getString("owner_uuid"));
		}
		this.sharing = SharingType.byBasename(nbt.getString("sharing"));
	}
}
