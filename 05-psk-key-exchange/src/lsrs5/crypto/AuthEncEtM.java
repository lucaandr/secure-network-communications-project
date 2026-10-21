package lsrs5.crypto;

 

import javax.crypto.SecretKey;

import lsrs5.misc.Utils;

 






public class AuthEncEtM 
{
	private EncSec encAlg;
	private AuthSec macAlg;
	
	public AuthEncEtM() throws Exception
	{ }
	
	 


	public AuthEncEtM(String encName, String macName) throws Exception
	{
		encAlg = new EncSec(encName);  
		macAlg = new AuthSec(macName); 
		Utils.debug("[AuthEnc] Authenticated-encryption scheme: "+encName+"_"+macName);
	}

	public Ciphertext encrypt(byte[] ptxt) throws Exception
	{
		Ciphertext ctxt = encAlg.encrypt(ptxt); 
		byte[] authBytes = Utils.concatenate(ctxt.getIV(), ctxt.getText());
		byte[] mac = macAlg.genMac(authBytes);
		ctxt.setMac(mac);
		return ctxt; 
	}
	
	public byte[] decrypt(Ciphertext ctxt) throws Exception
	{
		byte[] text = ctxt.getText(); 
		byte[] param = ctxt.getIV();
		byte[] mac = ctxt.getMac();
		return decrypt(text, param, mac);
	}

	public byte[] decrypt(byte[] text, byte[] param, byte[] mac) throws Exception
	{
		byte[] authBytes = Utils.concatenate(param, text);
		boolean accept = macAlg.verMac(authBytes, mac); 
		if (accept) {
			byte[] ptxt = encAlg.decrypt(param, text); 
			return ptxt; 
		}
		else {
			throw new Exception("[AuthEncEtM] Data authentication failed");
		}
	}

	public SecretKey getKeyMac()
	{
		return macAlg.getKey();
	}
	
	public void setKeyMac(SecretKey sk)
	{
		macAlg.setKey(sk);
	}
	
	public SecretKey getKeyEnc()
	{
		return encAlg.getKey();
	}
	
	public void setKeyEnc(SecretKey sk)
	{
		encAlg.setKey(sk);
	}
	
	public void setMacName(String macName) throws Exception
	{
		macAlg = new AuthSec(macName);
	}
	
	public void setEncName(String encName) throws Exception
	{
		encAlg = new EncSec(encName);
	}
	
	public String getEncName()
	{
		return encAlg.getEncName();
	}
	
	public String getMacName()
	{
		return macAlg.getMacName();
	}

}
