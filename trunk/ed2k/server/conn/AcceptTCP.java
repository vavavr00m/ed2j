package ed2k.server.conn;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AcceptTCP extends Thread{
	private ExecutorService es = Executors.newCachedThreadPool();
	private final ServerSocket ss;
	private ConnFactory factory = new DefaultConnFactory();
	public AcceptTCP(int port) throws IOException {
		ss = new ServerSocket(port);
		System.out.println("listening on port: "+port);
	}
	@Override
	public void run() {
		while(!isClosed()){
			try {
				Socket s = ss.accept();
				Runnable r = factory.createConn(s);
				es.execute(r);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	public void setFactory(ConnFactory factory) {
		this.factory = factory;
	}
	public boolean isClosed() {
		return ss.isClosed();
	}
	public void close() throws IOException {
		ss.close();
		es.shutdownNow();
	}
}
