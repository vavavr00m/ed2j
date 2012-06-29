package ed2k.server.message;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ed2k.server.client.Client;
import ed2k.server.core.Manager;
import ed2k.server.data_stru.FileHash;
import ed2k.server.data_stru.SourceItem;
import ed2k.server.data_stru.ubyte;

public class DefaultMessageProcessor extends AbstractMessageProcessor {

	public DefaultMessageProcessor(Client client, OutputStream out, ubyte[] address) {
		super(client, out, address);
	}

	protected List<SourceItem> getSources(FileHash fh) {
		Collection<Client> clients = Manager.findClients(fh);
		List<SourceItem> sources = new ArrayList<SourceItem>(clients.size());
		for (Client client : clients) {
			SourceItem item = client.getSource();
			if (Arrays.equals(item.client_id, localhost)) {
				sources.add(new SourceItem(address, item.client_port));
			} else {
				sources.add(item);
			}
		}
		return sources;
	}

	protected void offerFile(FileHash fh, String filename, long file_size) {
		Manager.addRes(client, fh);
	}
}
