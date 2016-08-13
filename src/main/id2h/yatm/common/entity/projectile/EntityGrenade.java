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
package id2h.yatm.common.entity.projectile;

import id2h.yatm.common.item.EnumGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable
{
	private EnumGrenade grenadeType = EnumGrenade.DUMMY;

	public EntityGrenade(World world)
	{
		super(world);
	}

	public EntityGrenade(World world, EntityLivingBase entity)
	{
		super(world, entity);
	}

	public EntityGrenade(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	public EnumGrenade getGrenadeType()
	{
		return grenadeType;
	}

	public EntityGrenade setGrenadeType(EnumGrenade type)
	{
		this.grenadeType = type;
		return this;
	}

	@Override
	protected void onImpact(MovingObjectPosition pos)
	{
		if (pos.entityHit != null)
		{
			pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}

		if (!worldObj.isRemote)
		{
			switch (grenadeType)
			{
				case DUMMY:
					// Nothing happens
					break;
				case FRAGMENTATION:
					// grenade should explode, with smoke, flameless and causes minor terrain deformation
					worldObj.newExplosion(this, posX, posY, posZ, 2.0f, false, true);
					break;
				case CONCUSSION:
					// grenade should explode, smokeless and flameless
					worldObj.newExplosion(this, posX, posY, posZ, 2.0f, false, false);
					break;
				case INCENDIARY:
					// grenade should explode, with fire only
					worldObj.newExplosion(this, posX, posY, posZ, 2.0f, true, false);
					break;
				case STUN:
					// @todo
					break;
				case FLASH_BANG:
					// @todo
					break;
				case EMP:
					// @todo
					break;
				case GRAVITY:
					// @todo
					break;
				case ANTI_GRAVITY:
					// @todo
					break;
				case AIR:
					// @todo
					break;
				case SUCTION:
					// @todo
					break;
				case BLACK_HOLE:
					// @todo
					break;
				case AQUIFER:
					// @todo
					break;
				default:
			}
			setDead();
		}
	}
}
