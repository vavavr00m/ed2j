package ed2k.server.message;

import java.io.EOFException;
import java.io.InputStream;

import ed2k.server.data_stru.ubyte;
import ed2k.server.util.Ed2kInputStream;

public class MessageReader implements Runnable {

	private final Ed2kInputStream ein;
	private final MessageProcessor mp;

	public MessageReader(InputStream in, MessageProcessor mp) {
		super();
		if(in instanceof Ed2kInputStream){
			ein = Ed2kInputStream.class.cast(in);
		}else{
			ein = new Ed2kInputStream(in);
		}
		this.mp = mp;
	}

	@Override
	public void run() {
		while (true) {
			try {
				ubyte[] b = ein.readProtocol();
				mp.processMessage(b);
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
				continue;
			}
		}
	}

}
