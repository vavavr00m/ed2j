package ed2k.server;
import java.io.IOException;

import ed2k.server.conn.AcceptTCP;
import ed2k.server.conn.DefaultConnFactory;


public class Ed2JServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		AcceptTCP atcp = new AcceptTCP(4661);
		atcp.setFactory(new DefaultConnFactory());
		atcp.start();
	}

}
