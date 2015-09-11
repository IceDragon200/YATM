package id2h.yatm.client.util

import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection

object ConnectedTextureSolver
{
	val UP: Int = 1
	val RIGHT: Int = 2
	val DOWN: Int = 4
	val LEFT: Int = 8
	val OBSTRUCTED: Int = 16 // face is obstructed
	val FULLMASK: Int = 0x1F
	val MASK: Int = 0xF

	def indexForDirections(up: Boolean, right: Boolean, down: Boolean, left: Boolean): Int = {
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

	def solve(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block): Int = {
		var result: Int = 0
		var top: Boolean = false
		var bottom: Boolean = false
		var west: Boolean = false
		var east: Boolean = false
		var north: Boolean = false
		var south: Boolean = false

		top = Block.isEqualTo(block, world.getBlock(x, y + 1, z))
		bottom = Block.isEqualTo(block, world.getBlock(x, y - 1, z))
		north = Block.isEqualTo(block, world.getBlock(x, y, z - 1))
		east = Block.isEqualTo(block, world.getBlock(x + 1, y, z))
		south = Block.isEqualTo(block, world.getBlock(x, y, z + 1))
		west = Block.isEqualTo(block, world.getBlock(x - 1, y, z))

		// solve for top face
		result |= indexForDirections(north, east, south, west)         // DOWN
		if (bottom) result |= OBSTRUCTED
		result |= indexForDirections(north, east, south, west) << 5    // UP
		if (top) result |= OBSTRUCTED << 5
		result |= indexForDirections(top, west, bottom, east) << 10     // NORTH
		if (north) result |= OBSTRUCTED << 10
		result |= indexForDirections(top, east, bottom, west) << 15    // SOUTH
		if (south) result |= OBSTRUCTED << 15
		result |= indexForDirections(top, south, bottom, north) << 20  // WEST
		if (west) result |= OBSTRUCTED << 20
		result |= indexForDirections(top, north, bottom, south) << 25  // EAST
		if (east) result |= OBSTRUCTED << 25

		result
	}

	def solve(world: IBlockAccess, x: Int, y: Int, z: Int): Int = solve(world, x, y, z, world.getBlock(x, y, z))

	def indexForSide(side: ForgeDirection, meta: Int): Int = {
		val value: Int = side match {
			case ForgeDirection.DOWN => meta
			case ForgeDirection.UP => (meta >> 5)
			case ForgeDirection.NORTH => (meta >> 10)
			case ForgeDirection.SOUTH => (meta >> 15)
			case ForgeDirection.WEST => (meta >> 20)
			case ForgeDirection.EAST => (meta >> 25)
			case _ => 0
		}
		value & FULLMASK
	}

	def indexForSide(side: Int, meta: Int): Int = indexForSide(ForgeDirection.getOrientation(side), meta)

	def solveForSide(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, side: Int): Int = {
		indexForSide(side, solve(world, x, y, z, block))
	}
}
