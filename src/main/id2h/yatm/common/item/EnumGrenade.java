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
package id2h.yatm.common.item;

import java.util.HashMap;
import java.util.Map;

import growthcraft.api.core.definition.IItemStackFactory;
import id2h.yatm.YATM;

import net.minecraft.item.ItemStack;

public enum EnumGrenade implements IItemStackFactory
{
	DUMMY(0, "dummy"),
	/**
	 * Acts like thrown TNT, destroys blocks and injures nearby entities
	 */
	FRAGMENTATION(10, "frag"),
	/**
	 * Injures entities nearby, leaves blocks mostly intact...
	 */
	CONCUSSION(20, "concussion"),
	/**
	 * Creates fires upon detonating
	 */
	INCENDIARY(30, "incendiary"),
	/**
	 * Temporarily paralyzes entities
	 */
	STUN(40, "stun"),
	/**
	 * Blinds entities upon detonation
	 */
	FLASH_BANG(41, "flash"),
	/**
	 * Disables machines upon detontation for a period of time
	 */
	EMP(50, "emp"),
	/**
	 * temporarily increases gravity in the area
	 */
	GRAVITY(60, "gravity"),
	/**
	 * temporarily inverts gravity in the area
	 */
	ANTI_GRAVITY(61, "anti_gravity"),
	/**
	 * A bottle of compressed air, forces entities away from impact area
	 */
	AIR(70, "air"),
	/**
	 * Sucks entities towards the detonation zone
	 */
	SUCTION(71, "suction"),
	/**
	 * Run all you want, this will eat you.
	 */
	BLACK_HOLE(80, "black_hole");

	public static final Map<Integer, EnumGrenade> idToEnum = new HashMap<Integer, EnumGrenade>();
	static
	{
		for (EnumGrenade en : values())
		{
			idToEnum.put(en.id, en);
		}
	}
	public final int id;
	public final String unlocalizedName;

	private EnumGrenade(int p_id, String p_name)
	{
		this.id = p_id;
		this.unlocalizedName = p_name;
	}

	@Override
	public ItemStack asStack(int size)
	{
		return YATM.items.grenade.asStack(size, id);
	}

	@Override
	public ItemStack asStack()
	{
		return asStack(1);
	}

	public static EnumGrenade getSafeById(int id)
	{
		if (idToEnum.containsKey(id))
		{
			return idToEnum.get(id);
		}
		return FRAGMENTATION;
	}
}
