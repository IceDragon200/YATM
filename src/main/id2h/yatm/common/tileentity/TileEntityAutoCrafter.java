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
package id2h.yatm.common.tileentity;

import id2h.yatm.common.inventory.IYATMInventory;
import id2h.yatm.common.inventory.YATMInternalInventory;
import id2h.yatm.common.tileentity.energy.YATMEnergyStorage;
import id2h.yatm.common.tileentity.machine.IMachineLogic;
import id2h.yatm.common.tileentity.machine.MachineAutoCrafter;

/*
 * An RF powered Crafting bench which autocrafts from a 8 slot input to a
 * 8 slot output, this thing eats power like its nobody's business however.
 */
public class TileEntityAutoCrafter extends YATMPoweredMachine
{
	@Override
	protected YATMEnergyStorage createEnergyStorage()
	{
		return new YATMEnergyStorage(40000, 10);
	}

	@Override
	protected IYATMInventory createInventory()
	{
		/*
		 * 0..7 - :Input
		 * 8    - :Output
		 * 9    - :Crafting Result
		 * 10..15 - :RESERVED
		 * 16..24 - :Fake crafting
		 * 25..34 - :Processing
		 */
		// 8 - input, 8 - output, 9 crafting, 9 processing
		return new YATMInternalInventory(this, 8 + 8 + 9 + 9);
	}

	@Override
	protected IMachineLogic createMachine()
	{
		return new MachineAutoCrafter();
	}
}
