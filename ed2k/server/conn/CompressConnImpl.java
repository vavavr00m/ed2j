package ed2k.server.conn;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import ed2k.server.util.CompressOutputStream;
import ed2k.server.util.DecompressInputStream;

public class CompressConnImpl extends ConnImpl {

	public CompressConnImpl(Socket s) throws IOException {
		super(s);
		in = new DecompressInputStream(new BufferedInputStream(s.getInputStream()));
		out = new CompressOutputStream(s.getOutputStream());
	}

}
