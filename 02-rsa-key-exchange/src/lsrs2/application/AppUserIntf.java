package lsrs2.application;

 

import java.security.PublicKey;

import lsrs2.comm.DataMsg;
import lsrs2.comm.KeyExchMsg;

public interface AppUserIntf 
{
	public void sendData(String dstId, byte[] ptxt) throws Exception;
	
	public byte[] recvData(DataMsg dataMsg) throws Exception;

	public void recvKeyExch(KeyExchMsg msg) throws Exception;
	
	public void recvPubKey(String remId, PublicKey pubKey) throws Exception;
	
}
