package nostale.data;

import java.util.Timer;
import java.util.TimerTask;

import nostale.net.NostaleConnection;

public class Player {
	
	public int session;
	public NostaleConnection connection;
	public String name;
	public MapInstance map;
	public int HP;
	public int MP;
	public Inventory inventory;
	public Skills skills;
	public Statistics statistics;
	public PacketParser parser;
	public Timer t;
	public TimerTask tt;
	public Player()
	{
		
	}

}
