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
package io.polyfox.yatm.common.tileentity.traits

import growthcraft.core.common.tileentity.event.TileEventHandler
import io.polyfox.yatm.security.ISecuredEntity
import io.polyfox.yatm.security.SecurityHeader

import net.minecraft.nbt.NBTTagCompound

trait TraitSecured extends ISecuredEntity
{
	def getSecurityHeader(): SecurityHeader

	def readSecurityFromNBT(nbt: NBTTagCompound)
	{
		val header = getSecurityHeader()
		if (nbt.hasKey("security_header"))
		{
			val sectag: NBTTagCompound = nbt.getCompoundTag("security_header")
			header.readFromNBT(sectag)
		}
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	def readFromNBT_Secured(nbt: NBTTagCompound)
	{
		readSecurityFromNBT(nbt);
	}

	def writeSecurityToNBT(nbt: NBTTagCompound)
	{
		val header = getSecurityHeader()
		val sectag = new NBTTagCompound()
		header.writeToNBT(sectag)
		nbt.setTag("security_header", sectag)
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	def writeToNBT_Secured(nbt: NBTTagCompound)
	{
		writeSecurityToNBT(nbt);
	}
}
