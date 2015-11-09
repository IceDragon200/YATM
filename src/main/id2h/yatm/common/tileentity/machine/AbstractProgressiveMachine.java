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
package id2h.yatm.common.tileentity.machine;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractProgressiveMachine extends AbstractMachine implements IProgressiveMachine
{
	protected float progress;
	protected float progressMax;

	@Override
	public boolean canWork(MachineUpdateState state)
	{
		return progressMax > 0;
	}

	protected void resetProgress()
	{
		this.progress = 0.0f;
		this.progressMax = 0.0f;
	}

	@Override
	protected void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		this.progress = (float)data.getDouble("progress");
		this.progressMax = (float)data.getDouble("progressMax");
	}

	@Override
	protected void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setDouble("progress", (double)progress);
		data.setDouble("progressMax", (double)progressMax);
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		this.progress = stream.readFloat();
		this.progressMax = stream.readFloat();
		return false;
	}

	@Override
	public void writeToStream(ByteBuf stream)
	{
		stream.writeFloat(progress);
		stream.writeFloat(progressMax);
	}

	@Override
	public float getProgress()
	{
		return progress;
	}

	@Override
	public float getProgressMax()
	{
		return progressMax;
	}

	@Override
	public void setProgress(float value)
	{
		progress = value;
	}

	@Override
	public void setProgressMax(float value)
	{
		this.progressMax = value;
	}

	@Override
	public float getProgressRate()
	{
		if (progressMax <= 0) return 0.0f;
		return progress / progressMax;
	}
}
