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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cc.gpai.data_stru.multimap.impl.EntryImpl;

public abstract class AbstractMultiDMap<K, V> extends AbstractDMap<K, V> implements MultiDMap<K, V> {

	protected MultiMap<K, V> forward;
	protected MultiMap<V, K> backward;
	protected int size = 0;

	protected void clearEmpty(K key, V value) {
		Collection<V> for_col = forward.getValues(key);
		if (for_col != null) {
			while (for_col.contains(value)) {
				for_col.remove(value);
			}
		}
		Collection<K> bak_col = backward.getValues(key);
		if (bak_col != null) {
			while (bak_col.contains(key)) {
				bak_col.remove(key);
			}
		}
	}

	@Override
	public V put(K key, V value) {
		boolean key_added = !forward.containsKey(key), val_added = !backward.containsKey(value);
		forward.put(key, value);
		backward.put(value, key);
		if ((key_added) || (val_added))
			size++;
		return value;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> putValues(K key, Collection<V> value) {
		for (V val : value) {
			put(key, val);
		}
		return value;
	}

	@Override
	public K getKey(Object value) {
		Collection<K> keys = getKeys(value);
		if (keys.isEmpty())
			return null;
		else
			return keys.iterator().next();
	}

	@Override
	public V get(Object key) {
		Collection<V> values = getValues(key);
		if (values.isEmpty())
			return null;
		else
			return values.iterator().next();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> getValues(Object key) {
		Collection<V> col = forward.getValues(key);
		if (col == null)
			return Arrays.asList();
		return col;
	}

	@Override
	public boolean remove(K key, V value) {
		if (containsKey(key) && containsValue(value)) {
			backward.remove(value);
			forward.remove(key);
			size--;
			clearEmpty(key, value);
			return true;
		} else {
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public V remove(Object key) {
		Collection<V> values = getValues(key);
		for (V val : values) {
			backward.remove(val);
			size--;
			clearEmpty((K) key, val);
		}
		return forward.remove(key);
	}
	
	@Override
	public boolean removeKey(K key) {
		return remove(key)!=null;
	}

	@Override
	public boolean removeValue(V value) {
		return !removeAllByValue(value).isEmpty();
	}

	@Override
	public Collection<K> removeAllByValue(V val) {
		Collection<K> keys = backward.getValues(val);
		backward.remove(val);
		for (K key : keys) {
			forward.remove(key);
			size--;
			clearEmpty(key, val);
		}
		return keys;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
		for (K key : keySet())
			for (V value : getValues(key))
				set.add(new EntryImpl<K, V>(key, value));
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<K> getKeys(Object value) {
		Collection<K> col = backward.getValues(value);
		if (col == null)
			return Arrays.asList();
		return col;
	}

	@Override
	public void clear() {
		forward.clear();
		backward.clear();
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		return forward.keySet();
	}

	@Override
	public Collection<V> values() {
		return backward.keySet();
	}

}
