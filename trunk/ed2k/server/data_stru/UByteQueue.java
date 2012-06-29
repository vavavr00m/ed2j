package ed2k.server.data_stru;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class UByteQueue implements Queue<ubyte> {

	private Queue<ubyte> l = new LinkedList<ubyte>();

	public UByteQueue() {
	}
	public UByteQueue(ubyte[] array) {
		addAll(Arrays.asList(array));
	}

	public ubyte[] pollSome(int size){
		ubyte[] array = new ubyte[size];
		for(int i=0;i<size;i++){
			array[i]=poll();
			if(array[i]==null){
				array[i] = new ubyte(0);
			}
		}
		return array;
	}
	
	public boolean add(ubyte e) {
		return l.add(e);
	}

	public boolean addAll(Collection<? extends ubyte> c) {
		return l.addAll(c);
	}

	public void clear() {
		l.clear();
	}

	public boolean contains(Object o) {
		return l.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return l.containsAll(c);
	}

	public ubyte element() {
		return l.element();
	}

	public boolean isEmpty() {
		return l.isEmpty();
	}

	public Iterator<ubyte> iterator() {
		return l.iterator();
	}

	public boolean offer(ubyte e) {
		return l.offer(e);
	}

	public ubyte peek() {
		return l.peek();
	}

	public ubyte poll() {
		return l.poll();
	}

	public ubyte remove() {
		return l.remove();
	}

	public boolean remove(Object o) {
		return l.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return l.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return l.retainAll(c);
	}

	public int size() {
		return l.size();
	}

	public Object[] toArray() {
		return l.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return l.toArray(a);
	}}
