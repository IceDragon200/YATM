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
package io.polyfox.yatm.common.tileentity

import java.io.IOException
import scala.util.control.Breaks._

import io.netty.buffer.ByteBuf

import growthcraft.api.core.util.BlockFlags
import growthcraft.core.common.inventory.IInventoryWatcher
import growthcraft.core.common.tileentity.event.TileEventHandler
import growthcraft.core.common.tileentity.feature.IGuiNetworkSync
import growthcraft.core.common.tileentity.feature.IInteractionObject
import growthcraft.core.util.ItemUtils
import io.polyfox.yatm.api.core.util.BytePack
import io.polyfox.yatm.api.power.IPowerProducer
import io.polyfox.yatm.common.inventory.IYATMInventory
import io.polyfox.yatm.common.tileentity.machine.IMachineLogic
import io.polyfox.yatm.common.tileentity.machine.IProgressiveMachine
import io.polyfox.yatm.common.tileentity.machine.MachineUpdateState
import io.polyfox.yatm.common.tileentity.traits.TraitSecured
import io.polyfox.yatm.common.tileentity.traits.TraitSidedInventory
import io.polyfox.yatm.security.SecurityHeader

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.ICrafting
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection

abstract class TilePoweredMachine extends TilePowered()
	with ISidedInventory
	with IGuiNetworkSync
	with IInventoryWatcher
	with IInteractionObject
	with TraitSecured
	with TraitSidedInventory
{
	protected var inventory: IYATMInventory = createInventory()
	protected var machine: IMachineLogic = createMachine()
	protected var lastWorkingState: Boolean = true
	protected var dirtyNext: Boolean = true
	protected var machineState: MachineUpdateState = new MachineUpdateState()
	protected var securityHeader: SecurityHeader = new SecurityHeader()

	this.machine.setTileEntity(this)

	override def getSecurityHeader(): SecurityHeader = securityHeader

	def getOwnerPlayer(): EntityPlayer = getSecurityHeader().getOwnerPlayer()
	def getWorkingState(): Boolean = lastWorkingState

	private def readInventoryFromNBT(nbt: NBTTagCompound)
	{
		if (nbt.hasKey("inventory")) inventory.readFromNBT(nbt, "inventory")
	}

	private def readMachineFromNBT(nbt: NBTTagCompound)
	{
		if (nbt.hasKey("machine")) machine.readFromNBT(nbt, "machine")
	}

	override def readFromNBTForItem(nbt: NBTTagCompound)
	{
		super.readFromNBTForItem(nbt)
		readInventoryFromNBT(nbt)
		readMachineFromNBT(nbt)
	}

	@TileEventHandler(event= TileEventHandler.EventType.NBT_READ)
	def readFromNBT_Machine(nbt: NBTTagCompound)
	{
		readInventoryFromNBT(nbt)
		readMachineFromNBT(nbt)
	}

	private def writeInventoryToNBT(nbt: NBTTagCompound)
	{
		inventory.writeToNBT(nbt, "inventory")
	}

	private def writeMachineToNBT(nbt: NBTTagCompound)
	{
		machine.writeToNBT(nbt, "machine")
	}

	override def writeToNBTForItem(nbt: NBTTagCompound)
	{
		super.writeToNBTForItem(nbt)
		writeInventoryToNBT(nbt)
		writeMachineToNBT(nbt)
	}

	@TileEventHandler(event= TileEventHandler.EventType.NBT_WRITE)
	def writeToNBT_Machine(nbt: NBTTagCompound)
	{
		writeInventoryToNBT(nbt)
		writeMachineToNBT(nbt)
	}

	@TileEventHandler(event= TileEventHandler.EventType.NETWORK_READ)
	def readFromStream_Machine(stream: ByteBuf): Boolean = machine.readFromStream(stream)

	@TileEventHandler(event= TileEventHandler.EventType.NETWORK_WRITE)
	def writeToStream_Machine(stream: ByteBuf): Boolean = machine.writeToStream(stream)

	override def sendGUINetworkData(container: Container, icrafting: ICrafting)
	{
		if (machine.isInstanceOf[IProgressiveMachine])
		{
			val progMachine = machine.asInstanceOf[IProgressiveMachine]
			icrafting.sendProgressBarUpdate(container, 100, progMachine.getProgress.toInt)
			icrafting.sendProgressBarUpdate(container, 101, progMachine.getProgressMax.toInt)
		}
		val buffer = Array.ofDim[Short](4)
		BytePack.packI64ToI16(getPowerStorage().getCapacity(), buffer, 0)
		for (i <- 0 until buffer.length)
		{
			icrafting.sendProgressBarUpdate(container, 200 + i, buffer(i))
		}
		BytePack.packI64ToI16(getPowerStorage().getAmount(), buffer, 0)
		for (i <- 0 until buffer.length)
		{
			icrafting.sendProgressBarUpdate(container, 204 + i, buffer(i))
		}
	}

	override def receiveGUINetworkData(id: Int, value: Int)
	{
		if (machine.isInstanceOf[IProgressiveMachine])
		{
			val progMachine = machine.asInstanceOf[IProgressiveMachine]
			id match
			{
				case 100 => progMachine.setProgress(value.toFloat)
				case 101 => progMachine.setProgressMax(value.toFloat)
				case _ =>
			}
		}
		id match
		{
			case 200 | 201 | 202 | 203 =>
			{
				val segment = id - 200
				getPowerStorage().setCapacity(BytePack.replaceI16SegmentInI64(segment, value.toShort, getPowerStorage().getCapacity))
			}
			case 204 | 205 | 206 | 207 =>
			{
				val segment = id - 204
				getPowerStorage().setAmountUnsafe(BytePack.replaceI16SegmentInI64(segment, value.toShort, getPowerStorage().getAmount))
			}
			case _ =>
		}
	}

	def createInventory(): IYATMInventory
	def getInventory(): IYATMInventory = inventory
	def createMachine(): IMachineLogic
	def getMachine(): IMachineLogic = machine

	override def isUseableByPlayer(player: EntityPlayer): Boolean =
	{
		securityHeader.canAccess(player)
	}

	def getMachineProgressRate(): Float =
	{
		if (machine.isInstanceOf[IProgressiveMachine])
		{
			return machine.asInstanceOf[IProgressiveMachine].getProgressRate
		}
		0.0f
	}

	override def markDirty()
	{
		super.markDirty()
		machine.awake()
	}

	// This is completely different from markDirty, and is more of a delayed mark
	// "Mark me as dirty next update"
	def markDirtyNext()
	{
		this.dirtyNext |= true
	}

	def updateMachine()
	{
		machineState.clear()
		machineState.inventory = getInventory()
		machineState.powerStorage = getPowerStorage()
		machine.updateMachine(machineState)
		if (machineState.powerConsumed != 0)
		{
			getPowerStorage().consume(machineState.powerConsumed, false)
			onInternalPowerChanged()
		}
		if (lastWorkingState != machineState.didWork)
		{
			lastWorkingState = machineState.didWork
			var meta = getBlockMetadata & 3
			if (lastWorkingState) meta |= 4
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, BlockFlags.SYNC)
			markDirty()
		}
	}

	protected def demandPowerFromNeighbours()
	{
		val demand = getPowerThrottle().getMaxConsume()
		breakable
		{
			for (i <- 0 until tileCache.length)
			{
				if (demand <= 0) break
				val te = tileCache(i)
				if (te != null)
				{
					if (te.isInstanceOf[IPowerProducer])
					{
						val producer = te.asInstanceOf[IPowerProducer]
						val dir = ForgeDirection.getOrientation(i)
						producer.demandPowerFrom(dir.getOpposite, demand)
					}
				}
			}
		}
	}

	override def updateEntity()
	{
		super.updateEntity()
		if (!worldObj.isRemote)
		{
			demandPowerFromNeighbours()
			updateMachine()
		}
		if (dirtyNext)
		{
			dirtyNext = false
			markDirty()
		}
	}

	protected def triggerInventoryChanged(inv: IInventory, index: Int)
	{
		if (machine.isInstanceOf[IInventoryWatcher])
		{
			machine.asInstanceOf[IInventoryWatcher].onInventoryChanged(inv, index)
		}
	}

	override def onInventoryChanged(inv: IInventory, index: Int)
	{
		markDirtyNext()
		triggerInventoryChanged(inv, index)
	}

	override def onItemDiscarded(inv: IInventory, stack: ItemStack, index: Int, discardedAmount: Int)
	{
		if (machine.isInstanceOf[IInventoryWatcher])
		{
			machine.asInstanceOf[IInventoryWatcher].onItemDiscarded(inv, stack, index, discardedAmount)
		}
		val discarded = stack.copy()
		discarded.stackSize = discardedAmount
		ItemUtils.spawnItemStack(worldObj, xCoord, yCoord, zCoord, discarded, worldObj.rand)
	}
}
