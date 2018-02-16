package nostale.event;

import nostale.gameobject.Player;

public class GameEventListener extends EventListener {

	public GameEventListener(Player p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void call() {
		for (int i = 0; i < player.gameEvents.size(); i++) {
			GameEvent pEvent = player.gameEvents.get(i);
			if (pEvent == null)
				continue;
			eventCall(pEvent);
		}

	}

	public void eventCall(GameEvent p) {

	}
}
