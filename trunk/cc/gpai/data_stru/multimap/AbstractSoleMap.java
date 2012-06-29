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

public abstract class AbstractSoleMap<K, V> extends AbstractMap<K, V> implements SoleMap<K, V> {

	protected Mode mode = Mode.REPLACE;

	public V put(K key, V value) {
		if (containsValue(value)) {
			if (mode.equals(Mode.EXCEPTION)) {
				throw new IllegalArgumentException("Value " + value + " already exists.");
			} else if (mode.equals(Mode.REPLACE)) {
				for (Entry<K, V> e : entrySet()) {
					if (e.getValue().equals(value)) {
						remove(e.getKey());
						break;
					}
				}
			}
		}
		return doPut(key, value);
	}

	protected abstract V doPut(K key, V value);

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return super.toString() + " [mode=" + mode + "]";
	}

}
