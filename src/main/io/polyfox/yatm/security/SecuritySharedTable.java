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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import growthcraft.api.core.nbt.NBTType;
import growthcraft.api.core.nbt.NBTTagStringList;
import growthcraft.api.core.nbt.INBTSerializable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SecuritySharedTable implements INBTSerializable
{
	public static class SecuritySharedOptions implements INBTSerializable
	{
		private final Set<UUID> shared = new HashSet<UUID>();

		public boolean hasSharedAccessFor(UUID user)
		{
			return shared.contains(user);
		}

		public void writeToNBT(NBTTagCompound nbt)
		{
			final NBTTagStringList stringList = new NBTTagStringList(shared);
			nbt.setTag("shared", stringList.getTag());
		}

		public void readFromNBT(NBTTagCompound nbt)
		{
			shared.clear();
			if (nbt.hasKey("shared"))
			{
				final NBTTagList tagList = nbt.getTagList("shared", NBTType.STRING.id);
				final NBTTagStringList stringList = new NBTTagStringList(tagList);
				for (int i = 0; i < stringList.size(); ++i)
				{
					final String uuidString = stringList.get(i);
					shared.add(UUID.fromString(uuidString));
				}
			}
		}

		public static SecuritySharedOptions loadFromNBT(NBTTagCompound nbt)
		{
			final SecuritySharedOptions result = new SecuritySharedOptions();
			result.readFromNBT(nbt);
			return result;
		}
	}

	private final Map<UUID, SecuritySharedOptions> securityMap = new HashMap<UUID, SecuritySharedOptions>();

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		for (UUID key : securityMap.keySet())
		{
			final SecuritySharedOptions secopts = securityMap.get(key);
			final NBTTagCompound secopttag = new NBTTagCompound();
			secopts.writeToNBT(secopttag);
			nbt.setTag(key.toString(), secopttag);
		}
	}

	@Override
	@SuppressWarnings({"unchecked"})
	public void readFromNBT(NBTTagCompound nbt)
	{
		final Set<String> keys = (Set<String>)nbt.func_150296_c();
		securityMap.clear();
		for (String key : keys)
		{
			securityMap.put(UUID.fromString(key), SecuritySharedOptions.loadFromNBT(nbt.getCompoundTag(key)));
		}
	}

	/**
	 * @param who who is trying to access the target
	 * @param what is being accessed
	 */
	public boolean hasSharedAccessTo(UUID who, UUID target)
	{
		final SecuritySharedOptions opts = securityMap.get(target);
		if (opts == null) return false;
		return opts.hasSharedAccessFor(who);
	}
}
