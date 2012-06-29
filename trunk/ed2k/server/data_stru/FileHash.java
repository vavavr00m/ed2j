package ed2k.server.data_stru;

import java.util.Arrays;

public class FileHash{

	private final ubyte[] id;
	private final transient int hash;
	private transient String str = null;

	public FileHash(ubyte[] id) {
		super();
		this.id = id;
		hash = Arrays.hashCode(id);
	}

	public ubyte[] getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return hash;
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
		if(str==null){
			StringBuilder sb = new StringBuilder();
			for(ubyte u:id){
				sb.append(u.toHex());
			}
			str = sb.toString();
		}
		return str;
	}
	
}
