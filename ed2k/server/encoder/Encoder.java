package ed2k.server.encoder;

import java.util.Map;

import ed2k.server.data_stru.ubyte;

public interface Encoder {
	
	public Map<String,Object> decode(ubyte[] b);
	
	public ubyte[] encode(Map<String,Object> m);

}
