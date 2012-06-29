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
import java.util.Collection;
import java.util.Map;


public interface MultiMap<K, V> extends Map<K, V> {
	
	public Collection<V> putValues(K key, Collection<V> value);
	
	public Collection<V> getValues(Object key);
	
	public boolean remove(K key, V value);
	
	public boolean removeKey(K key);
	
	public boolean removeValue(V value);
	
}
