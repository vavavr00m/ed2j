package ed2k.server.encoder.impl;

import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.UserHash;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;

public class Hello implements Encoder {

	public Hello() {
	}

	@Override
	public Map<String, Object> decode(ubyte[] b) {
		Map<String, Object> map = new TreeMap<String, Object>();

		ubyte[] user_hash = new ubyte[16];
		System.arraycopy(b, 7, user_hash, 0, 16);
		ubyte[] client_id = new ubyte[4];
		System.arraycopy(b, 23, client_id, 0, 4);
		ubyte[] client_port = new ubyte[2];
		System.arraycopy(b, 27, client_port, 0, 2);

		ubyte[] server_port = new ubyte[2];
		System.arraycopy(b, b.length - 2, server_port, 0, 2);
		ubyte[] server_ip = new ubyte[8];
		System.arraycopy(b, b.length - 6, server_ip, 0, 4);
		//
		// int len = b.length-46;
		// byte[]name_tag = new byte[len];
		// System.arraycopy(b, 38, name_tag, 0, len);
		// String nickname = new String(name_tag).trim();
		// nickname = nickname.substring(0, nickname.length()-3);
		// map.put("Nickname", nickname);
		map.put("UserHash", new UserHash(user_hash));
		map.put("Client ID", client_id);
		map.put("Client Port", client_port);
		map.put("Server Port", server_port);
		map.put("isHello", Boolean.TRUE);
		return map;
	}

	@Override
	public ubyte[] encode(Map<String, Object> map) {
		int size = 34;
		ubyte[] data = new ubyte[size + 5];
		data[0] = ubyte.valueOf(0xE3);
		data[1] = ubyte.valueOf(size);
		data[5] = ubyte.valueOf(1);
		data[6] = ubyte.valueOf(16);
		UserHash uh = (UserHash) map.get("UserHash");
		System.arraycopy(uh.getBytes(), 0, data, 7, 16);
		System.arraycopy(map.get("Client ID"), 0, data, 23, 4);
		System.arraycopy(map.get("Client Port"), 0, data, 27, 2);
		System.arraycopy(map.get("Server IP"), 0, data, 33, 4);
		System.arraycopy(map.get("Server Port"), 0, data, 37, 2);
		return data;
	}

}
