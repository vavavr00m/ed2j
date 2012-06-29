package ed2k.server.conn;

import java.io.IOException;
import java.net.Socket;

public class DefaultConnFactory implements ConnFactory {

	private boolean compressed = false;
	public boolean isCompressed() {
		return compressed;
	}
	public void setCompressed(boolean compressed) {
		this.compressed = compressed;
	}
	@Override
	public Runnable createConn(Socket socket) throws IOException {
		return isCompressed()?new CompressConnImpl(socket):new ConnImpl(socket);
	}

}
