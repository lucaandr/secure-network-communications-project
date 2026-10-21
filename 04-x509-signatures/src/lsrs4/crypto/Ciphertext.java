package lsrs4.crypto;

 

 






public class Ciphertext
{
	private byte[] txt; 
	private byte[] cert;
	private byte[] sig; 
	
	public Ciphertext(byte[] cert, byte[] txt, byte[] sig)
	{ this.cert = cert; this.txt = txt; this.sig = sig; }
	
	public Ciphertext(byte[] cert, byte[] txt)
	{ this.cert = cert; this.txt = txt; this.sig = null; }

	public byte[] getCert()
	{ return cert; }
	
	public byte[] getText()
	{ return txt; }		

	public byte[] getSig()
	{ return sig; }	

	public void setText(byte[] txt)
	{ this.txt = txt; }		

	public void setSig(byte[] sig)
	{ this.sig = sig; }	
}