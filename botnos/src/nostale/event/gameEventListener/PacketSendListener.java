package nostale.event.gameEventListener;

import nostale.event.GameEventListener;
import nostale.event.PacketEvent;
import nostale.gameobject.Player;
import nostale.handler.ConnectionHandler;
import nostale.net.Crypto;
import nostale.packet.PacketType;

public class PacketSendListener extends GameEventListener {
	public ConnectionHandler connectionHandler;

	public PacketSendListener(Player p, ConnectionHandler c) {
		super(p);
		this.connectionHandler = c;
	}

	@Override
	public void call() {
		for (int i = 0; i < player.packetEvents.size(); i++) {
			PacketEvent pEvent = player.packetEvents.get(i);
			if (pEvent == null)
				continue;
			if (pEvent.getType() == PacketType.SEND) {
				player.log("Packet send", pEvent.packet.packetString);
				connectionHandler.connection.send(Crypto.EncryptGamePacket(
						connectionHandler.packetId + " " + pEvent.packet.packetString, player.session, false));
				connectionHandler.packetId++;
			}
		}
	}
}
