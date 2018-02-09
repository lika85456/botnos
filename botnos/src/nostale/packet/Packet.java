package nostale.packet;


public class Packet {
	
	public String packetString; // Whole packet in string
	
	private String[] parameters;
	private boolean parsed = false;
	
	public String getParameter(int index)
	{
		if(parsed==false)
		{
			parsed = true;
			parameters = packetString.split(" ");
		}
		try {
			return parameters[index];
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	
	public Packet(String packet)
	{
		this.packetString = packet;
	}
	
	@Override
	public String toString()
	{
		return packetString;
	}

}