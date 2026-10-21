package lsrs4.comm;

 

import lsrs4.crypto.Ciphertext;
import lsrs4.misc.Utils;

 






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
		String ps = (data.getCert() == null)? "" : 
			" cert["+ data.getCert().length +"]:\n  " + Utils.toHex(data.getCert()) + "\n";
		String ms = (data.getSig() == null)? "" : 
			" sig["+ data.getSig().length +"]:\n  " + Utils.toHex(data.getSig()) + "\n";
		return "DataMsg: { from " + src + " to " + dst + "\n" + ds + ps + ms + "}"; 
	}

}
