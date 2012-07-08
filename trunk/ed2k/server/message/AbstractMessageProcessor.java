package ed2k.server.message;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ed2k.server.client.Client;
import ed2k.server.core.Manager;
import ed2k.server.data_stru.FileHash;
import ed2k.server.data_stru.SourceItem;
import ed2k.server.data_stru.UserHash;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.impl.FoundSources;
import ed2k.server.encoder.impl.Hello;
import ed2k.server.misc.Toolbox;

public abstract class AbstractMessageProcessor implements MessageProcessor {

	protected ubyte[] client_id;
	protected ubyte[] client_port;
	protected ubyte[] id_and_port = new ubyte[6];
	protected final Client client;
	protected final OutputStream out;
	protected final ubyte[] address;

	public AbstractMessageProcessor(Client client, OutputStream out, ubyte[] address) {
		this.out = out;
		this.address = address;
		this.client = client;
		this.client_id = client.getSource().client_id;
		this.client_port = client.getSource().client_port;
		System.arraycopy(client_id, 0, id_and_port, 0, 4);
		System.arraycopy(client_port, 0, id_and_port, 4, 2);
	}

	@Override
	public void processMessage(ubyte[] data) {
		int type = data[0].intValue();
		switch (type) {
		case 1:
			processHello(data);
			break;
		case 21:
			processOfferFiles(data);
			break;
		case 25:
			processGetSources(data);
			break;
		case 64:
			processIDChange(data);
			break;
		case 66:
			processFoundSources(data);
			break;
		default:
			processUnknown(data);
			break;
		}
	}

	protected void processHello(ubyte[] data) {
		Map<String, Object> map = new Hello().decode(recover(data));
		client_id = (ubyte[]) map.get("Client ID");
		client_port = (ubyte[]) map.get("Client Port");
		System.arraycopy(client_id, 0, id_and_port, 0, 4);
		System.arraycopy(client_port, 0, id_and_port, 4, 2);
		System.arraycopy(client_id, 0, client.getSource().client_id, 0, 4);
		System.arraycopy(client_port, 0, client.getSource().client_port, 0, 2);
		client.setUserHash((UserHash) map.get("UserHash"));
	}

	protected void processFoundSources(ubyte[] data) {
	}

	protected void processIDChange(ubyte[] data) {
		ubyte[] id = new ubyte[4];
		System.arraycopy(data, 1, id, 0, 4);
		client_id = id;
		System.arraycopy(id, 0, id_and_port, 0, 4);
		System.out.println("ID Changed!\t" + Arrays.toString(id));
	}

	private void processUnknown(ubyte[] data) {
		System.out.println("Unknown");
		System.out.println(Arrays.toString(data));
	}

	protected void processGetSources(ubyte[] data) {
		ubyte[] hashCode = new ubyte[16];
		System.arraycopy(data, 1, hashCode, 0, 16);
		FileHash fh = new FileHash(hashCode);
		System.out.println("file requested:\t" + fh);
		Collection<Client> clients = Manager.findClients(fh);
		if (clients.isEmpty())
			return;
		List<SourceItem> sources = getSources(fh);
		System.out.println("sources found:\t" + sources);
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("FileHash", hashCode);
		map.put("SourceCount", sources.size());
		map.put("Sources", sources);

		Toolbox.send(map, new FoundSources(), out);
	}

	protected abstract List<SourceItem> getSources(FileHash fh);

	protected void processOfferFiles(ubyte[] data) {
		int count = data[1].intValue();
		if (count > 200)
			count = 200;
		int offset = 5;
		for (int i = 0; i < count; i++) {

			offset = findNext(data, id_and_port, offset) - 16;
			if (offset < 0)
				break;

			ubyte[] hashCode = new ubyte[16];
			System.arraycopy(data, offset, hashCode, 0, 16);
			offset += 16;
			ubyte[] client_id = new ubyte[4];
			System.arraycopy(data, offset, client_id, 0, 4);
			offset += 4;
			ubyte[] client_port = new ubyte[2];
			System.arraycopy(data, offset, client_port, 0, 2);
			offset += 2;

			ubyte[] tag_count = new ubyte[4];
			System.arraycopy(data, offset, tag_count, 0, 4);
			offset += 4;

			ubyte[] ufile_name_tag = new ubyte[8];
			System.arraycopy(data, offset, ufile_name_tag, 0, ufile_name_tag.length);
			MessageTag file_name_tag = MessageTag.parseMessageTag(ufile_name_tag);
			ufile_name_tag = new ubyte[file_name_tag.length()];
			System.arraycopy(data, offset, ufile_name_tag, 0, ufile_name_tag.length);
			file_name_tag = MessageTag.parseMessageTag(ufile_name_tag);
			String filename = file_name_tag.getText();
			offset += file_name_tag.length();
			ubyte[] ufile_size_tag = new ubyte[8];
			System.arraycopy(data, offset, ufile_size_tag, 0, 8);
			MessageTag file_size_tag = MessageTag.parseMessageTag(ufile_size_tag);
			long file_size = file_size_tag.longValue();
			offset += file_size_tag.length();

			FileHash fh = new FileHash(hashCode);
			System.out.print("file added:" + filename + "\t" + fh);
			System.out.print("\t" + Toolbox.toDemicalString(client_id));
			System.out.println(":" + Toolbox.byte2Integer(client_port));
			offerFile(fh, filename, file_size);
		}
	}

	protected abstract void offerFile(FileHash fileHash, String filename, long file_size);

	protected ubyte[] recover(ubyte[] data) {
		ubyte[] data1 = ubyte.newArray(data.length + 5);
		data1[0] = ubyte.valueOf(0xe3);
		ubyte[] size = Toolbox.int2UBytes(data.length);
		System.arraycopy(size, 0, data1, 1, size.length);
		System.arraycopy(data, 0, data1, 5, data.length);
		return data1;
	}

	private int findNext(ubyte[] data, ubyte[] sub, int offset) {
		if (offset < 0)
			return -1;
		int len = data.length - sub.length;
		for (int i = offset; i < len; i++) {
			boolean eq = true;
			for (int j = 0; j < sub.length; j++) {
				if (!data[i + j].equals(sub[j])) {
					eq = false;
					break;
				}
			}
			if (eq)
				return i;
		}
		return -1;
	}
}
