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
package id2h.yatm.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import id2h.yatm.YATM;

import appeng.util.Platform;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class YATMPlatform
{
	private YATMPlatform() {}

	// Copied from Applied Energistics 2
	public static void openGui(@Nonnull EntityPlayer player, @Nullable TileEntity tile, @Nullable ForgeDirection dir, @Nonnull GuiType guiID)
	{
		if (Platform.isClient()) return;

		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;

		if (tile != null)
		{
			x = tile.xCoord;
			y = tile.yCoord;
			z = tile.zCoord;
		}

		player.openGui(YATM.instance(), guiID.ordinal(), player.getEntityWorld(), x, y, z);
	}
}
