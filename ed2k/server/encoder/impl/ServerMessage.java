package ed2k.server.encoder.impl;

import java.util.Map;

import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;
import ed2k.server.misc.Toolbox;

public class ServerMessage implements Encoder {
	public ServerMessage() {
	}

	@Override
	public Map<String, Object> decode(ubyte[] b) {
		return null;
	}

	@Override
	public ubyte[] encode(Map<String, Object> m) {
		byte[] msg = m.get("message").toString().getBytes();
		ubyte[] data = ubyte.newArray(msg.length + 8);
		data[0] = ubyte.valueOf(0xe3);
		ubyte[] size = ubyte.parseOneDArray(Toolbox.int2Bytes(msg.length + 3));
		for (int i = 0; i < size.length; i++) {
			data[i + 1] = size[i];
		}
		data[5] = ubyte.valueOf(0x38);
		size = ubyte.parseOneDArray(Toolbox.int2Bytes(msg.length));
		for (int i = 0; i < size.length; i++) {
			data[i + 6] = size[i];
		}
		ubyte[] msg1 = ubyte.parseOneDArray(msg);
		for (int i = 0; i < 4; i++) {
			data[i + 8] = msg1[i];
		}
		return data;
	}

}
