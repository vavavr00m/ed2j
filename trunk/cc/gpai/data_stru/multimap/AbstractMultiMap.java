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
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cc.gpai.data_stru.multimap.impl.EntryImpl;

public abstract class AbstractMultiMap<K, V> extends AbstractMap<K, V> implements MultiMap<K, V> {

	protected Map<K, Collection<V>> map;

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
	public boolean containsValue(Object value) {
		for (Collection<V> c : map.values()) {
			if (c.contains(value))
				return true;
		}
		return false;
	}

	@Override
	public V get(Object key) {
		Collection<V> values = getValues(key);
		if (values.isEmpty())
			return null;
		else
			return values.iterator().next();
	}

	@Override
	public Collection<V> getValues(Object key) {
		Collection<V> col = map.get(key);
		if(col==null)return newCollection();
		return col;
	}

	private void ensureKey(K key){
		if(!map.containsKey(key))
			map.put(key, newCollection());
	}
	@Override
	public Collection<V> putValues(K key, Collection<V> value) {
		Collection<V> v = getValues(key);
		ensureKey(key);
		map.get(key).addAll(value);
		return v;
	}

	@Override
	public V put(K key, V value) {
		V v = get(key);
		ensureKey(key);
		map.get(key).add(value);
		return v;
	}
	protected abstract Collection<V> newCollection();

	@Override
	public V remove(Object key) {
		V v = get(key);
		map.remove(key);
		return v;
	}

	@Override
	public boolean remove(K key, V value) {
		return getValues(key).remove(value);
	}

	@Override
	public boolean removeKey(K key) {
		boolean removed = false;
		if(map.containsKey(key)){
			Collection<V> col = map.get(key);
			if(col!=null){
				removed = !col.isEmpty();
				col.clear();
			}
			map.remove(key);
		}
		return removed;
	}

	@Override
	public boolean removeValue(V value) {
		if(value==null){
			return false;
		}
		boolean removed = false;
		for(Collection<V> col:map.values()){
			while(col.contains(value)){
				col.remove(value);
				removed = true;
			}
		}
		return removed;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
		Set<Entry<K, Collection<V>>> es = map.entrySet();
		for (Entry<K, Collection<V>> entry : es) {
			K key = entry.getKey();
			Collection<V> values = entry.getValue();
			for (V value : values) {
				set.add(new EntryImpl<K, V>(key, value));
			}
		}
		return set;
	}
}
