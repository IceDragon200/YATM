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
package id2h.yatm.network.handlers;

import id2h.yatm.network.messages.UpdateTilePropertyMessage;
import id2h.yatm.common.tileentity.feature.IUpdatableTile;
import id2h.yatm.YATM;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class UpdateTilePropertyMessageHandler implements IMessageHandler<UpdateTilePropertyMessage, IMessage>
{
	@Override
	public IMessage onMessage(UpdateTilePropertyMessage message, MessageContext ctx)
	{
		final World world = ctx.getServerHandler().playerEntity.worldObj;
		final TileEntity te = world.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
		if (te instanceof IUpdatableTile)
		{
			((IUpdatableTile)te).updateTilePropertyFromBytes(message.code, message.payload);
		}
		else
		{
			YATM.getLogger().error("Got a UpdateTilePropertyMessage but there is no IUpdatableTile at the specified location: xCoord=%d yCoord=%d zCoord=%d", message.xCoord, message.yCoord, message.zCoord);
		}
		return null;
	}
}
