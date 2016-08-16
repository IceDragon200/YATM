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
package io.polyfox.yatm.util;

import net.minecraftforge.common.util.ForgeDirection;

public class BlockSides
{
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	public static final int EAST = 5;

	// 6 directions
	public static final int[] DIR6 = { DOWN, UP, NORTH, SOUTH, WEST, EAST };
	// 6 directions - opposite / 180
	public static final int[] OPP6 = { UP, DOWN, SOUTH, NORTH, EAST, WEST };

	// 4 directions - Forge
	public static final ForgeDirection[] FORGE_DIR4 = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST };

	// 4 directions - Y axis
	public static final int[] DIR4 = { NORTH, SOUTH, WEST, EAST };
	// 4 directions - opposite / 180 - Y axis
	public static final int[] OPP4 = { SOUTH, NORTH, EAST, WEST };

	// rotate counter-clockwise rotated on the Y axis
	public static final int[] CCW = { WEST, EAST, SOUTH, NORTH };
	// rotate clockwise rotated on the Y axis
	public static final int[] CW = { EAST, WEST, NORTH, SOUTH };

	public static final int[][] ORIENTATIONS4 = {
		{ NORTH, SOUTH, WEST, EAST },
		{ SOUTH, NORTH, EAST, WEST },
		{ WEST, EAST, NORTH, SOUTH },
		{ EAST, WEST, SOUTH, NORTH }
	};

	private BlockSides() {}

	public static int getNormalized4(int index)
	{
		return BlockSides.ORIENTATIONS4[index][0];
	}

	public static int getNormalizedIndex4(int index)
	{
		return getNormalized4(index) - 2;
	}

	public static int rotateClockwiseIndex(int index)
	{
		return CW[index] - 2;
	}

	public static int rotateCounterClockwiseIndex(int index)
	{
		return CCW[index] - 2;
	}

	public static int getOppositeIndex4(int index)
	{
		return BlockSides.OPP4[index] - 2;
	}

	public static ForgeDirection getOppositeForgeDirection4(int index)
	{
		return BlockSides.FORGE_DIR4[getOppositeIndex4(index)];
	}
}
