package nostale;

import java.util.HashMap;

import nostale.gameobject.Player;

public class Statistics {
	public int mobsKilled = 0;
	public long dmgTaken = 0;
	public long dmgDealed = 0;
	public long timeTotal = 0;// in millis
	public int goldEarned = 0;
	public HashMap<Short, Integer> items; // vnum and ammoun

	public Statistics(Player player) {
		items = new HashMap<>();
		player.addGameEventListener(new StatisticsGameEventListener(player, this));

	}

}
