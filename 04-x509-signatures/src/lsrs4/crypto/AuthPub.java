package lsrs4.crypto;

 

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

 







public class AuthPub 
{
	private String sigName; 
	private Signature sigAlg; 
	private PublicKey pubKey;
	private PrivateKey privKey;

	 



	public AuthPub(String sigName) throws Exception
	{ 
		this.sigName = sigName; 
		sigAlg = Signature.getInstance(sigName);
		 
	}
	
	 





	public byte[] genSig(byte[] txt) throws Exception
	{
		if (privKey == null) throw new IllegalStateException("Signing private key has not been set");
		sigAlg.initSign(privKey);
		sigAlg.update(txt);
		return sigAlg.sign();
	}

	 






	public boolean verSig(byte[] txt, byte[] sig) throws Exception
	{
		if (pubKey == null) throw new IllegalStateException("Signature verification public key has not been set");
		sigAlg.initVerify(pubKey);
		sigAlg.update(txt);
		return sigAlg.verify(sig);
	}
	
	public String getSigName()
	{
		return sigName;
	}

	public void setSigName(String sigName)
	{
		this.sigName = sigName;
	}

	public PublicKey getPubKey()
	{
		return pubKey;
	}
	
	public void setPubKey(PublicKey pk)
	{
		this.pubKey = pk;
	}

	public PrivateKey getPrivKey()
	{
		return privKey;
	}
	
	public void setPrivKey(PrivateKey sk)
	{
		this.privKey = sk;
	}

}
