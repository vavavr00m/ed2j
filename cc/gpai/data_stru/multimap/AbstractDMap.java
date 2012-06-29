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

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * 双向映射的抽象实现
 * 
 * @author 张达林
 * @since 2011-4-21
 * 
 * @param <K>
 * @param <V>
 */
public abstract class AbstractDMap<K, V>  extends AbstractMap<K, V> implements DMap<K, V> {

	/**
	 * forward map
	 */
	protected Map<K, V> forward;
	/**
	 * backward map
	 */
	protected Map<V, K> backward;

	@Override
	public int size() {
		return forward.size();
	}

	@Override
	public boolean isEmpty() {
		return forward.isEmpty() && backward.isEmpty();
	}

	@Override
	public void clear() {
		forward.clear();
		backward.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return forward.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return backward.containsKey(value);
	}

	@Override
	public V get(Object key) {
		return forward.get(key);
	}

	@Override
	public V put(K key, V value) {
		V v = forward.put(key, value);
		backward.put(value, key);
		return v;
	}

	@Override
	public V remove(Object key) {
		V v = forward.remove(key);
		backward.remove(v);
		return v;
	}

	@Override
	public K removeByValue(V val) {
		K k = backward.remove(val);
		forward.remove(k);
		return k;
	}


	@Override
	public Set<Entry<K, V>> entrySet() {
		return forward.entrySet();
	}

	@Override
	public K getKey(Object value) {
		return backward.get(value);
	}

}
