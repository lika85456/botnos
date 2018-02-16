package nostale.packet.receive;

import nostale.data.SkillData;
import nostale.packet.Packet;
import nostale.resources.Resources;

//Sent at start of game or if skills changed. - list of skills
public class SkiPacket extends Packet {
	public int base;
	public SkillData[] generatedSkills;

	public SkiPacket(String str) {
		super(str);
		base = Integer.parseInt(parameters[1]);
		this.generatedSkills = new SkillData[parameters.length - 3];
		for (int i = 3; i < parameters.length; i++) {
			this.generatedSkills[i - 3] = Resources.getSkill(Integer.parseInt(parameters[i]));
		}

	}

}
