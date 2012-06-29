package ed2k.server.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class AbstractConn implements Runnable{
	protected Socket socket;
	protected InputStream in;
	protected OutputStream out;
	public AbstractConn(Socket s) throws IOException {
		socket=s;
		in = s.getInputStream();
		out = s.getOutputStream();
	}

}
