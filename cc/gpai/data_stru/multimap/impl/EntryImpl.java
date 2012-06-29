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

import java.util.Map.Entry;

public class EntryImpl<K, V> implements Entry<K, V>,Cloneable {

	private K key;
	private V value;

	public EntryImpl(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		EntryImpl other = (EntryImpl) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntryImpl [key=" + String.valueOf(key) + ", value=" + String.valueOf(value) + "]";
	}

	@Override
	protected EntryImpl<K, V> clone(){
		return new EntryImpl<K, V>(key,value);
	}
	
}