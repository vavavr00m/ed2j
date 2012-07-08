package ed2k.server.encoder.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import ed2k.server.data_stru.UByteQueue;
import ed2k.server.data_stru.UserHash;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;
import ed2k.server.message.MessageTag;
import ed2k.server.misc.Toolbox;

public class Login implements Encoder {

	public Login() {
	}

	@Override
	/**
	 * updated on 2012-6-29 by Super-User
	 */
	public Map<String, Object> decode(ubyte[] b) {
		Map<String, Object> map = new TreeMap<String, Object>();
		UByteQueue q = new UByteQueue(b);
		q.poll();// message type;
		map.put("UserHash", new UserHash(q.pollSome(16)));
		map.put("Client ID", q.pollSome(4));
		map.put("Client Port", q.pollSome(2));
		map.put("isHello", Boolean.FALSE);
		int tag_count = Toolbox.byte2Integer(q.pollSome(4));
		for (int i = 0; i < tag_count; i++) {
			MessageTag tag = new MessageTag();
			tag.setType(q.poll());
			q.pollSome(2);
			tag.setName(q.poll());
			if (tag.getType().equals(MessageTag.STRING_TAG)) {
				int len = Toolbox.byte2Integer(q.pollSome(2));
				ubyte[] arr = q.pollSome(len);
				byte[] barray = ubyte.toBytes(arr);
				try {
					tag.setText(new String(barray, "utf8"));
				} catch (UnsupportedEncodingException e) {
					tag.setText(new String(barray));
				}
			} else {
				tag.setNum(Toolbox.byte2Integer(q.pollSome(4)));
			}
			if (tag.getName().intValue() == 1) {
				map.put("Nickname", tag.getText());
			}
		}
		return map;
	}

	@Override
	public ubyte[] encode(Map<String, Object> p) {
		MessageTag name_tag = new MessageTag();
		name_tag.setText(String.valueOf(p.get("name")));
		name_tag.setType(MessageTag.STRING_TAG);
		name_tag.setName(1);
		ubyte[] name_tag_ubyte = MessageTag.encode(name_tag);
		int size = 56 + name_tag_ubyte.length;
		ubyte[] data = ubyte.newArray(size);
		System.arraycopy(name_tag_ubyte, 0, data, 32, name_tag_ubyte.length);
		data[0] = ubyte.valueOf(0xe3);
		data[5] = ubyte.valueOf(1);
		data[28] = ubyte.valueOf(4);
		ubyte[] usize = ubyte.parseOneDArray(Toolbox.int2Bytes(size - 5));
		System.arraycopy(usize, 0, data, 1, Math.min(usize.length, 4));
		ubyte[] user_hash = ((UserHash) p.get("UserHash")).getBytes();
		if (user_hash != null) {
			System.arraycopy(user_hash, 0, data, 6, 16);
		}
		ubyte[] client_id = (ubyte[]) p.get("Client ID");
		if (client_id != null) {
			System.arraycopy(client_id, 0, data, 22, 4);
		}
		ubyte[] client_port = (ubyte[]) p.get("Client Port");
		if (client_port == null) {
			client_port = ubyte.newArray(2);
		}
		System.arraycopy(client_port, 0, data, 26, 2);
		int offset = 32 + name_tag_ubyte.length;
		MessageTag version_tag = new MessageTag();
		version_tag.setType(MessageTag.INTEGER_TAG);
		version_tag.setName(0x11);
		version_tag.setNum(60);
		System.arraycopy(MessageTag.encode(version_tag), 0, data, offset, 8);
		offset += 8;
		MessageTag port_tag = new MessageTag();
		port_tag.setType(MessageTag.INTEGER_TAG);
		port_tag.setName(0x0f);
		port_tag.setNum(Toolbox.byte2Integer(client_port));
		System.arraycopy(MessageTag.encode(port_tag), 0, data, offset, 8);
		offset += 8;
		MessageTag flag_tag = new MessageTag();
		flag_tag.setType(MessageTag.INTEGER_TAG);
		flag_tag.setName(0x20);
		flag_tag.setNum(0);
		System.arraycopy(MessageTag.encode(flag_tag), 0, data, offset, 8);
		return data;
	}

}
