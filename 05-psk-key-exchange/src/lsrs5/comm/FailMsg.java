package lsrs5.comm;

 

 






public class FailMsg 
{
	private String src;  
	private String dst;  
	private String reason; 

	public FailMsg(String src, String dst, String reason)
	{
		this.src = src; 
		this.dst = dst; 
		this.reason = reason; 
	}
	
	public String getSrc()
	{
		return src;
	}

	public String getDst()
	{
		return dst;
	}

	public String getReason()
	{
		return reason;
	}
	
	public String toHexStr()
	{
		return "FailMsg: { from " + src + " to " + dst + ": " + reason + " }"; 
	}

}
