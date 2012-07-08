package ed2k.server.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.impl.Hello;
import ed2k.server.encoder.impl.HelloAnswer;
import ed2k.server.misc.Toolbox;

public class LoginCall implements Callable<Boolean> {
	private final int port;
	private final long timeout;
	private final Map<String, Object> map;
	private final Socket socket;

	public LoginCall(Socket socket, int port, long timeout, Map<String, Object> map) {
		super();
		this.port = port;
		this.socket = socket;
		this.timeout = timeout;
		this.map = map;
	}

	@Override
	public Boolean call() throws Exception {
		Socket socket1 = null;
		try {
			socket1 = new Socket(socket.getInetAddress(), port);
			InputStream s1in = socket1.getInputStream();
			OutputStream s1out = socket1.getOutputStream();
			map.put("Server IP", ubyte.parseOneDArray(socket.getLocalAddress().getAddress()));
			map.put("Server Port", ubyte.parseOneDArray(Toolbox.int2Bytes(socket.getLocalPort())));
			Toolbox.send(map, new Hello(), s1out);
			Map<String, Object> map1 = new TreeMap<String, Object>();
			map1 = Toolbox.receive(new HelloAnswer(), s1in, timeout);
			if (map1.isEmpty()) {
				return false;
			}
		} catch (IOException e) {
			return false;
		} finally {
			try {
				socket1.close();
			} catch (Exception e) {

			}
		}
		return true;
	}

}