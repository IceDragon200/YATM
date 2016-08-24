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
package io.polyfox.yatm.client.util;

import io.polyfox.yatm.util.BlockFacing;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class ConnectedTextureSolver
{
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 4;
	public static final int LEFT = 8;
	public static final int DIR4 = 0xF;
	public static final int DIR8 = 0xF0;
	public static final int OBSTRUCTED = 0x100;
	public static final int FULLMASK = 0x1FF;
	public static final int MASK = 0xFF;

	private ConnectedTextureSolver() {}

	private static int removeInvalidDirs(int value)
	{
		switch (value & 0xF)
		{
			case 0:
				return 0;
			case 1:
				return value & 0x10F;
			case 2:
				return value & 0x10F;
			case 3:
				return value & 0x11F;
			case 4:
				return value & 0x10F;
			case 5:
				return value & 0x10F;
			case 6:
				return value & 0x12F;
			case 7:
				return value & 0x13F;
			case 8:
				return value & 0x10F;
			case 9:
				return value & 0x18F;
			case 10:
				return value & 0x10F;
			case 11:
				return value & 0x19F;
			case 12:
				return value & 0x14F;
			case 13:
				return value & 0x1CF;
			case 14:
				return value & 0x16F;
			default:
				break;
		}
		return value;
	}

	private static int packDirs(boolean up, boolean right, boolean down, boolean left)
	{
		int index = 0;

		if (up) index |= UP;
		if (right) index |= RIGHT;
		if (down) index |= DOWN;
		if (left) index |= LEFT;

		return index;
	}

	public static int solveSide(IBlockAccess world, int x, int y, int z, Block block, BlockFacing side, boolean dir8)
	{
		boolean north = false;
		boolean east = false;
		boolean south = false;
		boolean west = false;

		boolean north_east = false;
		boolean north_west = false;
		boolean south_east = false;
		boolean south_west = false;

		boolean facing = false;

		// north, east, south, west
		switch (side)
		{
			case DOWN:
				north = Block.isEqualTo(block, world.getBlock(x, y, z - 1));
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z));
				south = Block.isEqualTo(block, world.getBlock(x, y, z + 1));
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z - 1));
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z - 1));
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z + 1));
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z + 1));
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y - 1, z));
				break;
			case UP:
				north = Block.isEqualTo(block, world.getBlock(x, y, z - 1));
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z));
				south = Block.isEqualTo(block, world.getBlock(x, y, z + 1));
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z - 1));
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z - 1));
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z + 1));
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z + 1));
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y + 1, z));
				break;
			case NORTH:
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z));
				east = Block.isEqualTo(block, world.getBlock(x - 1, y, z));
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z));
				west = Block.isEqualTo(block, world.getBlock(x + 1, y, z));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x - 1, y + 1, z));
					north_west = Block.isEqualTo(block, world.getBlock(x + 1, y + 1, z));
					south_east = Block.isEqualTo(block, world.getBlock(x - 1, y - 1, z));
					south_west = Block.isEqualTo(block, world.getBlock(x + 1, y - 1, z));
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y, z - 1));
				break;
			case SOUTH:
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z));
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z));
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z));
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y + 1, z));
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y + 1, z));
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y - 1, z));
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y - 1, z));
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y, z + 1));
				break;
			case WEST:
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z));
				east = Block.isEqualTo(block, world.getBlock(x, y, z + 1));
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z));
				west = Block.isEqualTo(block, world.getBlock(x, y, z - 1));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x, y + 1, z + 1));
					north_west = Block.isEqualTo(block, world.getBlock(x, y + 1, z - 1));
					south_east = Block.isEqualTo(block, world.getBlock(x, y - 1, z + 1));
					south_west = Block.isEqualTo(block, world.getBlock(x, y - 1, z - 1));
				}

				facing = Block.isEqualTo(block, world.getBlock(x - 1, y, z));
				break;
			case EAST:
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z));
				east = Block.isEqualTo(block, world.getBlock(x, y, z - 1));
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z));
				west = Block.isEqualTo(block, world.getBlock(x, y, z + 1));

				if (dir8)
				{
					north_east = Block.isEqualTo(block, world.getBlock(x, y + 1, z - 1));
					north_west = Block.isEqualTo(block, world.getBlock(x, y + 1, z + 1));
					south_east = Block.isEqualTo(block, world.getBlock(x, y - 1, z - 1));
					south_west = Block.isEqualTo(block, world.getBlock(x, y - 1, z + 1));
				}

				facing = Block.isEqualTo(block, world.getBlock(x + 1, y, z));
				break;
			default:
				return 0;
		}

		return removeInvalidDirs((packDirs(north_east, south_east, south_west, north_west) << 4) |
			packDirs(north, east, south, west)) | (facing ? OBSTRUCTED : 0);
	}

	public static int indexToIcon(int side, int value)
	{
		int index = 0;
		switch (value & 0xFF)
		{
			case 0x00:
				index = 0;
				break;
			case 0x01:
				index = 1;
				break;
			case 0x02:
				index = 2;
				break;
			case 0x03:
				index = 3;
				break;
			case 0x04:
				index = 4;
				break;
			case 0x05:
				index = 5;
				break;
			case 0x06:
				index = 6;
				break;
			case 0x07:
				index = 7;
				break;
			case 0x08:
				index = 8;
				break;
			case 0x09:
				index = 9;
				break;
			case 0x0a:
				index = 10;
				break;
			case 0x0b:
				index = 11;
				break;
			case 0x0c:
				index = 12;
				break;
			case 0x0d:
				index = 13;
				break;
			case 0x0e:
				index = 14;
				break;
			case 0x0f:
				index = 15;
				break;
			case 0x13:
				index = 16;
				break;
			case 0x17:
				index = 17;
				break;
			case 0x1b:
				index = 18;
				break;
			case 0x1f:
				index = 19;
				break;
			case 0x26:
				index = 20;
				break;
			case 0x27:
				index = 21;
				break;
			case 0x2e:
				index = 22;
				break;
			case 0x2f:
				index = 23;
				break;
			case 0x37:
				index = 24;
				break;
			case 0x3f:
				index = 25;
				break;
			case 0x4c:
				index = 26;
				break;
			case 0x4d:
				index = 27;
				break;
			case 0x4e:
				index = 28;
				break;
			case 0x4f:
				index = 29;
				break;
			case 0x5f:
				index = 30;
				break;
			case 0x6e:
				index = 31;
				break;
			case 0x6f:
				index = 32;
				break;
			case 0x7f:
				index = 33;
				break;
			case 0x89:
				index = 34;
				break;
			case 0x8b:
				index = 35;
				break;
			case 0x8d:
				index = 36;
				break;
			case 0x8f:
				index = 37;
				break;
			case 0x9b:
				index = 38;
				break;
			case 0x9f:
				index = 39;
				break;
			case 0xaf:
				index = 40;
				break;
			case 0xbf:
				index = 41;
				break;
			case 0xcd:
				index = 42;
				break;
			case 0xcf:
				index = 43;
				break;
			case 0xdf:
				index = 44;
				break;
			case 0xef:
				index = 45;
				break;
			case 0xff:
				index = 46;
				break;
			default:
				index = 0;
		}
		return index | (value & 0x100);
	}

	public static int iconForSide(IBlockAccess world, int x, int y, int z, Block block, int side, boolean dir8)
	{
		return indexToIcon(side, solveSide(world, x, y, z, block, BlockFacing.byIndex(side), dir8));
	}
}
