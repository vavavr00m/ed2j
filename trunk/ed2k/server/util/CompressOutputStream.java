package ed2k.server.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

public class CompressOutputStream extends DeflaterOutputStream{

	public CompressOutputStream(OutputStream out) {
		super(out);
	}

	@Override
	public void flush() throws IOException {
		super.finish();
	}

}
