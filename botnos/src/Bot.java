import java.util.Map;

import nostale.data.AccountData;
import nostale.data.InventoryItemInstance;
import nostale.data.MapCharacterInstance;
import nostale.data.MonsterMapInstance;
import nostale.data.SkillData;
import nostale.domain.ItemType;
import nostale.gameobject.Player;
import nostale.handler.BattleHandler;
import nostale.handler.InventoryHandler;
import nostale.handler.MapHandler;
import nostale.handler.SpecialistHandler;
import nostale.handler.WalkHandler;
import nostale.resources.Resources;
import nostale.util.Pos;


public class Bot {
	public Player bot;
	public Thread thread;
	private boolean run = false;
	private boolean threadRun = false;
	public Bot(AccountData botData) {
		bot = new Player(botData);
		MapHandler mapHandler = new MapHandler(bot);
		InventoryHandler inventoryHandler = new InventoryHandler(bot);
		SpecialistHandler specialistHandler = new SpecialistHandler(bot);
		BattleHandler battleHandler = new BattleHandler(bot);
		WalkHandler walkHandler = new WalkHandler(bot);
		new java.util.Timer("BotTimer").schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	while(true)
		            	{
		            		try{
				            	if(battleHandler.target==null)
					            	battleHandler.target = Pos.GetNearestMob(bot);
				            	SkillData skillToBeUsed = battleHandler.baseSkill;
				            	if(Pos.getRange(bot.pos, battleHandler.target.Pos)<skillToBeUsed.Range+1)
				            		walkHandler.Walk(Pos.getShortestPosInRange(skillToBeUsed.Range+1, battleHandler.target.Pos, bot.pos));
				            	else
				            		battleHandler.useSkill(skillToBeUsed);
		            		}
		            		catch(Exception e)
		            		{
		            			e.printStackTrace();
		            		}
		            	}

		            }
		        }, 
		        5000 
		);
		//specialistHandler.putOn();
		thread = new Thread(){
			@Override
			public void run()
			{
				while(threadRun==true)
				{
					while(run==true)
					{
						bot.loop();
					}
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
			}
		};
	}
	
	public void run()
	{
		this.run = true;
		this.threadRun = true;
		if(!thread.isAlive())
			thread.start();
	}
	public void stop()
	{
		this.run=false;
		this.threadRun=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void restart(){
		stop();
		run();
	}

}
