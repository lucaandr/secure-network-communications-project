package lsrs5.comm;

 

import lsrs5.misc.Utils;

 










public class KeyExchMsg 
{
	private String src;    
	private String dst;    
	private byte[] nonce;  
	
	public KeyExchMsg(String src, String dst, byte[] nonce)
	{
		this.src = src; 
		this.dst = dst; 
		this.nonce = nonce;
	}
	
	public String getSrc()
	{
		return src;
	}

	public String getDst()
	{
		return dst;
	}

	public byte[] getNonce()
	{
		return nonce;
	}
	
	public void setNonce(byte[] nonce)
	{
		this.nonce = nonce;
	}
	
	public String toHexStr()
	{
		return "KeyExchMsg: { from " + src + " to " + dst + 
				"\n Nonce["+nonce.length+"]:\n  " + Utils.toHexExt(nonce) + "}"; 
	}

}
