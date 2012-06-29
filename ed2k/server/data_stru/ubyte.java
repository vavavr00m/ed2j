package ed2k.server.data_stru;

import ed2k.server.misc.Toolbox;

public class ubyte extends Number implements Comparable<ubyte>,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8932816268190356866L;
	private final byte value;
	public ubyte(byte b) {
		value = b;
	}
	public ubyte(int value) {
		int v1 = value%256;
		if(v1>127)v1-=256;
		this.value = (byte) v1;
	}
	@Override
	public int hashCode() {
		return value;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ubyte))
			return false;
		ubyte other = (ubyte) obj;
		if (value != other.value)
			return false;
		return true;
	}
	@Override
	public String toString() {
		String s = Integer.toHexString(intValue());
		return s.length()<2?"0"+s:s;
	}
	@Override
	public byte byteValue() {
		return value;
	}
	@Override
	public int intValue() {
		int v1 = value;
		if(v1<0)v1+=256;
		return v1;
	}
	@Override
	public long longValue() {
		return intValue();
	}
	@Override
	public float floatValue() {
		return intValue();
	}
	@Override
	public double doubleValue() {
		return intValue();
	}
	@Override
	public int compareTo(ubyte o) {
		return intValue()-o.intValue();
	}
	@Override
	protected ubyte clone(){
		return new ubyte(value);
	}
	public String toHex(){
		int i = intValue();
		int hi = i / 16;
		int lo = i % 16;
		return new String(new char[] { Toolbox.int2hex(lo), Toolbox.int2hex(hi) });
	}
	public static ubyte parseUbyte(String str){
		return new ubyte(Toolbox.hex2int(str.charAt(0))*256+Toolbox.hex2int(str.charAt(1)));
	}
	public static ubyte[] parseOneDArray(byte[] b){
		ubyte[] data = new ubyte[b.length];
		for(int i=0;i<b.length;i++){
			data[i]=new ubyte(b[i]);
		}
		return data;
	}
	public static ubyte[] newArray(int size){
		ubyte[] array = new ubyte[size];
		for(int i=0;i<size;i++){
			array[i]= new ubyte(0);
		}
		return array;
	}
	public static byte[] toBytes(ubyte[] ub){
		byte[] data = new byte[ub.length];
		for(int i=0;i<ub.length;i++){
			data[i]=ub[i]==null?0:ub[i].value;
		}
		return data;
	}
}