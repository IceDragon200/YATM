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
package id2h.yatm.client.renderer;

import com.google.common.collect.ObjectArrays;

import growthcraft.api.core.util.BBox;

public class BoxModels
{
	public static final float SCALE = 1f / 16f;

	public static final BBox[] cagedMachine1 = {
		// Core
		BBox.newCube(1, 1, 1, 14, 14, 14).scale(SCALE),
		// Vertical Rails
		BBox.newCube(0, 0, 0, 1, 16, 1).scale(SCALE),
		BBox.newCube(0, 0, 15, 1, 16, 1).scale(SCALE),
		BBox.newCube(15, 0, 15, 1, 16, 1).scale(SCALE),
		BBox.newCube(15, 0, 0, 1, 16, 1).scale(SCALE),
		// Horizontal Rails
		BBox.newCube(1, 0, 0, 14, 1, 1).scale(SCALE),
		BBox.newCube(1, 0, 15, 14, 1, 1).scale(SCALE),
		BBox.newCube(0, 0, 1, 1, 1, 14).scale(SCALE),
		BBox.newCube(15, 0, 1, 1, 1, 14).scale(SCALE),
		BBox.newCube(1, 15, 0, 14, 1, 1).scale(SCALE),
		BBox.newCube(1, 15, 15, 14, 1, 1).scale(SCALE),
		BBox.newCube(0, 15, 1, 1, 1, 14).scale(SCALE),
		BBox.newCube(15, 15, 1, 1, 1, 14).scale(SCALE)
	};

	public static final BBox[] cagedMachine2 = {
		// Core
		BBox.newCube(2, 2, 2, 12, 12, 12).scale(SCALE),
		// Vertical Rails
		BBox.newCube(0, 0, 0, 2, 16, 2).scale(SCALE),
		BBox.newCube(0, 0, 14, 2, 16, 2).scale(SCALE),
		BBox.newCube(14, 0, 14, 2, 16, 2).scale(SCALE),
		BBox.newCube(14, 0, 0, 2, 16, 2).scale(SCALE),
		// Horizontal Rails
		BBox.newCube(2, 0, 0, 12, 2, 2).scale(SCALE),
		BBox.newCube(2, 0, 14, 12, 2, 2).scale(SCALE),
		BBox.newCube(0, 0, 2, 2, 2, 12).scale(SCALE),
		BBox.newCube(14, 0, 2, 2, 2, 12).scale(SCALE),
		BBox.newCube(2, 14, 0, 12, 2, 2).scale(SCALE),
		BBox.newCube(2, 14, 14, 12, 2, 2).scale(SCALE),
		BBox.newCube(0, 14, 2, 2, 2, 12).scale(SCALE),
		BBox.newCube(14, 14, 2, 2, 2, 12).scale(SCALE)
	};

	public static final BBox[] compactor = ObjectArrays.concat(cagedMachine2, new BBox[]{
		// Top stubs
		/// Center Stub
		BBox.newCube(0, 0, 0, 8, 2, 8).translate(4, 14, 4).scale(SCALE),
		/// Corner Stubs
		BBox.newCube(0, 0, 0, 4, 1, 4).translate(1, 14, 1).scale(SCALE),
		BBox.newCube(0, 0, 0, 4, 1, 4).translate(1, 14, 11).scale(SCALE),
		BBox.newCube(0, 0, 0, 4, 1, 4).translate(11, 14, 1).scale(SCALE),
		BBox.newCube(0, 0, 0, 4, 1, 4).translate(11, 14, 11).scale(SCALE)
	}, BBox.class);
}
