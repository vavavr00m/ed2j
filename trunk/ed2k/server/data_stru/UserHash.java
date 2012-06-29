package ed2k.server.data_stru;

import java.util.Arrays;
import java.util.Random;

import ed2k.server.misc.Toolbox;

public class UserHash implements Comparable<UserHash>,Cloneable{
	
	private final ubyte[] b;
	private final String str;
	public UserHash(ubyte[] b){
		this.b=b;
		str = Toolbox.toStringBytes(b);
	}
	public static UserHash parseUserID(String str){
		ubyte[] b = new ubyte[16];
		for(int i=0;i<8;i++){
			String s = str.substring(i*2, i*2+1);
			b[i]=ubyte.parseUbyte(s);
		}
		return new UserHash(b);
	}
	public static UserHash generate(){
		Random rand = new Random();
		byte[] b = new byte[16];
		rand.nextBytes(b);
		ubyte[] ub = ubyte.parseOneDArray(b);
		ub[5] = new ubyte(14);
		ub[14] = new ubyte(111);
		return new UserHash(ub);
	}
	public ubyte[] getBytes(){
		return b;
	}
	@Override
	public int hashCode() {
		return Arrays.hashCode(b);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserHash other = (UserHash) obj;
		if (!Arrays.equals(b, other.b))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return str;
	}
	public int compareTo(UserHash uid) {
		return str.compareTo(uid.str);
	}
	@Override
	protected UserHash clone(){
		return new UserHash(b);
	}
	
}
