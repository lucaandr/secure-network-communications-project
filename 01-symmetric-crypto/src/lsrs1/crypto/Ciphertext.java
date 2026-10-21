package lsrs1.crypto;

 

 




public class Ciphertext
{
	private byte[] etxt; 
	private byte[] iv; 
	
	public Ciphertext(byte[] param, byte[] etxt)
	{ this.iv = param; this.etxt = etxt; }
	
	public byte[] getParam()
	{ return iv; }
	
	public byte[] getText()
	{ return etxt; }
}