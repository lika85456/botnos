package nostale.util;

import java.util.List;
import java.util.Map.Entry;

import nostale.data.MonsterMapInstance;
import nostale.gameobject.Player;
import nostale.resources.Map;

public class Pos {
	public short x;
	public short y;

	public Pos(Pos x) {
		this.x = x.x;
		this.y = x.y;
	}

	public Pos(int x, int y) {
		this.x = (short) x;
		this.y = (short) y;
	}

	public static int getRange(Pos x, Pos y) {
		int difX = x.x - y.x;
		int difY = x.y - y.y;
		if (difX == 0)
			difX = 1;
		if (difY == 0)
			difY = 1;
		difX -= difX / Math.abs(difX);
		difY -= difY / Math.abs(difY);
		return (int) Math.ceil(Math.sqrt(Math.pow(x.x - y.x, 2) + Math.pow(x.y - y.y, 2)));
	}

	public void add(Pos x) {
		this.x += x.x;
		this.y += x.y;
	}

	public static Pos getShortestMovablePos(Map m, Pos p) {
		for (int i = 1; i < 2000; i++) {
			for (int x1 = p.x - i; x1 < x1 + i; x1++) {
				if (m.canWalkHere(x1, p.y + i)) {
					return new Pos(x1, p.y + 1);
				}
				if (m.canWalkHere(x1, p.y - i)) {
					return new Pos(x1, p.y - 1);
				}
			}
			for (int y1 = p.y - i; y1 < y1 + i; y1++) {
				if (m.canWalkHere(p.x + i, y1)) {
					return new Pos(p.x + i, y1);
				}
				if (m.canWalkHere(p.x - i, y1)) {
					return new Pos(p.x - i, y1);
				}
			}
		}
		return null;
	}

	public static Pos getShortestPosInRange(int range, Pos mob, Pos character) {
		int r = getRange(mob, character);
		if (r < range) {
			return character;
		}
		Pos toRet = new Pos(character.x + ((mob.x - character.x) * (r - range) / r),
				character.y + ((mob.y - character.y) * (r - range) / r));
		return toRet;
	}

	public static Pos[] getPath(nostale.resources.Map m, Pos p1, Pos p2) {
		if (p1.x == p2.x && p1.y == p2.y)
			return new Pos[] { p1 };
		nostale.util.Map<ExampleNode> map = new nostale.util.Map<ExampleNode>(m.width, m.height, new ExampleFactory());
		for (int x = 0; x < m.width; x++) {
			for (int y = 0; y < m.height; y++) {
				map.setWalkable(x, y, m.canWalkHere(x, y));
			}
		}
		List<ExampleNode> list = null;
		try {
			list = map.findPath(p1.x, p1.y, p2.x, p2.y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// map.drawMap();
		Pos[] toReturn = new Pos[list.size()];
		for (int i = 0; i < list.size(); i++) {
			toReturn[i] = new Pos(list.get(i).getxPosition(), list.get(i).getyPosition());
		}
		return toReturn;
	}

	public static MonsterMapInstance GetNearestMob(Player p) {
		int range = 100000;
		MonsterMapInstance m = null;
		for (Entry<Integer, MonsterMapInstance> entry : p.map.Mobs.entrySet()) {
			// Integer key = entry.getKey();
			MonsterMapInstance mob = entry.getValue();
			if (mob == null || mob.Pos==null)
				continue;
			if (Pos.getRange(mob.Pos, p.pos) < range) {
				m = mob;
				range = Pos.getRange(mob.Pos, p.pos);
			}
		}
		return m;
	}

	@Override
	public String toString() {
		return this.x + "|" + this.y;
	}
}
