package lsrs3.crypto;

 

import javax.crypto.SecretKey;

import lsrs3.misc.Utils;

 







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
		Ciphertext encrypted = encAlg.encrypt(ptxt);
		byte[] iv = encrypted.getIV() == null ? new byte[0] : encrypted.getIV();
		byte[] authenticated = Utils.concatenate(iv, encrypted.getText());
		byte[] mac = macAlg.genMac(authenticated);
		return new Ciphertext(encrypted.getIV(), encrypted.getText(), mac);
	}
	
	 





	public byte[] decrypt(Ciphertext ctxt) throws Exception
	{
		byte[] iv = ctxt.getIV() == null ? new byte[0] : ctxt.getIV();
		byte[] authenticated = Utils.concatenate(iv, ctxt.getText());
		boolean accept = macAlg.verMac(authenticated, ctxt.getMac());
		if (accept) {
			return encAlg.decrypt(ctxt);
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
