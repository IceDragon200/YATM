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

public enum BlockFacing
{
	DOWN(-1, "down"),
	UP(-1, "up"),
	NORTH(0, "north"),
	SOUTH(1, "south"),
	WEST(2, "west"),
	EAST(3, "east");

	// 6 directions
	public static final BlockFacing[] DIR6 = { DOWN, UP, NORTH, SOUTH, WEST, EAST };
	// 6 directions - opposite / 180
	public static final BlockFacing[] OPP6 = { UP, DOWN, SOUTH, NORTH, EAST, WEST };

	// 4 directions - Forge
	public static final ForgeDirection[] FORGE_DIR4 = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST };

	// 4 directions - Y axis
	public static final BlockFacing[] DIR4 = { NORTH, SOUTH, WEST, EAST };
	// 4 directions - opposite / 180 - Y axis
	public static final BlockFacing[] OPP4 = { SOUTH, NORTH, EAST, WEST };

	// rotate counter-clockwise rotated on the Y axis
	public static final BlockFacing[] CCW = { WEST, EAST, SOUTH, NORTH };

	// rotate clockwise rotated on the Y axis
	public static final BlockFacing[] CW = { EAST, WEST, NORTH, SOUTH };

	public static final BlockFacing[][] ORIENTATIONS4 = {
		{ NORTH, SOUTH, WEST, EAST },
		{ SOUTH, NORTH, EAST, WEST },
		{ WEST, EAST, NORTH, SOUTH },
		{ EAST, WEST, SOUTH, NORTH }
	};

	private final int index;
	private final int horizontalIndex;
	private final String name;

	private BlockFacing(int p_horizontalIndex, String p_name)
	{
		this.horizontalIndex = p_horizontalIndex;
		this.index = ordinal();
		this.name = p_name;
	}

	public int getIndex()
	{
		return index;
	}

	public int getHorizontalIndex()
	{
		return horizontalIndex;
	}

	public ForgeDirection getForgeDirection()
	{
		return ForgeDirection.VALID_DIRECTIONS[index];
	}

	public BlockFacing getOpposite()
	{
		return OPP6[index];
	}

	public BlockFacing rotateClockwiseOnY()
	{
		switch (this)
		{
			case UP:
			case DOWN:
				return this;
			default:
				return CW[horizontalIndex];
		}
	}

	public BlockFacing rotateCounterClockwiseOnY()
	{
		switch (this)
		{
			case UP:
			case DOWN:
				return this;
			default:
				return CCW[horizontalIndex];
		}
	}

	public static BlockFacing byHorizontalIndex(int index)
	{
		if (index >= 0 && index < DIR4.length)
		{
			return DIR4[index];
		}
		return SOUTH;
	}

	public static BlockFacing byIndex(int index)
	{
		if (index >= 0 && index < DIR6.length)
		{
			return DIR6[index];
		}
		return SOUTH;
	}

	public static BlockFacing byForgeDirection(ForgeDirection dir)
	{
		return byIndex(dir.ordinal());
	}
}
