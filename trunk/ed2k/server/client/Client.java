package ed2k.server.client;

import ed2k.server.data_stru.SourceItem;
import ed2k.server.data_stru.UserHash;

public class Client {

	private UserHash user_hash;
	private SourceItem source;
	public Client(UserHash user_hash, SourceItem source) {
		super();
		this.user_hash = user_hash;
		this.source = source;
	}

	public UserHash getUserHash() {
		return user_hash;
	}

	public SourceItem getSource() {
		return source;
	}

	public void setUserHash(UserHash userHash) {
		this.user_hash = userHash;
	}

	public void setSource(SourceItem source) {
		this.source = source;
	}

	public int hashCode() {
		return user_hash.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (user_hash == null) {
			if (other.user_hash != null)
				return false;
		} else if (!user_hash.equals(other.user_hash))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [");
		if (user_hash != null) {
			builder.append("user_hash=");
			builder.append(user_hash);
			builder.append(", ");
		}
		if (source != null) {
			builder.append("source=");
			builder.append(source);
		}
		builder.append("]");
		return builder.toString();
	}
}
