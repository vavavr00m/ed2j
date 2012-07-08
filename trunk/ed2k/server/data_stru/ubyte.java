package ed2k.server.data_stru;

import static ed2k.server.misc.Toolbox.hex2int;
import static ed2k.server.misc.Toolbox.int2hex;

public class ubyte extends Number implements Comparable<ubyte> {

	private static final ubyte[] VALUE = new ubyte[256];
	/**
	 * 
	 */
	private static final long serialVersionUID = -8932816268190356866L;
	private final byte value;

	private ubyte(int val) {
		if (val > 127)
			val -= 256;
		value = (byte) val;
	}

	public static ubyte rand() {
		return ubyte.valueOf((int) (Math.random() * 256));
	}

	public static ubyte valueOf(int value) {
		int val = value % 256;
		if (val < 0) {
			val += 256;
		}
		if (VALUE[val] == null) {
			VALUE[val] = new ubyte((byte) val);
		}
		return VALUE[val];
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ubyte))
			return false;
		ubyte other = (ubyte) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return toHex(false);
	}

	@Override
	public byte byteValue() {
		return value;
	}

	@Override
	public int intValue() {
		return value < 0 ? value + 256 : value;
	}

	@Override
	public long longValue() {
		return intValue();
	}

	@Override
	public float floatValue() {
		return intValue();
	}

	@Override
	public double doubleValue() {
		return intValue();
	}

	@Override
	public int compareTo(ubyte o) {
		return intValue() - o.intValue();
	}

	public String toHex(boolean bigEndian) {
		int i = intValue();
		int hi = i / 16;
		int lo = i % 16;
		if (bigEndian) {
			return new String(new char[] { int2hex(hi), int2hex(lo) });
		} else {
			return new String(new char[] { int2hex(lo), int2hex(hi) });
		}
	}

	public static ubyte valueOf(String str, boolean bigEndian) {
		if (bigEndian) {
			return ubyte.valueOf(hex2int(str.charAt(0)) * 16 + hex2int(str.charAt(1)));
		} else {
			return ubyte.valueOf(hex2int(str.charAt(1)) * 16 + hex2int(str.charAt(0)));
		}
	}

	public static ubyte[] parseOneDArray(byte[] b) {
		ubyte[] data = new ubyte[b.length];
		for (int i = 0; i < b.length; i++) {
			data[i] = ubyte.valueOf(b[i]);
		}
		return data;
	}

	public static ubyte[] newArray(int size) {
		ubyte[] array = new ubyte[size];
		for (int i = 0; i < size; i++) {
			array[i] = ubyte.valueOf(0);
		}
		return array;
	}

	public static byte[] toBytes(ubyte[] ub) {
		byte[] data = new byte[ub.length];
		for (int i = 0; i < ub.length; i++) {
			data[i] = ub[i] == null ? 0 : ub[i].value;
		}
		return data;
	}
}