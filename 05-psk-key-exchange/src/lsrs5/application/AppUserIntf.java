package lsrs5.application;

 

import lsrs5.comm.AuthMsg;
import lsrs5.comm.DataMsg;
import lsrs5.comm.FailMsg;
import lsrs5.comm.KeyExchMsg;

public interface AppUserIntf 
{
	public void sendData(String dstId, byte[] ptxt) throws Exception;
	
	public byte[] recvData(DataMsg dataMsg) throws Exception;

	public void recvKeyExch(KeyExchMsg msg) throws Exception;

	public void recvAuth(AuthMsg msg) throws Exception;

	public void recvFail(FailMsg msg) throws Exception;
}
