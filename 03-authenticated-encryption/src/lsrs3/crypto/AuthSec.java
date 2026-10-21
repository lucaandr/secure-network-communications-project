package lsrs3.crypto;

 

import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

 







public class AuthSec 
{
	private String macName;  
	private Mac macAlg;	
	private SecretKey sk; 
	
	 





	public AuthSec(String macName) throws Exception
	{
		this.macName = macName;
		macAlg = Mac.getInstance(macName);  
		 
	}
	
	 





	public byte[] genMac(byte[] txt) throws Exception
	{
		if (sk == null) throw new IllegalStateException("MAC key has not been set");
		macAlg.init(sk);
		return macAlg.doFinal(txt);
	}

	 






	public boolean verMac(byte[] txt, byte[] mac) throws Exception
	{
		if (sk == null) throw new IllegalStateException("MAC key has not been set");
		macAlg.init(sk);
		byte[] expected = macAlg.doFinal(txt);
		return java.security.MessageDigest.isEqual(expected, mac);
	}
	
	public String getMacName()
	{ 
		return macName; 
	}

	public void setMacName(String macName)
	{
		this.macName = macName;
	}

	public SecretKey getKey()
	{
		return sk;
	}
	
	public void setKey(SecretKey sk)
	{
		this.sk = sk;
	}
	
	public void setKeyBytes(byte[] keyBytes)
	{
		this.sk = new SecretKeySpec(keyBytes, "AES");
	}
}
