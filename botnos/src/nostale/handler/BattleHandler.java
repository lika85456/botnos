package nostale.handler;

import java.util.HashMap;

import nostale.data.MonsterMapInstance;
import nostale.data.SkillData;
import nostale.event.packetEventListener.BattlePacketEventListener;
import nostale.gameobject.Player;
import nostale.packet.Packet;

public class BattleHandler extends Handler{
	public  SkillData lastSkillRequest = null;
	public MonsterMapInstance target = null;
	public SkillData baseSkill;
	public HashMap<Short,SkillData> skills;
	public BattleHandler(Player p) {
		super(p);
		
		player.addPacketEventListener(new BattlePacketEventListener(p,this));
	}



	public void useSkill(SkillData skill) {
		if(skill.IsOnCooldown && this.lastSkillRequest!=null)
		{
			System.out.println();
		}
		if (this.lastSkillRequest == null && skill.IsOnCooldown==false) {
			skill.IsOnCooldown = true;
			lastSkillRequest = skill;
			System.out.println("Skill "+skill.CastId+" is on cooldown");
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	skill.IsOnCooldown = false;
			            	System.out.println("Skill "+skill.CastId+" is out of cooldown");
			            }
			        }, 
			        skill.Cooldown*100
			);
			System.out.println("Using a skill");
			this.lastSkillRequest = skill;
			player.sendPacket(
					new Packet("u_s " + skill.CastId + " 3 " + target.id + " " + player.pos.x + " " + player.pos.y));
		}

	}



}
