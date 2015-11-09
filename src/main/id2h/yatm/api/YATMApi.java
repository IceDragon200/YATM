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
package id2h.yatm.api;

import id2h.yatm.api.blastfurnace.BlastingRegistry;
import id2h.yatm.api.compactor.CompactingRegistry;
import id2h.yatm.api.crusher.CrushingRegistry;
import id2h.yatm.api.mixer.MixingRegistry;
import id2h.yatm.api.roller.RollingRegistry;

public class YATMApi
{
	private static final YATMApi inst = new YATMApi();

	private final BlastingRegistry blastingRegistry = new BlastingRegistry();
	private final CrushingRegistry crushingRegistry = new CrushingRegistry();
	private final CompactingRegistry compactingRegistry = new CompactingRegistry();
	private final MixingRegistry mixingRegistry = new MixingRegistry();
	private final RollingRegistry rollingRegistry = new RollingRegistry();

	public static YATMApi instance()
	{
		return inst;
	}

	public BlastingRegistry blasting()
	{
		return blastingRegistry;
	}

	public CrushingRegistry crushing()
	{
		return crushingRegistry;
	}

	public CompactingRegistry compacting()
	{
		return compactingRegistry;
	}

	public MixingRegistry mixing()
	{
		return mixingRegistry;
	}

	public RollingRegistry rolling()
	{
		return rollingRegistry;
	}
}
