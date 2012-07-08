package ed2k.server.data_stru;

import java.util.Arrays;

import ed2k.server.misc.Toolbox;

public class FileHash {

	private final ubyte[] id;

	public FileHash(ubyte[] id) {
		super();
		this.id = id;
	}

	public ubyte[] getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileHash other = (FileHash) obj;
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Toolbox.toStringBytes(id, true);
	}

}
