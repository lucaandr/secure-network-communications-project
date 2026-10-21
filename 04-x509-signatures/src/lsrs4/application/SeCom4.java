package lsrs4.application;

 

import java.security.PublicKey;
import java.security.cert.X509Certificate;

import lsrs4.comm.*;
import lsrs4.crypto.*;
import lsrs4.misc.Utils;

 








public class SeCom4 implements AppUserIntf
{
	private String locId;
	private Communicator comm;

	private AuthPub authPub;
	private KeyManagerKS km;
	X509Certificate locCert;

	public SeCom4(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this);
	}

	public void setAuthPub(String authPubName) throws Exception
	{
		authPub = new AuthPub(authPubName);
	}

	public void setCertificate(String pkcFile) throws Exception
	{
		locCert = X509PKC.getCert(pkcFile);
		Utils.debug("[Participant: "+ locId + "] PKC loaded: "+ X509PKC.toStringShort(locCert));  
	}

	public void setKeyStore(String ksFile, String pwd) throws Exception
	{
		km = new KeyManagerKS(ksFile, pwd);
		km.list("[Participant: "+ locId + "] ");
	}

	public PublicKey getPubKey() throws Exception
	{
		return locCert.getPublicKey();
	}

	public void sendData(String dstId, byte[] ptxt) throws Exception
	{
		Utils.debug("[Participant: "+ locId + "]: sends message to "+dstId+"\n  "+Utils.toHexExt(ptxt));
		authPub.setPrivKey(km.getPrivateKey(locId));
		byte[] sig = authPub.genSig(ptxt);
		Ciphertext ctxt = new Ciphertext(locCert.getEncoded(), ptxt, sig);
		 
		comm.sendDataMsg(new DataMsg(locId, dstId, ctxt));
	}

	public byte[] recvData(DataMsg dataMsg) throws Exception
	{
		byte[] ptxt = dataMsg.getData().getText();
		X509Certificate cert = X509PKC.getCert(dataMsg.getData().getCert());
		verifyCert(cert, dataMsg.getSrc());
		authPub.setPubKey(cert.getPublicKey());
		boolean accept = authPub.verSig(ptxt, dataMsg.getData().getSig());
		Utils.debug("[Participant] "+locId+": rcvd message from "+dataMsg.getSrc()+
			". "+ ((accept)? "Accepted." : "Rejected") + "\n  "+Utils.toHexExt(ptxt));
		if (accept)
			return ptxt;
		else
			throw new Exception("[AppUserSig] Data authentication failed");
	}

	 





	private void verifyCert(X509Certificate cert, String name) throws Exception
	{
		 
		String pkcSubName = cert.getSubjectX500Principal().getName();
		String pkcComName = pkcSubName.split(",")[0].split("=")[1];
		Utils.debug("[Participant] "+locId+": rcvd PKC from "+name+" with CN "+pkcComName); 
		if (!name.equals(pkcComName)) {
			throw new Exception("[Participant] "+locId+" Invalid Certificate: Wrong Subject name");
		}

		cert.checkValidity();
		X509Certificate issuerCert = km.getTrustedIssuerCert(cert);
		PublicKey issuerKey = issuerCert.getPublicKey();
		cert.verify(issuerKey);

		 
		 
	}

	public String getId()
	{
		return locId;
	}


}
