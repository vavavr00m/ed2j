package ed2k.server.encoder.impl;

import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;

public class IDchange implements Encoder {

	public IDchange() {
	}

	@Override
	public Map<String, Object> decode(ubyte[] b) {
		Map<String, Object> map = new TreeMap<String, Object>();
		ubyte[] client_id = new ubyte[4];
		System.arraycopy(b, 6, client_id, 0, 4);
		map.put("Client ID", client_id);
		return map;
	}

	@Override
	public ubyte[] encode(Map<String, Object> map) {
		ubyte[] data = ubyte.newArray(10);
		data[0] = ubyte.valueOf(0xE3);
		data[1] = ubyte.valueOf(5);
		data[5] = ubyte.valueOf(0x40);
		ubyte[] id = (ubyte[]) map.get("Client ID");
		System.arraycopy(id, 0, data, 6, 4);
		return data;
	}

}
