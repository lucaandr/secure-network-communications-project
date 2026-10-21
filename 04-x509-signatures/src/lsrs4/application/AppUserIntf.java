package lsrs4.application;


import lsrs4.comm.DataMsg;

public interface AppUserIntf 
{
	public void sendData(String dstId, byte[] ptxt) throws Exception;
	
	public byte[] recvData(DataMsg dataMsg) throws Exception;
	
}
