package ed2k.server.message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import ed2k.server.data_stru.ubyte;

public class MessageTag extends Number{
	public static final ubyte STRING_TAG = new ubyte(2);
	public static final ubyte INTEGER_TAG = new ubyte(3);
	public static final ubyte FLOAT_TAG = new ubyte(4);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1723698809634082389L;
	private ubyte type,name;
	private String text = "";
	private Number num = 0;
	private int len = 8;
	public MessageTag() {
		super();
	}

	public static MessageTag parseMessageTag(ubyte[] b){
		MessageTag msg = new MessageTag();
		msg.type=b[0];
		msg.name=b[3];
		int type = b[0].intValue();
		if(type==2){//str
			int len = 0;
			len += b[4].intValue();
			len += b[5].intValue()*256;
			msg.len=len+6;
			int l1 = b.length-6;
			if(l1<len)
				len = l1;
			byte[] barray = new byte[len];
			ubyte[] ubarray = ubyte.newArray(len);
			System.arraycopy(b, 6, ubarray, 0, len);
			barray=ubyte.toBytes(ubarray);
			try {
				msg.text = new String(barray,"utf8");
			} catch (UnsupportedEncodingException e) {
				msg.text = new String(barray);
			}
		}else{
			long num = 0;
			for(int i=0;i<4;i++){
				num+=b[4+i].intValue()*Math.pow(256, i);
			}
			msg.num=num;
			if(type==4){
				msg.num = Float.intBitsToFloat((int) num);
			}
		}
		return msg;
	}

	
	public static ubyte[] encode(MessageTag msg){
		ArrayList<ubyte> data = new ArrayList<ubyte>(msg.len);
		data.add(msg.type);
		data.add(new ubyte(1));
		data.add(new ubyte(0));
		data.add(msg.name);
		int type = msg.type.intValue();
		if(type==2){
			String text = msg.text;
			int len = text.length();
			ubyte lo = new ubyte(len%256);
			ubyte hi = new ubyte(len/256);
			data.add(lo);
			data.add(hi);
			data.addAll(Arrays.asList(ubyte.parseOneDArray(text.getBytes())));
		}else{
			long val = msg.longValue();
			if(type==4){
				val = Float.floatToIntBits(msg.floatValue());
			}
			for(int i=0;i<4;i++){
				int j = (int) (val%256);
				data.add(new ubyte(j));
				val/=256;
			}
		}
		return data.toArray(new ubyte[]{});
	}
	public int length(){
		return len;
	}
	
	public void setType(ubyte type) {
		this.type = type;
	}

	public void setName(ubyte name) {
		this.name = name;
	}
	public void setName(int name) {
		this.name = new ubyte(name);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setNum(Number num) {
		this.num = num;
	}

	public ubyte getType() {
		return type;
	}

	public ubyte getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	@Override
	public int intValue() {
		return num.intValue();
	}

	@Override
	public long longValue() {
		return num.longValue();
	}

	@Override
	public float floatValue() {
		return num.floatValue();
	}

	@Override
	public double doubleValue() {
		return num.doubleValue();
	}
	
}
