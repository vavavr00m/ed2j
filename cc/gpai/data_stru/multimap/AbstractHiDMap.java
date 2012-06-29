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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import cc.gpai.data_stru.multimap.impl.EntryImpl;


public abstract class AbstractHiDMap<K, V> extends AbstractMap<K, V> implements HiDMap<K, V> {
	protected Map<List<K>,V> map;

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
    public boolean containsKey(K... key) {
		return map.containsKey(Arrays.asList(key));
    }

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		if(key instanceof List){
			return map.get(List.class.cast(key));
		}else{
			return map.get(Arrays.asList(key));
		}
	}

	@Override
	public V put(K key, V value) {
		return put(Collections.singletonList(key), value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(Arrays.asList(key));
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		for(List<K> l:map.keySet()){
			set.addAll(l);
		}
		return set;
	}

	/**
	 * @since 2011-12-11
	 */
	@Override
    public Set<List<K>> keys() {
		Set<List<K>> set = new HashSet<List<K>>();
		for(List<K> l:map.keySet()){
			set.add(l);
		}
		return set;
    }

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
		for(List<K> l:map.keySet()){
			V val = map.get(l);
			for(K key:l){
				set.add(new EntryImpl<K, V>(key,val));
			}
		}
		return set;
	}

	@Override
	public V get(K... key) {
		return map.get(Arrays.asList(key));
	}

	@Override
	public V put(V value, K... key) {
		return put(Arrays.asList(key), value);
	}

	@Override
	public V put(K[] key, V value) {
		return put(Arrays.asList(key), value);
	}

	@Override
	public V put(List<K> key, V value) {
		return map.put(key, value);
	}

	@Override
    public String toString() {
	    return map.toString();
    }

}
