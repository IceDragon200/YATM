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

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class SecurityWorldData extends WorldSavedData
{
	public static final String WORLD_DATA_NAME = "yatm:security";
	private SecuritySharedTable securityTable = new SecuritySharedTable();

	public SecurityWorldData()
	{
		super(WORLD_DATA_NAME);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("security_table"))
		{
			final NBTTagCompound sectab = nbt.getCompoundTag("security_table");
			securityTable.readFromNBT(sectab);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		final NBTTagCompound sectab = new NBTTagCompound();
		securityTable.writeToNBT(sectab);
		nbt.setTag("security_table", sectab);
	}

	/**
	 * @param who who is trying to access the target
	 * @param what is being accessed
	 */
	public boolean hasSharedAccessTo(UUID who, UUID target)
	{
		return securityTable.hasSharedAccessTo(who, target);
	}
}
