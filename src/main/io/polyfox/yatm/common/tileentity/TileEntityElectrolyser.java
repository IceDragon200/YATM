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
package io.polyfox.yatm.common.tileentity;

import io.polyfox.yatm.api.power.PowerStorage;
import io.polyfox.yatm.api.power.PowerThrottle;
import io.polyfox.yatm.common.inventory.ContainerElectrolyser;
import io.polyfox.yatm.common.inventory.IYATMInventory;
import io.polyfox.yatm.common.inventory.YATMInternalInventory;
import io.polyfox.yatm.common.tileentity.machine.IMachineLogic;
import io.polyfox.yatm.common.tileentity.machine.MachineElectrolyser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileEntityElectrolyser extends TilePoweredMachine
{
	@Override
	protected PowerStorage createPowerStorage()
	{
		return new PowerStorage(40000);
	}

	@Override
	public PowerThrottle createPowerThrottle(PowerStorage storage)
	{
		return new PowerThrottle(storage, 200, 200);
	}

	@Override
	public IYATMInventory createInventory()
	{
		return new YATMInternalInventory(this, 7);
	}

	@Override
	public String getGuiID()
	{
		return "yatm:electrolyser";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerElectrolyser(playerInventory, this);
	}

	@Override
	public IMachineLogic createMachine()
	{
		return new MachineElectrolyser();
	}
}
