package lsrs4.misc;

 

 




public class Utils 
{
	public static boolean ENABLE_DEBUG = true;
	
	public static byte[] concatenate(byte[] a1, byte[] a2)
	{
		byte[] out = new byte[a1.length + a2.length];  
		System.arraycopy(a1, 0, out, 0, a1.length);
		System.arraycopy(a2, 0, out, a1.length, a2.length);
		return out; 
	}
	
	public static void split(byte[] b, byte[] a1, byte[] a2)
	{
		System.arraycopy(b, 0, a1, 0, a1.length);
		System.arraycopy(b, a1.length, a2, 0, a2.length);
	}
	
	 
	public static final String hexDigits[] = 
		{ "0", "1", "2", "3", "4", "5", "6", "7",
		"8", "9", "A", "B", "C", "D", "E", "F" };
	
	public static String toHex(byte[] bytes)
	{
		if (bytes.length == 0) 
			return "";
		 
		int maxRowLen = 16; 
		String s = ""; 
		for (int i=0; i < bytes.length; i++) {
			if (i > 0 && i % maxRowLen == 0) 
				s = s + "\n  ";
			int d1 = bytes[i] & 0xf; int d2 = (bytes[i]>>4) & 0xf;
			s = s + hexDigits[d2] + hexDigits[d1] + " ";
		}
		return s;
	}
	
	public static String toHexExt(byte[] bytes)
	{
		if (bytes.length == 0) 
			return "";
		 
		int maxRowLen = 16; 
		int numRows = bytes.length / maxRowLen; 
		int lastRowLen = bytes.length % maxRowLen; 
		if (lastRowLen == 0) {
			lastRowLen = maxRowLen;
			numRows--; 
		}
		byte[] t = new byte[maxRowLen]; 
		String s = ""; 
		for (int i=0; i < numRows; i++) {
			for (int j=0; j < maxRowLen; j++) {
				int k = i * maxRowLen + j; 
				int d1 = bytes[k] & 0xF; 
				int d2 = (bytes[k] >> 4) & 0xF;
				s = s + hexDigits[d2] + hexDigits[d1] + " ";
				if ((bytes[k] >= 0x20) && (bytes[k] <= 0x7E)) 
					t[j] = bytes[k];
				else
					t[j] = (byte)0x2E;  
			}
			s = s + "  " + new String(t) + "\n  "; 
		}
		 
		int k = numRows * maxRowLen; 
		for (int j=0; j < maxRowLen; j++) {
			if (k < bytes.length) {
				int d1 = bytes[k] & 0xF; 
				int d2 = (bytes[k] >> 4) & 0xF;
				s = s + hexDigits[d2] + hexDigits[d1] + " ";
				if ((bytes[k] >= 0x20) && (bytes[k] <= 0x7E)) 
					t[j] = bytes[k];
				else
					t[j] = (byte)0x2E;  
			}
			else {
				s = s + "   ";
			}
			k++;
		}
		s = s + "  " + new String(t, 0, lastRowLen); 
		return s;
	}
	
	public static void debug(String s)
	{
		if (Utils.ENABLE_DEBUG)
			System.out.println(s);
	}
}
