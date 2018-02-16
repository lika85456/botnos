package nostale;

import nostale.event.GameEvent;
import nostale.event.GameEventListener;
import nostale.event.gameEvent.IKilledAMobGameEvent;
import nostale.event.gameEvent.MeHitGameEvent;
import nostale.event.gameEvent.SkillHitGameEvent;
import nostale.gameobject.Player;

public class StatisticsGameEventListener extends GameEventListener {

	private Statistics statistics;

	public StatisticsGameEventListener(Player p, Statistics statistics) {
		super(p);
		this.statistics = statistics;
	}

	@Override
	public void eventCall(GameEvent p) {
		boolean print = true;
		if (p instanceof IKilledAMobGameEvent)
			statistics.mobsKilled++;
		else if (p instanceof MeHitGameEvent)
			statistics.dmgTaken += ((MeHitGameEvent) (p)).damage;
		else if (p instanceof SkillHitGameEvent)
			statistics.dmgDealed += ((SkillHitGameEvent) (p)).damage;
		else
			print = false;
		if (print == true)
			System.out.println(this.toString());
	}

	@Override
	public String toString() {
		String toRet = "";
		toRet += "Mobs killed " + statistics.mobsKilled + "\n";
		toRet += "Dmg Taken " + statistics.dmgTaken + "\n";
		toRet += "Dmg Dealed " + statistics.dmgDealed;
		return toRet;
	}

}
