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

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.gpai.data_stru.multimap.DMap;

public class ArrayDMap<K, V> extends AbstractMap<K, V> implements DMap<K, V> {

	protected List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();

	public ArrayDMap() {

	}

	public ArrayDMap(List<Entry<K, V>> list) {
	    super();
	    this.list = list;
    }

	@Override
	public V put(K key, V value) {
		for (Entry<K, V> e : list)
			if (e.getKey().equals(key))
				return e.setValue(value);
		list.add(new EntryImpl<K, V>(key, value));
		return null;
	}

	@Override
	public K removeByValue(V val) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		Entry<K, V> correctEntry = null;
		if (val == null) {
			while (correctEntry == null && i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getValue() == null) {
					correctEntry = e;
					break;
				}
			}
		} else {
			while (correctEntry == null && i.hasNext()) {
				Entry<K, V> e = i.next();
				if (val.equals(e.getValue())) {
					correctEntry = e;
					break;
				}
			}
		}

		K oldKey = null;
		if (correctEntry != null) {
			oldKey = correctEntry.getKey();
			i.remove();
		}
		return oldKey;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {

			@Override
			public Iterator<Entry<K, V>> iterator() {
				return list.listIterator();
			}

			@Override
			public int size() {
				return list.size();
			}
		};
	}

	@Override
	public K getKey(Object value) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (value == null) {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getKey() == null)
					return e.getKey();
			}
		} else {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (value.equals(e.getKey()))
					return e.getKey();
			}
		}
		return null;
	}
}
