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

import growthcraft.api.core.nbt.INBTSerializableContext;
import growthcraft.api.core.stream.IStreamable;

import net.minecraft.tileentity.TileEntity;

public interface IMachineLogic extends INBTSerializableContext, IStreamable
{
	void updateMachine(MachineUpdateState state);

	/**
	 * Power cost regardless of machine state, by simply being connected to a
	 * power source, the machine should drain this amount.
	 *
	 * @param state - machine state
	 * @return worked power cost
	 */
	int getRunningPowerCost(MachineUpdateState state);

	/**
	 * Can this machine work?
	 *
	 * @param state - machine state
	 * @return true, if the machine can work, false otherwise
	 */
	boolean canWork(MachineUpdateState state);

	/**
	 * Power cost if the machine "canWork", even if it doesn't actually do
	 * anything.
	 *
	 * @param state - machine state
	 * @return worked power cost
	 */
	int getWorkingPowerCost(MachineUpdateState state);

	/**
	 * How much power is required before the machine can work?
	 *
	 * @param state - machine state
	 * @return power threshold
	 */
	int getWorkingThreshold(MachineUpdateState state);

	/**
	 * Called when the machine actually needs to do some work.
	 *
	 * @param state - machine state
	 */
	void doWork(MachineUpdateState state);

	// Machines do not REQUIRE a TileEntity if they don't need access to the
	// world around them, most machines can do without it.
	// The only exception is the AutoCrafter, which requires a world for
	// the crafting recipes.
	void setTileEntity(TileEntity te);

	void awake();
}
