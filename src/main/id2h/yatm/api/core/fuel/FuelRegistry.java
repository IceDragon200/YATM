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
package id2h.yatm.api.core.fuel;

import java.util.ArrayList;
import java.util.List;

import growthcraft.api.core.definition.IMultiFluidStacks;
import growthcraft.api.core.definition.IMultiItemStacks;
import growthcraft.api.core.util.MultiStacksUtil;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FuelRegistry
{
	protected static class FluidFuelMapping
	{
		public final IMultiFluidStacks fluidStacks;
		public final Fuel fuel;

		public FluidFuelMapping(IMultiFluidStacks p_fluidStacks, Fuel p_fuel)
		{
			this.fluidStacks = p_fluidStacks;
			this.fuel = p_fuel;
		}

		public boolean contains(FluidStack stack)
		{
			return fluidStacks.containsFluidStack(stack);
		}
	}

	protected static class ItemFuelMapping
	{
		public final IMultiItemStacks itemStacks;
		public final Fuel fuel;

		public ItemFuelMapping(IMultiItemStacks p_itemStacks, Fuel p_fuel)
		{
			this.itemStacks = p_itemStacks;
			this.fuel = p_fuel;
		}

		public boolean contains(ItemStack stack)
		{
			return itemStacks.containsItemStack(stack);
		}
	}

	private final List<ItemFuelMapping> itemFuels = new ArrayList<ItemFuelMapping>();
	private final List<FluidFuelMapping> fluidFuels = new ArrayList<FluidFuelMapping>();

	public void putFluidFuel(Object fluid, Fuel fuel)
	{
		fluidFuels.add(new FluidFuelMapping(MultiStacksUtil.toMultiFluidStacks(fluid), fuel));
	}

	public void putItemFuel(Object item, Fuel fuel)
	{
		itemFuels.add(new ItemFuelMapping(MultiStacksUtil.toMultiItemStacks(item), fuel));
	}

	public Fuel getFuel(ItemStack stack)
	{
		for (ItemFuelMapping mapping : itemFuels)
		{
			if (mapping.contains(stack))
			{
				return mapping.fuel;
			}
		}
		return null;
	}

	public Fuel getFuel(FluidStack stack)
	{
		for (FluidFuelMapping mapping : fluidFuels)
		{
			if (mapping.contains(stack))
			{
				return mapping.fuel;
			}
		}
		return null;
	}
}
