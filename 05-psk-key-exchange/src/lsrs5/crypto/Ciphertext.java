package lsrs5.crypto;

 

 













public class Ciphertext
{
	private byte[] txt; 
	private byte[] iv;
	private byte[] mac; 
	
	public Ciphertext(byte[] iv, byte[] etxt, byte[] mac)
	{ this.iv = iv; this.txt = etxt; this.mac = mac; }
	
	public Ciphertext(byte[] iv, byte[] etxt)
	{ this.iv = iv; this.txt = etxt; this.mac = null; }

	public byte[] getIV()
	{ return iv; }
	
	public byte[] getText()
	{ return txt; }	

	public byte[] getMac()
	{ return mac; }	

	public void setText(byte[] txt)
	{ this.txt = txt; }	

	public void setMac(byte[] mac)
	{ this.mac = mac; }	
	
	public void setIV(byte[] iv)
	{ this.mac = iv; }	
}