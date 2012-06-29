package ed2k.server.message;

import ed2k.server.data_stru.ubyte;

public interface MessageProcessor {
	public void processMessage(ubyte[] data);
}
