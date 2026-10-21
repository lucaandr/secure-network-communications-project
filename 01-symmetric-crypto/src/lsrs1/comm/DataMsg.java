package lsrs1.comm;

 

import lsrs1.crypto.Ciphertext;
import lsrs1.misc.Utils;

 






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
		return "DataMsg: { from " + src + " to " + dst + "\n" + 
			" data["+ data.getText().length +"]:\n  " + Utils.toHexExt(data.getText()) + "\n" +
			" iv["+ data.getParam().length +"]:\n  " + Utils.toHex(data.getParam()) + "\n}";
	}
	

}
