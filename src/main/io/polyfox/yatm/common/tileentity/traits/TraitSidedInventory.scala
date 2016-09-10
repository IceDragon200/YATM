/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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

import io.polyfox.yatm.common.inventory.IYATMInventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack

trait TraitSidedInventory extends ISidedInventory
{
	def getInventory(): IYATMInventory
	def getSizeInventory(): Int = getInventory().getSizeInventory
	def getStackInSlot(index: Int): ItemStack = getInventory().getStackInSlot(index)
	def getInventoryStackLimit(): Int = getInventory().getInventoryStackLimit
	def decrStackSize(index: Int, amount: Int): ItemStack = getInventory().decrStackSize(index, amount)
	def getStackInSlotOnClosing(index: Int): ItemStack = getInventory().getStackInSlotOnClosing(index)

	def setInventorySlotContents(index: Int, stack: ItemStack)
	{
		getInventory().setInventorySlotContents(index, stack)
	}

	def hasCustomInventoryName(): Boolean = getInventory().hasCustomInventoryName()
	def getInventoryName(): String = getInventory().getInventoryName
	def isUseableByPlayer(player: EntityPlayer): Boolean = getInventory().isUseableByPlayer(player)

	def openInventory()
	{
		getInventory().openInventory()
	}

	def closeInventory()
	{
		getInventory().closeInventory()
	}

	def isItemValidForSlot(index: Int, stack: ItemStack): Boolean =
	{
		getInventory().isItemValidForSlot(index, stack)
	}

	def getAccessibleSlotsFromSide(side: Int): Array[Int] = Array(0)
	def canInsertItem(index: Int, stack: ItemStack, side: Int): Boolean = false
	def canExtractItem(index: Int, stack: ItemStack, side: Int): Boolean = false
}
