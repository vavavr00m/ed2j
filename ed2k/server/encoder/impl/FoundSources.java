package ed2k.server.encoder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.SourceItem;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;
import ed2k.server.misc.Toolbox;

public class FoundSources implements Encoder {

	@Override
	public Map<String, Object> decode(ubyte[] b) {
		ubyte[] file_hash = new ubyte[16];
		System.arraycopy(b, 6, file_hash, 0, 16);
		int sources_count = b[22].intValue();
		List<SourceItem> sources = new ArrayList<SourceItem>(sources_count);
		for (int i = 23; i + 6 < b.length; i += 6) {
			ubyte[] id = new ubyte[4];
			ubyte[] port = new ubyte[2];
			System.arraycopy(b, i, id, 0, 4);
			System.arraycopy(b, i + 4, port, 0, 2);
			sources.add(new SourceItem(id, port));
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("Sources", sources);
		map.put("FileHash", file_hash);
		return map;
	}

	@Override
	public ubyte[] encode(Map<String, Object> m) {
		@SuppressWarnings("unchecked")
		Collection<SourceItem> sources = (Collection<SourceItem>) m.get("Sources");
		ubyte[] data = ubyte.newArray(23 + sources.size() * 6);
		data[0] = new ubyte(0xe3);
		ubyte[] x = ubyte.parseOneDArray(Toolbox.int2Bytes(18 + sources.size() * 6));
		System.arraycopy(x, 0, data, 1, x.length);
		data[5] = new ubyte(0x42);
		System.arraycopy((ubyte[]) m.get("FileHash"), 0, data, 6, 16);
		data[22] = new ubyte(sources.size());
		int i = 0;
		for (SourceItem item : sources) {
			System.arraycopy(item.client_id, 0, data, 23 + i * 6, 4);
			System.arraycopy(item.client_port, 0, data, 23 + i * 6 + 4, 2);
			i++;
		}
		return data;
	}

}
