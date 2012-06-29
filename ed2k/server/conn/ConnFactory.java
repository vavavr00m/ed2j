package ed2k.server.conn;

import java.io.IOException;
import java.net.Socket;

public interface ConnFactory {

	public Runnable createConn(Socket socket) throws IOException;
}
