package ed2k.server.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import ed2k.server.data_stru.ubyte;


public class Ed2kInputStream extends DataInputStream{

	public Ed2kInputStream(InputStream in) {
		super(in);
	}

	/**
	 * Reads a message in bytes not including
	 * the header and size fields
	 * @return a sequence of ubyte
	 * @throws IOException
	 */
	public ubyte[] readProtocol() throws IOException{
		wait4Protocol();
		long size = readSize();
		ubyte[] data = readFully(size);
		return data;
	}
	private void wait4Protocol() throws IOException{
		while(true){
			if(!isProtocol())
				continue;
			break;
		}
	}
	private boolean isProtocol() throws IOException{
		int i = readUnsignedByte();
		if((i==0xE3)||(i==0xC5))return true;
		return false;
	}
	private long readSize() throws IOException{
		long l = 0;
		for(int i=0;i<4;i++){
			l+=readUnsignedByte()*Math.pow(256, i);
		}
		return l;
	}
	private ubyte[] readFully(long size) throws IOException{
		ubyte[] b = new ubyte[(size>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)size)];
		for(int i=0;i<size;i++){
			b[i] = new ubyte(read());
		}
		return b;
	}
}
