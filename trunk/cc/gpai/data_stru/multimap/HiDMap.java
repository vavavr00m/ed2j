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
package cc.gpai.data_stru.multimap;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 高维映射，可以使用多个对象的有序集合作为索引。
 * @author 张达林
 * @since 2011-5-18
 *
 * @param <K>
 * @param <V>
 */
public interface HiDMap<K, V> extends Map<K, V> {
	public boolean containsKey(K... key);
	public V get(K... key);
	public V put(V value,K... key);
	public V put(K[] key,V value);
	public V put(List<K> key ,V value);
	public Set<List<K>> keys();
}
