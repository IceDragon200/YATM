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
package io.polyfox.yatm.common.tileentity.machine;

import io.polyfox.yatm.api.power.PowerStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.MathHelper;

// Heaters are blocks which act as a heat source, they do not "work", they
// simply have a running cost and will deactivate if they have no energy
public class MachineHeater extends AbstractMachine
{
	@Override
	public int getRunningPowerCost(MachineUpdateState state)
	{
		return 50;
	}

	@Override
	public int getWorkingPowerCost(MachineUpdateState state)
	{
		return 0;
	}

	@Override
	public boolean canWork(MachineUpdateState state)
	{
		return state.powerStorage.getAmount() > 0;
	}

	public void doWork(MachineUpdateState state)
	{

	}

	public float getHeatValue(PowerStorage pw, IInventory _inv)
	{
		final long enStored = pw.getAmount();
		if (enStored > 0)
		{
			final double rt = (double)enStored / (pw.getCapacity() * 0.5);
			return MathHelper.clamp_float((float)rt, 0.0f, 1.0f);
		}
		return 0.0f;
	}
}
