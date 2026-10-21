package lsrs5.application;

 

import lsrs5.comm.AuthMsg;
import lsrs5.comm.Communicator;
import lsrs5.comm.DataMsg;
import lsrs5.comm.FailMsg;
import lsrs5.comm.KeyExchMsg;
import lsrs5.crypto.AuthEncEtM;
import lsrs5.crypto.Ciphertext;
import lsrs5.crypto.KeyExchPSKMac;
import lsrs5.crypto.KeyManagerKS;
import lsrs5.misc.Utils;

 
















public class SeCom5 implements AppUserIntf
{
	private String locId; 
	private Communicator comm;
	
	private KeyManagerKS km; 
	private KeyExchPSKMac kx;
	private String keyAuthName = "HMACSHA256"; 
	private int keyLen = 128;  

	private AuthEncEtM authEnc;

	public SeCom5(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this); 
	}

	public void setKeyStore(String ksFile, String pwd) throws Exception
	{
		km = new KeyManagerKS(ksFile, pwd); 
		km.loadKeyStore();
		km.list("[SeCom: "+ locId + "] Initial ");
	}

	 


	public void setKeyExch(String keyAuthName, int keyLen) 
	{
		this.keyAuthName = keyAuthName;
		this.keyLen = keyLen;
	}
	
	 


	public void setAuthEnc(String authSecName, String encSecName, int keyLen) throws Exception
	{
		authEnc = new AuthEncEtM(); 
		authEnc.setMacName(authSecName);
		authEnc.setEncName(encSecName);
	}

	 



	private void prepKeyExch() 
	{
		kx = new KeyExchPSKMac(locId, comm, keyAuthName, keyLen);
		kx.setKeyManager(km);
	}
	
	 






	public void doKeyExch(String remId) throws Exception
	{
		prepKeyExch(); 
		kx.doKeyExch(remId); 
	}
	
	 




	public void recvKeyExch(KeyExchMsg msg) throws Exception
	{
		if (kx == null) 
			prepKeyExch(); 
		else if (kx.isFinished())
			prepKeyExch(); 
		kx.recvKeyExch(msg);
	}

	 













	public void recvAuth(AuthMsg msg) throws Exception
	{
		kx.recvAuth(msg); 
		if (kx.isSuccess()) {
			Utils.debug("[SeCom: "+ locId + "] Key exchange succeeded."); 
			km.list("[SeCom: "+ locId + "] ");
		}
		else
			Utils.debug("[SeCom: "+ locId + "] Key exchange failed."); 
	}

	 





	public void recvFail(FailMsg msg) throws Exception
	{
		 
		kx.recvFail(msg); 
		Utils.debug("[SeCom: "+ locId + "] Key exchange failed."); 
	}
	
	 



	public void sendData(String dstId, byte[] ptxt) throws Exception
	{
		Utils.debug("[SeCom: "+ locId + "] sends DataMsg to "+dstId+"\n  "+Utils.toHexExt(ptxt));
		authEnc.setKeyMac(km.getSecretKey(dstId + "_secret_key_mac"));
		authEnc.setKeyEnc(km.getSecretKey(dstId + "_secret_key_enc"));
		Ciphertext ctxt = authEnc.encrypt(ptxt);
		comm.sendDataMsg(new DataMsg(locId, dstId, ctxt));
	}

	 



	public byte[] recvData(DataMsg dataMsg) throws Exception
	{
		authEnc.setKeyMac(km.getSecretKey(dataMsg.getSrc() + "_secret_key_mac"));
		authEnc.setKeyEnc(km.getSecretKey(dataMsg.getSrc() + "_secret_key_enc"));
		byte[] ptxt = authEnc.decrypt(dataMsg.getData()); 
		Utils.debug("[SeCom: "+locId+"] rcvd DataMsg from "+dataMsg.getSrc()+ 
				". Accepted\n  "+Utils.toHexExt(ptxt)); 
		return ptxt;
	}

	public String getId()
	{
		return locId;
	}

}
