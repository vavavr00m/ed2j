package ed2k.server.message;

public class MessageHeader {

	byte protocol = (byte) (0xC5-128);//0xE3 for eDonky and 0xC5 for eMule
	byte size = 0;
	byte type;
}
