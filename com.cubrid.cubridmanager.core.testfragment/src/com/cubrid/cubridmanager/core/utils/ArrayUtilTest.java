/*
 * Copyright (C) 2013 Search Solution Corporation. All rights reserved by Search Solution. 
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer. 
 *
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution. 
 *
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE. 
 *
 */
package com.cubrid.cubridmanager.core.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.cubrid.common.core.util.ArrayUtil;

/**
 * ArrayUtilTest Description
 * 
 * @author Kevin.Wang
 * @version 1.0 - 2013-6-5 created by Kevin.Wang
 */
public class ArrayUtilTest extends TestCase{
	public void testArrayUtil() {
		assertTrue(ArrayUtil.isEmpty(null));
		
		List<String> list = new ArrayList<String>();
		assertTrue(ArrayUtil.isEmpty(list));
		
		list.add("a");
		assertFalse(ArrayUtil.isEmpty(list));

		//test collectionToCsString()
		List<String> list2 = new ArrayList<String>();
		list2.add("Broker");
		list2.add("Host");
		String str = ArrayUtil.collectionToCSString(list2);
		assertTrue(str.indexOf("Broker") > -1);
		assertTrue(str.indexOf("Host") > -1);
		assertTrue(str.indexOf(",") > -1);
	}
}