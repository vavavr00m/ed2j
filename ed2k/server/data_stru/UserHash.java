package ed2k.server.data_stru;

import java.util.Arrays;

import ed2k.server.misc.Toolbox;

public class UserHash {

	private final ubyte[] b;

	public UserHash(ubyte[] b) {
		this.b = b;
	}

	public static UserHash parseUserID(String str) {
		ubyte[] b = new ubyte[16];
		for (int i = 0; i < 8; i++) {
			String s = str.substring(i * 2, i * 2 + 1);
			b[i] = ubyte.valueOf(s, false);
		}
		return new UserHash(b);
	}

	public static UserHash generate() {
		ubyte[] ub = new ubyte[16];
		for (int i = 0; i < 16; i++) {
			ub[i] = ubyte.rand();
		}
		ub[5] = ubyte.valueOf(14);
		ub[14] = ubyte.valueOf(111);
		return new UserHash(ub);
	}

	public ubyte[] getBytes() {
		return b;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(b);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserHash other = (UserHash) obj;
		if (!Arrays.equals(b, other.b))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Toolbox.toStringBytes(b, true);
	}
}
