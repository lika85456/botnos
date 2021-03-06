package nostale.handler;

import nostale.gameobject.Player;
import nostale.packet.Packet;
import nostale.util.Pos;

public class WalkHandler extends Handler {
	public WalkHandler(Player p) {
		super(p);
	}

	public void Walk(Pos p) {
		if (player.IsMoving == false && player.pos.x != p.x && player.pos.y != p.y)
			this.Walk(Pos.getPath(player.map, player.pos, p), 0);
	}

	public void Walk(int x, int y) {
		this.Walk(new Pos(x, y));
	}

	private void Walk(Pos[] path, int index) {
		if (index >= path.length - 1) {
			player.IsMoving = false;
			player.pos.x = path[path.length-1].x;
			player.pos.y = path[path.length-1].y;
			player.sendPacket(new Packet(
					"walk " + path[path.length - 1].x + " " + path[path.length - 1].y + " 0 " + player.Speed));
			System.out.println("Walking stopped");
;			return;
		}

		player.IsMoving = true;
		player.sendPacket(new Packet("walk " + path[index].x + " " + path[index].y + " 0 " + player.Speed));
		index += 3;
		final int i = index;
		int timeToWait = 3 * (player.Speed / 1000);
		new java.util.Timer("WalkTimer").schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				Walk(path, i);
			}
		}, timeToWait);
	}
}
