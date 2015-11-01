/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package id2h.yatm.common.tileentity.machine;

import cofh.api.energy.EnergyStorage;

import net.minecraft.inventory.IInventory;
//import net.minecraft.nbt.NBTTagCompound;

// Heaters are blocks which act as a heat source, they do not "work", they
// simply have a running cost and will deactivate if they have no energy
public class MachineHeater extends AbstractProgressiveMachine
{
	@Override
	public int getRunningPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 20;
	}

	@Override
	public int getWorkingPowerCost(EnergyStorage energyStorage, IInventory inventory)
	{
		return 0;
	}

	@Override
	public boolean canWork(EnergyStorage en, IInventory _inv)
	{
		return en.getEnergyStored() > 0;
	}

	public void doWork(EnergyStorage _en, IInventory _inv)
	{

	}
}
