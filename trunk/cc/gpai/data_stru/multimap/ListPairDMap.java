package cc.gpai.data_stru.multimap;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.gpai.data_stru.multimap.impl.EntryImpl;

public abstract class ListPairDMap<K, V> extends AbstractMap<K, V> implements DMap<K, V> {

	protected final List<Entry<K, V>> list = createList();

	protected abstract List<Entry<K, V>> createList();

	protected Entry<K, V> getEntryByKey(K key) {
		for (Entry<K, V> e : list) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	protected Entry<K, V> getEntryByVal(V val) {
		for (Entry<K, V> e : list) {
			if (e.getValue().equals(val)) {
				return e;
			}
		}
		return null;
	}

	protected int indexOf(V val) {
		int i = 0;
		for (Entry<K, V> e : list) {
			if (e.getValue().equals(val)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public V put(K key, V value) {
		Entry<K, V> e = getEntryByKey(key);
		if (e == null) {
			list.add(new EntryImpl<K, V>(key, value));
			return null;
		} else {
			return e.setValue(value);
		}
	}

	@Override
	public K getKey(Object value) {
		@SuppressWarnings("unchecked")
		Entry<K, V> e = getEntryByVal((V) value);
		return e == null ? null : e.getKey();
	}

	@Override
	public K removeByValue(V val) {
		int i = indexOf(val);
		if (i > -1) {
			return list.remove(i).getKey();
		}
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {

			@Override
			public Iterator<Entry<K, V>> iterator() {
				return list.iterator();
			}

			@Override
			public int size() {
				return list.size();
			}
		};
	}
}
