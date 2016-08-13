/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package id2h.yatm.common.block;

import id2h.yatm.common.tileentity.TileEntityWirelessRedstoneReceiver;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWirelessRedstoneReceiver extends YATMBlockBaseTile
{
	public BlockWirelessRedstoneReceiver()
	{
		super(Material.rock, TileEntityWirelessRedstoneReceiver.class);
		setStepSound(Block.soundTypeMetal);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("yatm.wireless_redstone_receiver");
		setBlockTextureName("yatm:wireless_redstone_receiver");
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int facing)
	{
		final TileEntityWirelessRedstoneReceiver te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			return te.getPowerValue();
		}
		return 0;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int facing)
	{
		return isProvidingWeakPower(world, x, y, z, facing);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			world.notifyBlocksOfNeighborChange(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, this);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
