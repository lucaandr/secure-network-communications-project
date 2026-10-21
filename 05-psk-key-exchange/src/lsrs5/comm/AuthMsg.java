package lsrs5.comm;

 

import lsrs5.misc.Utils;

 







public class AuthMsg 
{
	private String src;  
	private String dst;  
	
	private byte[] auth;  
	
	public AuthMsg(String src, String dst, byte[] auth)
	{
		this.src = src; 
		this.dst = dst; 
		this.auth = auth;
	}
	
	public String getSrc()
	{
		return src;
	}

	public String getDst()
	{
		return dst;
	}

	public byte[] getAuth()
	{
		return auth;
	}
	
	public void setAuth(byte[] auth)
	{
		this.auth = auth;
	}
	
	public String toHexStr()
	{
		return "AuthMsg: { from " + src + " to " + dst + 
				"\n auth["+auth.length+"]:\n  " + Utils.toHexExt(auth) + "}"; 
	}
}
