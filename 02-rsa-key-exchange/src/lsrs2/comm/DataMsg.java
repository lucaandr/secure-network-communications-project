package lsrs2.comm;

 

import lsrs2.crypto.Ciphertext;
import lsrs2.misc.Utils;

 






public class DataMsg 
{
	private String src;  
	private String dst;  
	private Ciphertext data; 
	
	public DataMsg(String src, String dst, Ciphertext data)
	{
		this.src = src; 
		this.dst = dst; 
		this.data = data; 
	}
	
	public String getSrc()
	{
		return src;
	}

	public String getDst()
	{
		return dst;
	}

	public Ciphertext getData()
	{
		return data;
	}
	
	public String toHexStr()
	{
	    String ds = " data["+ data.getText().length +"]:\n  " + Utils.toHexExt(data.getText()) + "\n";
		String ps = (data.getParam() == null)? "" : 
			" iv["+ data.getParam().length +"]:\n  " + Utils.toHex(data.getParam()) + "\n";
		String ms = (data.getMac() == null)? "" : 
			" mac["+ data.getMac().length +"]:\n  " + Utils.toHex(data.getMac()) + "\n";
		return "DataMsg: { from " + src + " to " + dst + "\n" + ds + ps + ms + "}"; 
	}

}
