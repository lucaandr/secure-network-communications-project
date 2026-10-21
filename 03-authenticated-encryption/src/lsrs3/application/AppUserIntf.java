package lsrs3.application;

import javax.crypto.SecretKey;

import lsrs3.comm.DataMsg;

public interface AppUserIntf 
{
	public void sendData(String dstId, byte[] ptxt) throws Exception;

	public byte[] recvData(DataMsg dataMsg) throws Exception;

	public void setSecretKey(String remId, String use, SecretKey secKey) throws Exception;
}
