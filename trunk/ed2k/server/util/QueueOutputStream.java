package ed2k.server.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueOutputStream extends FilterOutputStream implements Runnable{

	private BlockingQueue<byte[]> queue = new LinkedBlockingQueue<byte[]>();
	private boolean running = true;
	public QueueOutputStream(OutputStream out) {
		super(out);
	}
	@Override
	public void run() {
		while(running){
			try {
				write(queue.take());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void close() throws IOException {
		running=false;
		super.close();
	}
	@Override
	public void write(int b) throws IOException {
		write(new byte[]{(byte) b});
	}
	@Override
	public void write(byte[] b) throws IOException {
		try {
			queue.put(b);
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
	}

}
