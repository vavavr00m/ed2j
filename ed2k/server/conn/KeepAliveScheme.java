package ed2k.server.conn;

import static ed2k.server.misc.Toolbox.int2UBytes;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import ed2k.server.client.Client;
import ed2k.server.core.Manager;
import ed2k.server.data_stru.FileHash;
import ed2k.server.data_stru.UByteQueue;
import ed2k.server.data_stru.ubyte;

public class KeepAliveScheme implements Runnable, Closeable {

	private final int port = 4665;
	private boolean closed = false;

	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private final byte receiveByte[] = new byte[6];
	private final ExecutorService es = Executors.newSingleThreadExecutor(new MyThreadFactory());

	@Override
	public void run() {
		try {
			dataSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		while (!closed) {
			dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
			try {
				dataSocket.receive(dataPacket);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			UByteQueue q = new UByteQueue(ubyte.parseOneDArray(receiveByte));
			if (q.poll().intValue() != 0xe3) {
				continue;
			}
			int type = q.poll().intValue();
			switch (type) {
			case 0x96:
				processStatus(q);
				break;
			case 0xa2:
				processDescription(q);
				break;
			case 0x9a:
				processGetSources(q, dataSocket.getRemoteSocketAddress());
				break;

			}
		}
	}

	protected void processDescription(UByteQueue q) {
		q.clear();
		q.add(0xe3);
		q.add(0xa3);
		q.pushSome(ubyte.parseOneDArray("Ed2J".getBytes()));
		byte[] bytes = q.toByteArray();
		SocketAddress addr = dataPacket.getSocketAddress();
		dataPacket = new DatagramPacket(bytes, bytes.length);
		dataPacket.setSocketAddress(addr);
		try {
			dataSocket.send(dataPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void processGetSources(UByteQueue q, SocketAddress socketAddress) {
		List<FileHash> l = new LinkedList<FileHash>();
		while (q.size() >= 16) {
			l.add(new FileHash(q.pollSome(16)));
		}
		es.submit(new GetSourcesWorker(l, socketAddress));
	}

	protected void processStatus(UByteQueue q) {
		ubyte[] challenge = q.pollSome(4);
		q.clear();
		q.add(0xe3);
		q.add(0x97);
		q.pushSome(challenge);
		ubyte[] u = ubyte.newArray(4);
		ubyte[] u1 = int2UBytes(Manager.clientCount());
		System.arraycopy(u1, 0, u, 0, u1.length);
		q.pushSome(u);
		u = ubyte.newArray(4);
		u1 = int2UBytes(Manager.fileCount());
		System.arraycopy(u1, 0, u, 0, u1.length);
		q.pushSome(u);
		q.pushSome(int2UBytes(Integer.MAX_VALUE));
		q.pushSome(int2UBytes(Integer.MAX_VALUE));
		q.pushSome(int2UBytes(1));
		byte[] bytes = q.toByteArray();
		SocketAddress addr = dataPacket.getSocketAddress();
		dataPacket = new DatagramPacket(bytes, bytes.length);
		dataPacket.setSocketAddress(addr);
		try {
			dataSocket.send(dataPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}

	class GetSourcesWorker implements Runnable {

		List<FileHash> l;
		SocketAddress socketAddress;

		public GetSourcesWorker(List<FileHash> l, SocketAddress socketAddress) {
			super();
			this.l = l;
			this.socketAddress = socketAddress;
		}

		@Override
		public void run() {
			for (FileHash fh : l) {
				Collection<Client> col = Manager.findClients(fh);
				if (!col.isEmpty()) {
					UByteQueue q = new UByteQueue();
					q.add(0xe3);
					q.add(0x9b);
					q.pushSome(fh.getId());
					int sources = col.size();
					if (sources > 256) {
						sources = 256;
					}
					q.add(sources);
					int i = 0;
					for (Client client : col) {
						q.pushSome(client.getSource().client_id);
						q.pushSome(client.getSource().client_port);
						i++;
						if (i > sources) {
							break;
						}
					}
					byte[] bytes = q.toByteArray();
					DatagramPacket dataPacket = new DatagramPacket(bytes, bytes.length);
					dataPacket.setSocketAddress(socketAddress);
					try {
						dataSocket.send(dataPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	static class MyThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setPriority(Thread.MIN_PRIORITY);
			return t;
		}

	}
}
