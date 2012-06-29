package ed2k.server.misc;

import ed2k.server.data_stru.ubyte;

public class UByteCharSeq implements CharSequence{

	private final ubyte[] array;
	public UByteCharSeq(ubyte[] array) {
		super();
		this.array = array;
	}

	@Override
	public char charAt(int arg0) {
		return (char) array[arg0].intValue();
	}

	@Override
	public int length() {
		return array.length;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		ubyte[] arr = new ubyte[end-start];
		System.arraycopy(array, start, arr, 0, end-start);
		return new UByteCharSeq(arr);
	}

}
