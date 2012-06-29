package ed2k.server.encoder.impl;

import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;
import ed2k.server.misc.Toolbox;

public class GeneralDecoder implements Encoder {

	@Override
	public Map<String, Object> decode(ubyte[] b) {
//		ubyte protocol = b[0];
		ubyte[] u_size = new ubyte[4];
		System.arraycopy(b, 1, u_size, 0, 4);
		int size = Toolbox.byte2Integer(u_size);
		if(b.length<size+5){
			throw new IllegalArgumentException();
		}
		int type = b[5].intValue();
		return ProperDecoder.get(type).decode(b);
	}

	@Override
	public ubyte[] encode(Map<String, Object> m) {
		throw new UnsupportedOperationException();
	}

	public static class ProperDecoder{
		static Map<Integer,Encoder> map = new TreeMap<Integer,Encoder>();

		public static Encoder get(Integer key) {
			return map.get(key);
		}
		static{
			map.put(1, new Hello());
			map.put(66, new FoundSources());
			map.put(64, new IDchange());
			map.put(1, new Hello());
			map.put(1, new Hello());
		}
	}
}
