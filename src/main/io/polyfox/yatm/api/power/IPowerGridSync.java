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
package io.polyfox.yatm.api.power;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerGridSync
{
	int getPowerSyncLevelFrom(ForgeDirection from);
	/**
	 * Queries the tile's sync direction for the given IPowerGridSync tile
	 * The target tile should respond with it's expectation, if it is a lower priority it should respond with a RECEIVE direction
	 * Meaning "I expect to receive power from this `other`"
	 * If it is a higher priority it should respond with a SEND direction
	 * Meaning "I will send power to this `other`"
	 *
	 * @param from direction to sync from
	 * @param other the tile being synched with
	 * @return sync direction
	 */
	PowerSyncDirection getPowerSyncDirectionFor(ForgeDirection from, IPowerGridSync other);
	long syncPowerFrom(ForgeDirection from, long value);
}
