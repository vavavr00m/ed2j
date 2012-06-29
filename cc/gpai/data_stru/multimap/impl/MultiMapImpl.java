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

import cc.gpai.data_stru.multimap.AbstractMultiMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class MultiMapImpl<K, V> extends AbstractMultiMap<K, V> {

	protected Class<? extends Collection<V>> col_clz;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MultiMapImpl(Class<? extends Map> clazz,Class<? extends Collection<V>> col_clz){
		try {
			map = clazz.newInstance();
                        if(col_clz==null){
                            col_clz = (Class<? extends Collection<V>>) ((Collection<V>)new java.util.ArrayList<V>()).getClass();
                            this.col_clz=col_clz;
                        }else{
                            this.col_clz=col_clz;
                            col_clz.newInstance();
                        }
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<V> newCollection() {
		try {
			return col_clz.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return Arrays.asList();
	}

}
