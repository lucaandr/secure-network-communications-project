package lsrs5.attacks;

 

import lsrs5.application.AppUserIntf;
import lsrs5.comm.AuthMsg;
import lsrs5.comm.Communicator;
import lsrs5.comm.DataMsg;
import lsrs5.comm.KeyExchMsg;
import lsrs5.misc.Utils;

 















public class Adv1 extends Communicator
{
	private int test = 20; 
	private int index = 16;  
	
	public Adv1(int test) 
	{
		this.test = test; 
	}
	
	 
	public void sendDataMsg(DataMsg msg) throws Exception
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		switch (test) {
		case 1:
			if (msg.getSrc().equals("alice") && msg.getDst().equals("bob")) {
				byte[] data = msg.getData().getText();
				 
				byte[] fakeData = new byte[data.length];
				System.arraycopy(data, 0, fakeData, 0, data.length);
				fakeData[index] ^= 8; 
				msg.getData().setText(fakeData);
				Utils.debug("[Adv1] Forged "+msg.toHexStr());
			}
		}
		d.recvData(msg); 
	}

	 
	public void sendKeyExchMsg(KeyExchMsg msg) throws Exception
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		switch (test) {
		case 10:  
			if (msg.getSrc().equals("alice") && msg.getDst().equals("bob")) {
			 
				byte[] data = msg.getNonce();
				 
				byte[] fakeData = new byte[data.length];
				System.arraycopy(data, 0, fakeData, 0, data.length);
				fakeData[0] ^= 8; 
				msg.setNonce(fakeData);
				Utils.debug("[Adv1] Forged "+msg.toHexStr());
			}			
			break;
		case 11:  
			 
		}
		d.recvKeyExch(msg); 
	}
	
	 
	public void sendAuthMsg(AuthMsg msg) throws Exception
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		switch (test) {
		case 20:  
			 
			 
			if (msg.getSrc().equals("alice") && msg.getDst().equals("bob")) {
			 
				byte[] data = msg.getAuth();
				 
				byte[] fakeData = new byte[data.length];
				System.arraycopy(data, 0, fakeData, 0, data.length);
				fakeData[0] ^= 8; 
				msg.setAuth(fakeData);
				Utils.debug("[Adv1] Forged "+msg.toHexStr());
			}			
			break;
		case 21:  
			 
		}
		d.recvAuth(msg); 
	}

}
