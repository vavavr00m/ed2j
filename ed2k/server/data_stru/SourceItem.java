package ed2k.server.data_stru;

import java.util.Arrays;

import ed2k.server.misc.Toolbox;

public class SourceItem {
	public final ubyte[] client_id;
	public final ubyte[] client_port;

	public SourceItem(ubyte[] client_id, ubyte[] client_port) {
		super();
		this.client_id = client_id;
		this.client_port = client_port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(client_id);
		result = prime * result + Arrays.hashCode(client_port);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceItem other = (SourceItem) obj;
		if (!Arrays.equals(client_id, other.client_id))
			return false;
		if (!Arrays.equals(client_port, other.client_port))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (client_id != null) {
			builder.append(Toolbox.toDemicalString(client_id));
		}
		builder.append(':');
		if (client_port != null) {
			builder.append(Toolbox.byte2Integer(client_port));
		}
		return builder.toString();
	}
}