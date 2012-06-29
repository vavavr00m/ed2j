/*
 *  Copyright 2011 David Zhang (nybbs2003@163.com)
 * 
 *  This file is part of MultiMap.
 *
 *  MultiMap is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 3 only 
 *  as published by the Free Software Foundation.
 *
 *  MultiMap is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with MultiMap.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.gpai.data_stru.multimap.impl;

import java.util.Map;

import cc.gpai.data_stru.multimap.AbstractHiDMap;

public class HiDMapImpl<K, V> extends AbstractHiDMap<K, V> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HiDMapImpl(Class<? extends Map> clazz){
		try {
			map = clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
