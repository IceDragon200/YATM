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

import java.util.Map;
import java.util.HashMap;

import appeng.client.texture.FlippableIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class StateIconLoader
{
	public static class IconVariant
	{
		public String name;
		public String fallback;

		public IconVariant(String p_name, String p_fallback)
		{
			this.name = p_name;
			this.fallback = p_fallback;
		}
	}

	public static final StateIconLoader instance = new StateIconLoader();
	public final IconVariant[] defaultIconVariants = new IconVariant[] { new IconVariant("off", null), new IconVariant("on", "off") };

	public FlippableIcon optionalIcon(IIconRegister ir, String name, IIcon substitute)
	{
		// if the input is an flippable IIcon find the original.
		while (substitute instanceof FlippableIcon)
		{
			substitute = ((FlippableIcon)substitute).getOriginal();
		}

		if (substitute != null)
		{
			try
			{
				ResourceLocation resLoc = new ResourceLocation( name );
				resLoc = new ResourceLocation( resLoc.getResourceDomain(), String.format( "%s/%s%s", "textures/blocks", resLoc.getResourcePath(), ".png" ) );

				final IResource res = Minecraft.getMinecraft().getResourceManager().getResource( resLoc );
				if( res != null )
				{
					return new FlippableIcon( ir.registerIcon( name ) );
				}
			}
			catch( Throwable e )
			{
				return new FlippableIcon( substitute );
			}
		}

		return new FlippableIcon( ir.registerIcon( name ) );
	}

	public FlippableIcon optionalSubIcon(IIconRegister ir, String name, IIcon substitute)
	{
		return optionalIcon(ir, name, substitute);
	}

	private FlippableIcon fallbackIcon(FlippableIcon[] baseIcons, FlippableIcon[] fallbackIcons, int fallbackOffset, int index)
	{
		if (fallbackIcons[fallbackOffset + index] != null)
		{
			return fallbackIcons[fallbackOffset + index];
		}
		return baseIcons[index];
	}

	public FlippableIcon[] registerIconsWithIconVariants(String basename, IIconRegister reg, IconVariant[] variants)
	{
		assert variants.length > 0;
		final FlippableIcon[] icons = new FlippableIcon[variants.length * 6];
		final FlippableIcon[] baseIcons = new FlippableIcon[7];
		final Map<String, Integer> variantOffset = new HashMap<String, Integer>();
		final IIcon defaultIcon = optionalIcon(reg, basename, null);
		// default side icon
		baseIcons[6] = optionalSubIcon(reg, basename + "/side", defaultIcon);
		// per side
		baseIcons[0] = optionalSubIcon(reg, basename + "/bottom", defaultIcon);
		baseIcons[1] = optionalSubIcon(reg, basename + "/top", defaultIcon);
		baseIcons[2] = optionalSubIcon(reg, basename + "/back", baseIcons[6]);
		baseIcons[3] = optionalSubIcon(reg, basename + "/front", baseIcons[6]);
		baseIcons[4] = optionalSubIcon(reg, basename + "/east", baseIcons[6]);
		baseIcons[5] = optionalSubIcon(reg, basename + "/west", baseIcons[6]);

		for (int i = 0; i < variants.length; ++i)
		{
			final IconVariant variant = variants[i];
			final int offset = i * 6;
			if (variant.name == null)
			{
				icons[offset + 0] = baseIcons[0];
				icons[offset + 1] = baseIcons[1];
				icons[offset + 2] = baseIcons[2];
				icons[offset + 3] = baseIcons[3];
				icons[offset + 4] = baseIcons[4];
				icons[offset + 5] = baseIcons[5];
			}
			else
			{
				variantOffset.put(variant.name, offset);
				final String variantName = variant.name;
				int fallbackOffset = 0;
				FlippableIcon[] fallbackSource = baseIcons;
				if (variant.fallback != null)
				{
					final Integer o = variantOffset.get(variant.fallback);
					if (o != null)
					{
						fallbackOffset = o;
						fallbackSource = icons;
					}
				}
				icons[offset + 0] = optionalSubIcon(reg, String.format("%s/bottom.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 0));
				icons[offset + 1] = optionalSubIcon(reg, String.format("%s/top.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 1));
				icons[offset + 2] = optionalSubIcon(reg, String.format("%s/back.%s", basename, variantName), optionalSubIcon(reg, String.format("%s/side.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 2)));
				icons[offset + 3] = optionalSubIcon(reg, String.format("%s/front.%s", basename, variantName), optionalSubIcon(reg, String.format("%s/side.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 3)));
				icons[offset + 4] = optionalSubIcon(reg, String.format("%s/east.%s", basename, variantName), optionalSubIcon(reg, String.format("%s/side.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 4)));
				icons[offset + 5] = optionalSubIcon(reg, String.format("%s/west.%s", basename, variantName), optionalSubIcon(reg, String.format("%s/side.%s", basename, variantName), fallbackIcon(baseIcons, fallbackSource, fallbackOffset, 5)));
			}
		}
		return icons;
	}


}
