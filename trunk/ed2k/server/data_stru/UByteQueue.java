package ed2k.server.data_stru;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class UByteQueue implements Queue<ubyte> {

	private final LinkedList<ubyte> l = new LinkedList<ubyte>();

	public UByteQueue() {
	}

	public UByteQueue(ubyte[] array) {
		addAll(Arrays.asList(array));
	}

	public boolean add(int i) {
		return l.add(ubyte.valueOf(i));
	}

	public ubyte[] pollSome(int size) {
		ubyte[] array = new ubyte[size];
		for (int i = 0; i < size; i++) {
			array[i] = poll();
			if (array[i] == null) {
				array[i] = ubyte.valueOf(0);
			}
		}
		return array;
	}

	public void pushSome(ubyte... data) {
		for (ubyte u : data) {
			l.add(u);
		}
	}

	public byte[] toByteArray() {
		byte[] b = new byte[size()];
		for (int i = 0; i < l.size(); i++) {
			b[i] = l.get(i).byteValue();
		}
		return b;
	}

	@Override
	public String toString() {
		return new String(toByteArray());
	}

	@Override
	public boolean add(ubyte e) {
		return l.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends ubyte> c) {
		return l.addAll(c);
	}

	@Override
	public void clear() {
		l.clear();
	}

	@Override
	public boolean contains(Object o) {
		return l.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return l.containsAll(c);
	}

	@Override
	public ubyte element() {
		return l.element();
	}

	@Override
	public boolean isEmpty() {
		return l.isEmpty();
	}

	@Override
	public Iterator<ubyte> iterator() {
		return l.iterator();
	}

	@Override
	public boolean offer(ubyte e) {
		return l.offer(e);
	}

	@Override
	public ubyte peek() {
		return l.peek();
	}

	@Override
	public ubyte poll() {
		return l.poll();
	}

	@Override
	public ubyte remove() {
		return l.remove();
	}

	@Override
	public boolean remove(Object o) {
		return l.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return l.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return l.retainAll(c);
	}

	@Override
	public int size() {
		return l.size();
	}

	@Override
	public Object[] toArray() {
		return l.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return l.toArray(a);
	}
}
