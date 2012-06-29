package ed2k.server.encoder.impl;

import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.UserHash;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;

public class HelloAnswer implements Encoder {

	@Override
	public Map<String,Object> decode(ubyte[] b){
		Map<String,Object> map = new TreeMap<String,Object>();
		
		ubyte[] user_hash = ubyte.newArray(16);
		ubyte[] client_id = ubyte.newArray(4);
		ubyte[] client_port = new ubyte[2];
		ubyte[] server_port = ubyte.newArray(2);
		ubyte[] server_ip = new ubyte[8];
		try{
                    System.arraycopy(b, 6, user_hash, 0, 16);
                    System.arraycopy(b, 22, client_id, 0, 4);
                    System.arraycopy(b, 26, client_port, 0, 2);

                    System.arraycopy(b, b.length-2, server_port, 0, 2);
                    System.arraycopy(b, b.length-6, server_ip, 0, 4);
                }catch(Throwable t){
                    
                }
		map.put("UserHash", new UserHash(user_hash));
		map.put("Client ID", client_id);
		map.put("Server Port", server_port);
		
		return map;
	}

	@Override
	public ubyte[] encode(Map<String,Object> map) {
		int size = 35;
		ubyte[] data = new ubyte[size+5];
		data[0] = new ubyte(0xE3);
		data[1] = new ubyte(size);
		data[5]=new ubyte(1);
		UserHash uh = (UserHash) map.get("UserHash");
		System.arraycopy((ubyte[])uh.getBytes(), 0, data, 6, 16);
		System.arraycopy((ubyte[])map.get("Client ID"), 0, data, 22, 4);
		System.arraycopy((ubyte[])map.get("Client Port"), 0, data, 26, 2);
		System.arraycopy((ubyte[])map.get("Server IP"), 0, data, 32, 4);
		System.arraycopy((ubyte[])map.get("Server Port"), 0, data, 36, 2);
		return data;
	}

}
