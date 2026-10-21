package lsrs2.comm;

 

import lsrs2.misc.Utils;

public class KeyExchMsg 
{
	private String src;  
	private String dst;  
	private byte[] data;  
	
	public KeyExchMsg(String src, String dst, byte[] data)
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

	public byte[] getData()
	{
		return data;
	}
	
	public String toHexStr()
	{
		return "KeyExchMsg: { from " + src + " to " + dst + "\n  " + Utils.toHexExt(data) + "}"; 
	}

}
