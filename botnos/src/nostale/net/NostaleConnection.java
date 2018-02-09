package nostale.net;

import nostale.data.Server;

public class NostaleConnection extends Connection{
	public NostaleConnection(Server gs)
	{
		this.Connect(gs.ip,gs.port);
	}
}
