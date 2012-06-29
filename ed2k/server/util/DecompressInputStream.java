package ed2k.server.util;

import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class DecompressInputStream extends InflaterInputStream{

	public DecompressInputStream(InputStream in) {
		super(in);
	}

}
