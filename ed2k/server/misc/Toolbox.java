package ed2k.server.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import ed2k.server.data_stru.ubyte;
import ed2k.server.encoder.Encoder;

public class Toolbox {

	public static String toVisualStringBytes(byte[] b) {
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) b[i];
		}
		return new String(c);
	}

	public static String toStringBytes(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (byte b1 : b) {
			sb.append(byte2Hex(b1));
		}
		return sb.toString();
	}

	public static int byte2Integer(byte[] b) {
		int result = 0;
		for (int i = 0; i < b.length; i++) {
			result += b[i] * Math.pow(256, i);
		}
		return result;
	}

	public static int byte2Integer(ubyte[] b) {
		if (b == null)
			return 0;
		int result = 0;
		for (int i = 0; i < b.length; i++) {
			result += b[i].intValue() * Math.pow(256, i);
		}
		return result;
	}

	public static byte[] int2Bytes(int a) {
		if (a == 0) {
			return new byte[] { 0 };
		}
		byte[] data = new byte[(int) Math.ceil(Math.log(a) / Math.log(256))];
		if (data.length < 1) {
			data = new byte[1];
		}
		int j = a;
		int i = 0;
		while (j > 0) {
			data[i] = (byte) (j % 256);
			j /= 256;
			i++;
		}
		return data;
	}

	public static ubyte[] int2UBytes(int a) {
		byte[] b = int2Bytes(a);
		return ubyte.parseOneDArray(b);
	}

	public static String byte2Hex(byte b) {
		int i = b + 128;
		int hi = i / 16;
		int lo = i % 16;
		return new String(new char[] { int2hex(hi), int2hex(lo) });
	}

	public static void printProperties(Properties p, PrintStream out) {
		for (Object key : p.keySet()) {
			Object val = p.get(key);
			out.print(key);
			out.print('=');
			out.print(val);
			out.println();
		}

	}

	public static char int2hex(int i) {
		if ((i >= 0) && (i <= 9))
			return String.valueOf(i).charAt(0);
		byte b = (byte) i;
		switch (b) {
		case 10:
			return 'a';
		case 11:
			return 'b';
		case 12:
			return 'c';
		case 13:
			return 'd';
		case 14:
			return 'e';
		case 15:
			return 'f';
		}
		return '\0';
	}

	public static int hex2int(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		}
		return -1;
	}

	public static byte[] parseBytes(String s) {
		byte[] data = new byte[s.length() / 2];
		for (int i = 0; i < data.length; i++) {
			data[i] = parseByte(s.substring(i * 2, i * 2 + 1));
		}
		return data;
	}

	public static byte parseByte(String s) {
		char hi;
		char lo;
		if (s.length() == 1) {
			hi = '0';
			lo = s.charAt(0);
		} else if (s.length() == 2) {
			hi = s.charAt(0);
			lo = s.charAt(1);
		} else {
			throw new IllegalArgumentException();
		}
		return (byte) (hex2int(hi) * 16 + hex2int(lo) - 128);
	}

	public static String toDemicalString(ubyte[] ub) {
		StringBuilder sb = new StringBuilder();
		for (ubyte b : ub) {
			sb.append(b.intValue());
			sb.append('.');
		}
		if (ub.length > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String toStringBytes(ubyte[] b, boolean bigEndian) {
		StringBuilder sb = new StringBuilder();
		for (ubyte u : b) {
			sb.append(u.toHex(bigEndian));
		}
		return sb.toString();
	}

	public static void send(Map<String, Object> map, Encoder en, OutputStream out) {
		try {
			out.write(ubyte.toBytes(en.encode(map)));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> receive(Encoder en, InputStream in) {
		return receive(en, in, 5000);
	}

	public static Map<String, Object> receive(Encoder en, InputStream in, long timeout) {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			while (timeout > 0) {
				Thread.sleep(100);
				timeout -= 100;
				int size = in.available();
				if (size <= 0)
					continue;
				byte[] data = new byte[size];
				in.read(data);
				ubyte[] udata = ubyte.parseOneDArray(data);
				result = en.decode(udata);
				break;
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}
}
