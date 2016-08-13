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
import id2h.yatm.api.core.wireless.EnumWirelessCode;
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
		// The address this event is emitted on
		public final String address;
		// The requested address that the sender expects to hear a response on
		public final String responseAddress;
		// Where did the event emit from?
		public final Point3 origin;
		// How far in each direction should the event travel
		public final double range;
		// An EnumWirelessCode
		public final EnumWirelessCode code;
		// The data being sent, please keep it small (under 32 bytes)
		private final ByteBuf payload;

		public WirelessEvent(int p_dimensionId, String p_address, String p_responseAddress, Point3 p_origin, double p_range, EnumWirelessCode p_code, ByteBuf p_payload)
		{
			this.dimensionId = p_dimensionId;
			this.address = p_address;
			this.responseAddress = p_responseAddress;
			this.origin = p_origin;
			this.range = p_range;
			this.code = p_code;
			this.payload = p_payload;
		}

		public ByteBuf getPayload()
		{
			return payload.duplicate();
		}

		public String toString()
		{
			return String.format("<WirelessEvent dimensionId=%d address=%s responseAddress=%s origin=%s range=%f code=%s>", dimensionId, address, responseAddress, origin, range, code);
		}
	}

	/**
	 * dimensionId => events
	 */
	private final Map<Integer, List<WirelessEvent>> eventsInDimension = new HashMap<Integer, List<WirelessEvent>>();
	private final Map<Integer, List<WirelessEvent>> eventsInDimensionReponses = new HashMap<Integer, List<WirelessEvent>>();

	@SubscribeEvent
	public void unload(WorldEvent.Unload event)
	{
		eventsInDimension.clear();
		eventsInDimensionReponses.clear();
	}

	public void pub(WirelessEvent event)
	{
		//YATM.getLogger().info("WirelessSystem#pub event=%s", event);
		synchronized (eventsInDimension)
		{
			if (eventsInDimension.get(event.dimensionId) == null)
			{
				eventsInDimension.put(event.dimensionId, new ArrayList<WirelessEvent>());
			}
			synchronized (eventsInDimensionReponses)
			{
				if (eventsInDimensionReponses.get(event.dimensionId) == null)
				{
					eventsInDimensionReponses.put(event.dimensionId, new ArrayList<WirelessEvent>());
				}
			}
			final List<WirelessEvent> events = eventsInDimension.get(event.dimensionId);
			synchronized (events)
			{
				events.add(event);
			}
		}
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
			synchronized (eventsInDimensionReponses)
			{
				final List<WirelessEvent> events = eventsInDimension.get(dimensionId);
				if (events == null) return;
				final List<WirelessEvent> responses = eventsInDimensionReponses.get(dimensionId);
				synchronized (events)
				{
					synchronized (responses)
					{
						final List<TileEntity> wirelessTiles = new ArrayList<TileEntity>();
						for (TileEntity te : (List<TileEntity>)event.world.loadedTileEntityList)
						{
							if (te instanceof IWirelessReceiver)
							{
								wirelessTiles.add(te);
							}
						}
						for (WirelessEvent wev : events)
						{
							//YATM.getLogger().info("WirelessSystem#update event=%s", wev);
							for (TileEntity te : wirelessTiles)
							{
								if (te instanceof IWirelessReceiver)
								{
									final double dist = Math.sqrt(te.getDistanceFrom(wev.origin.x, wev.origin.y, wev.origin.z));
									//YATM.getLogger().info("WirelessSystem#update testing tile entity with tile_entity=`%s` dist=%f event=`%s`", te, dist, wev);
									if (dist > wev.range) continue;
									final IWirelessReceiver wr = (IWirelessReceiver)te;
									final WirelessEvent response = wr.onWirelessEvent(wev);
									if (response != null)
									{
										responses.add(response);
									}
								}
							}
						}
						events.clear();
						events.addAll(responses);
						responses.clear();
					}
				}
			}
		}
	}
}
