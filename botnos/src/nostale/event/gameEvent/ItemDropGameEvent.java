package nostale.event.gameEvent;

import nostale.data.MapItemInstance;
import nostale.event.GameEvent;

public class ItemDropGameEvent extends GameEvent{
	public MapItemInstance item;
	public ItemDropGameEvent(MapItemInstance i)
	{
		item = i;
	}
}
