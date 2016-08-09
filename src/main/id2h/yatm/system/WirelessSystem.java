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
package id2h.yatm.system;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import growthcraft.api.core.util.Point3;
import id2h.yatm.common.tileentity.feature.IWirelessReceiver;
//import id2h.yatm.YATM;

import io.netty.buffer.ByteBuf;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.WorldEvent;

public class WirelessSystem
{
	public static class WirelessEvent
	{
		// Which dimension emitted the event?
		public final int dimensionId;
		// A frequency, channel, whatever you want to call it
		public final int frequency;
		// Where did the event emit from?
		public final Point3 origin;
		// How far in each direction should the event travel
		public final double range;
		// The data being sent, please keep it small (under 32 bytes)
		private final ByteBuf payload;

		public WirelessEvent(int p_dimensionId, int p_frequency, Point3 p_origin, double p_range, ByteBuf p_payload)
		{
			this.dimensionId = p_dimensionId;
			this.frequency = p_frequency;
			this.origin = p_origin;
			this.range = p_range;
			this.payload = p_payload;
		}

		public ByteBuf getPayload()
		{
			return payload.duplicate();
		}
	}

	/**
	 * dimensionId => events
	 */
	private final Map<Integer, List<WirelessEvent>> eventsInDimension = new HashMap<Integer, List<WirelessEvent>>();

	@SubscribeEvent
	public void unload(WorldEvent.Unload event)
	{
		eventsInDimension.clear();
	}

	@SubscribeEvent
	public void update(WorldTickEvent event)
	{
		// We only process these events on the server
		if (event.side == Side.CLIENT) return;
		// and we only do them at the start of the world tick
		if (event.phase == TickEvent.Phase.END) return;
		final int dimensionId = event.world.provider.dimensionId;
		synchronized (eventsInDimension)
		{
			if (eventsInDimension.containsKey(dimensionId))
			{
				final List<WirelessEvent> events = eventsInDimension.get(dimensionId);
				for (WirelessEvent wev : events)
				{
					for (TileEntity te : (List<TileEntity>)event.world.loadedTileEntityList)
					{
						final double dist = Math.sqrt(
							Math.pow(wev.origin.x - te.xCoord, 2) +
							Math.pow(wev.origin.y - te.yCoord, 2) +
							Math.pow(wev.origin.z - te.zCoord, 2)
						);
						if (dist < wev.range)
						{
							if (te instanceof IWirelessReceiver)
							{
								final IWirelessReceiver wr = (IWirelessReceiver)te;
								final WirelessEvent _response = wr.onWirelessEvent(wev);
								// @todo do something with the response
							}
						}
					}
				}
				events.clear();
			}
		}
	}

	public void pub(WirelessEvent event)
	{
		if (!eventsInDimension.containsKey(event.dimensionId))
		{
			eventsInDimension.put(event.dimensionId, new ArrayList<WirelessEvent>());
		}
		eventsInDimension.get(event.dimensionId).add(event);
	}
}
