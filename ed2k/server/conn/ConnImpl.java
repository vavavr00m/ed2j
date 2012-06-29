package ed2k.server.conn;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ed2k.server.client.Client;
import ed2k.server.core.Manager;
import ed2k.server.data_stru.SourceItem;
import ed2k.server.data_stru.UserHash;
import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.impl.HelloAnswer;
import ed2k.server.encoder.impl.IDchange;
import ed2k.server.encoder.impl.Login;
import ed2k.server.message.DefaultMessageProcessor;
import ed2k.server.message.MessageProcessor;
import ed2k.server.message.MessageReader;
import ed2k.server.misc.Toolbox;
import ed2k.server.util.Ed2kInputStream;

import java.util.TreeMap;

public class ConnImpl extends AbstractConn {
	public ConnImpl(Socket s) throws IOException {
		super(s);
	}

	@Override
	public void run() {
		System.out.println("client connected!");
		Map<String, Object> map = new TreeMap<String, Object>();
		Ed2kInputStream ein = new Ed2kInputStream(in);
		try {
			map = new Login().decode(ein.readProtocol());
		} catch (IOException e) {
			shutdown(null, null);
			return;
		}
		if (!Boolean.FALSE.equals(map.get("isHello"))) {
			ubyte[] client_id = ubyte.parseOneDArray(socket.getLocalAddress().getAddress());
			ubyte[] port = Toolbox.int2UBytes(socket.getLocalPort());
			map.put("UserHash", UserHash.generate());
			map.put("Client ID", client_id);
			map.put("Client Port", port);
			map.put("Server IP", client_id);
			map.put("Server Port", port);
			Toolbox.send(map, new HelloAnswer(), out);
			shutdown(null, client_id);
			return;
		}
		ubyte[] client_port = (ubyte[]) map.get("Client Port");
		int port = Toolbox.byte2Integer(client_port);
		boolean high_id = true;
		long timeout = 10000;
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<Boolean> future = es.submit(new LoginCall(socket, port, timeout, map));
		try {
			high_id = future.get(timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e1) {
			high_id = false;
		}
		es.shutdown();
		es = null;
		ubyte[] client_id;
		if (high_id) {
			client_id = ubyte.parseOneDArray(socket.getInetAddress().getAddress());
		} else {
			Random r = new Random();
			client_id = new ubyte[] { new ubyte(r.nextInt(256)), new ubyte(r.nextInt(256)), new ubyte(r.nextInt(256)), new ubyte(0) };
		}

		map.put("Client ID", client_id);
		Toolbox.send(map, new IDchange(), out);
		System.out.println("client got :" + (high_id ? "hi" : "lo") + " id :" + Toolbox.toDemicalString(client_id));
		Client client = new Client((UserHash) map.get("UserHash"), new SourceItem(client_id, client_port));

		if (high_id) {
			client_id = (ubyte[]) map.get("Client ID");
			client_port = (ubyte[]) map.get("Client Port");
		} else {
			client_id = ubyte.newArray(4);// in question
			client_port = ubyte.newArray(2);// FIXME:
		}
		MessageReader reader = new MessageReader(ein, createMessageProcessor(client));
		reader.run();
		shutdown(client, client_id);
	}

	protected MessageProcessor createMessageProcessor(Client client) {
		return new DefaultMessageProcessor(client, out, ubyte.parseOneDArray(socket.getLocalAddress().getAddress()));
	}

	protected void shutdown(Client client, ubyte[] client_id) {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("client id :" + Arrays.toString(client_id) + " disconnected!");
		Manager.delRes(client);
	}
}
