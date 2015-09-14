package id2h.yatm.client.util

import id2h.yatm.block.Sides

import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection

object ConnectedTextureSolver
{
	val UP: Int = 1
	val RIGHT: Int = 2
	val DOWN: Int = 4
	val LEFT: Int = 8
	val DIR4: Int = 0xF
	val DIR8: Int = 0xF0
	val OBSTRUCTED: Int = 256 // face is obstructed
	val FULLMASK: Int = 0x1FF
	val MASK: Int = 0xFF

	private def removeInvalidDirs(value: Int): Int = {
		(value & 0xF) match {
			case 0 => 0
			case 1 => value & 0x10F
			case 2 => value & 0x10F
			case 3 => value & 0x11F
			case 4 => value & 0x10F
			case 5 => value & 0x10F
			case 6 => value & 0x12F
			case 7 => value & 0x13F
			case 8 => value & 0x10F
			case 9 => value & 0x18F
			case 10 => value & 0x10F
			case 11 => value & 0x19F
			case 12 => value & 0x14F
			case 13 => value & 0x1CF
			case 14 => value & 0x16F
			case _ => value
		}
	}

	private def packDirs(up: Boolean, right: Boolean, down: Boolean, left: Boolean): Int = {
		var index: Int = 0
		if (up)
			index |= UP
		if (right)
			index |= RIGHT
		if (down)
			index |= DOWN
		if (left)
			index |= LEFT
		index
	}

	def solveSide(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, side: Int, dir8: Boolean): Int = {
		var north: Boolean = false
		var east: Boolean = false
		var south: Boolean = false
		var west: Boolean = false

		var north_east: Boolean = false
		var north_west: Boolean = false
		var south_east: Boolean = false
		var south_west: Boolean = false

		var facing: Boolean = false

		// north, east, south, west
		side match {
			case Sides.DOWN  =>
				north = Block.isEqualTo(block, world.getBlock(x, y, z - 1))
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z))
				south = Block.isEqualTo(block, world.getBlock(x, y, z + 1))
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z - 1))
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z - 1))
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z + 1))
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z + 1))
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
			case Sides.UP    =>
				north = Block.isEqualTo(block, world.getBlock(x, y, z - 1))
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z))
				south = Block.isEqualTo(block, world.getBlock(x, y, z + 1))
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z - 1))
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z - 1))
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y, z + 1))
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y, z + 1))
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
			case Sides.NORTH =>
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
				east = Block.isEqualTo(block, world.getBlock(x - 1, y, z))
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
				west = Block.isEqualTo(block, world.getBlock(x + 1, y, z))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x - 1, y + 1, z))
					north_west = Block.isEqualTo(block, world.getBlock(x + 1, y + 1, z))
					south_east = Block.isEqualTo(block, world.getBlock(x - 1, y - 1, z))
					south_west = Block.isEqualTo(block, world.getBlock(x + 1, y - 1, z))
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y, z - 1))
			case Sides.SOUTH =>
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
				east = Block.isEqualTo(block, world.getBlock(x + 1, y, z))
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
				west = Block.isEqualTo(block, world.getBlock(x - 1, y, z))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x + 1, y + 1, z))
					north_west = Block.isEqualTo(block, world.getBlock(x - 1, y + 1, z))
					south_east = Block.isEqualTo(block, world.getBlock(x + 1, y - 1, z))
					south_west = Block.isEqualTo(block, world.getBlock(x - 1, y - 1, z))
				}

				facing = Block.isEqualTo(block, world.getBlock(x, y, z + 1))
			case Sides.WEST  =>
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
				east = Block.isEqualTo(block, world.getBlock(x, y, z + 1))
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
				west = Block.isEqualTo(block, world.getBlock(x, y, z - 1))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x, y + 1, z + 1))
					north_west = Block.isEqualTo(block, world.getBlock(x, y + 1, z - 1))
					south_east = Block.isEqualTo(block, world.getBlock(x, y - 1, z + 1))
					south_west = Block.isEqualTo(block, world.getBlock(x, y - 1, z - 1))
				}

				facing = Block.isEqualTo(block, world.getBlock(x - 1, y, z))
			case Sides.EAST  =>
				north = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
				east = Block.isEqualTo(block, world.getBlock(x, y, z - 1))
				south = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
				west = Block.isEqualTo(block, world.getBlock(x, y, z + 1))

				if (dir8) {
					north_east = Block.isEqualTo(block, world.getBlock(x, y + 1, z - 1))
					north_west = Block.isEqualTo(block, world.getBlock(x, y + 1, z + 1))
					south_east = Block.isEqualTo(block, world.getBlock(x, y - 1, z - 1))
					south_west = Block.isEqualTo(block, world.getBlock(x, y - 1, z + 1))
				}

				facing = Block.isEqualTo(block, world.getBlock(x + 1, y, z))
			case _ =>
				return 0
		}

		removeInvalidDirs((packDirs(north_east, south_east, south_west, north_west) << 4) |
						  packDirs(north, east, south, west)) | (if (facing) OBSTRUCTED else 0)
	}

	def indexToIcon(side: Int, value: Int): Int = {
		val index: Int = (value & 0xFF) match {
			case 0x00 => 0
			case 0x01 => 1
			case 0x02 => 2
			case 0x03 => 3
			case 0x04 => 4
			case 0x05 => 5
			case 0x06 => 6
			case 0x07 => 7
			case 0x08 => 8
			case 0x09 => 9
			case 0x0a => 10
			case 0x0b => 11
			case 0x0c => 12
			case 0x0d => 13
			case 0x0e => 14
			case 0x0f => 15
			case 0x13 => 16
			case 0x17 => 17
			case 0x1b => 18
			case 0x1f => 19
			case 0x26 => 20
			case 0x27 => 21
			case 0x2e => 22
			case 0x2f => 23
			case 0x37 => 24
			case 0x3f => 25
			case 0x4c => 26
			case 0x4d => 27
			case 0x4e => 28
			case 0x4f => 29
			case 0x5f => 30
			case 0x6e => 31
			case 0x6f => 32
			case 0x7f => 33
			case 0x89 => 34
			case 0x8b => 35
			case 0x8d => 36
			case 0x8f => 37
			case 0x9b => 38
			case 0x9f => 39
			case 0xaf => 40
			case 0xbf => 41
			case 0xcd => 42
			case 0xcf => 43
			case 0xdf => 44
			case 0xef => 45
			case 0xff => 46
			case _ => 0
		}
		index | (value & 0x100)
	}

	def iconForSide(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, side: Int, dir8: Boolean): Int = {
		indexToIcon(side, solveSide(world, x, y, z, block, side, dir8))
	}
}
