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
package id2h.yatm.api.core.wireless;

public enum EnumWirelessCode
{
	/**
	 * No Operation
	 */
	NOOP(0, "noop"),
	/**
	 * Generic HEAD
	 * @param variable_data
	 */
	HEAD(1, "head"),
	/**
	 * Generic GET
	 * @param variable_data
	 */
	GET(2, "get"),
	/**
	 * Generic POST
	 * @param variable_data
	 */
	POST(3, "post"),
	/**
	 * Generic PUT
	 * @param variable_data
	 */
	PUT(4, "put"),
	/**
	 * Generic PATCH
	 * @param variable_data
	 */
	PATCH(5, "patch"),
	/**
	 * Generic DELETE
	 * @param variable_data
	 */
	DELETE(6, "delete"),
	/**
	 * Connection Request
	 * @param int: responseFrequency
	 */
	CONNECT(10, "connect"),
	/**
	 * Connection Response
	 * @param int: status code - 200 OK, 403 Forbidden
	 */
	CONNECTED(11, "connected"),
	/**
	 * Disconnection Request
	 * @param int: responseFrequency
	 */
	DISCONNECT(20, "disconnect"),
	/**
	 * Disconnection Response
	 * @param int: status code - 200 OK, 403 Forbidden
	 */
	DISCONNECTED(21, "disconnected"),
	/**
	 * Ping Request
	 * @param int: responseFrequency
	 */
	PING(100, "ping"),
	/**
	 * Ping Response
	 */
	PONG(101, "pong"),
	/**
	 * Identity Request
	 * @param int: responseFrequency
	 */
	IDENTIFY(1000, "identify"),
	/**
	 * Identity Response
	 * @param variable_data
	 */
	IDENTITY(1001, "Identity");

	public final int id;
	public final String name;

	private EnumWirelessCode(int p_id, String p_name)
	{
		this.id = p_id;
		this.name = p_name;
	}
}
