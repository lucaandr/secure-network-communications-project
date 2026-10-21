package lsrs4.crypto;

 

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

 





public class X509PKC 
{
	public static X509Certificate getCert(String pkcFile) throws Exception
	{
		 java.io.InputStream inStream = null;
		 X509Certificate pkc = null; 
		 try {
		     inStream = new java.io.FileInputStream(pkcFile);
		     CertificateFactory cf = CertificateFactory.getInstance("X.509");
		     pkc = (X509Certificate)cf.generateCertificate(inStream);
		 } finally {
		     if (inStream != null) {
		         inStream.close();
		     }
		 }
		 return pkc; 
	}

	public static X509Certificate getCert(byte[] cert) throws Exception
	{
		java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(cert); 		
		CertificateFactory cf = CertificateFactory.getInstance("X.509"); 
		return (X509Certificate)cf.generateCertificate(bais); 
	}

	public static String toStringShort(X509Certificate c)
	{
		String s = "X.509 Certificate version " + c.getVersion() + "\n" +
		 "Subject: " + c.getSubjectX500Principal().getName() + "\n" +
		 "Issuer:  " + c.getIssuerX500Principal().getName() + "\n" +
		 "Public key algorithm: " + c.getPublicKey().getAlgorithm() + "\n" +
		 "Valid until: " + c.getNotAfter(); 
		return s; 
	}
}
