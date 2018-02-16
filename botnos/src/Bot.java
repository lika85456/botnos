import nostale.Statistics;
import nostale.data.AccountData;
import nostale.data.MapItemInstance;
import nostale.data.SkillData;
import nostale.event.GameEvent;
import nostale.event.GameEventListener;
import nostale.event.gameEvent.ItemDropGameEvent;
import nostale.event.gameEvent.MapoutGameEvent;
import nostale.gameobject.Player;
import nostale.handler.BattleHandler;
import nostale.handler.InventoryHandler;
import nostale.handler.MapHandler;
import nostale.handler.SpecialistHandler;
import nostale.handler.WalkHandler;
import nostale.util.Pos;

public class Bot {
	public Player bot;
	public Thread thread;
	private boolean run = false;
	private boolean threadRun = false;
	private boolean shouldBeSitting = false;
	private boolean shouldBeBotting = true;
	private boolean goingForItem = false;
	private boolean initialized = false;
	
	public Statistics statistics;

	public Bot(AccountData botData) {

		bot = new Player(botData);
		statistics = new Statistics(bot);
		MapHandler mapHandler = new MapHandler(bot);
		InventoryHandler inventoryHandler = new InventoryHandler(bot);
		SpecialistHandler specialistHandler = new SpecialistHandler(bot);
		BattleHandler battleHandler = new BattleHandler(bot);
		WalkHandler walkHandler = new WalkHandler(bot);
		// Map control
		bot.addGameEventListener(new GameEventListener(bot) {
			public void eventCall(GameEvent p) {
				if (p instanceof MapoutGameEvent) {
					// TODO end bot
				}
			}
		});
		// Map control END

		// Admin control
		bot.addGameEventListener(new GameEventListener(bot) {
			public void eventCall(GameEvent p) {
				// TODO end bot
			}
		});
		// Admin control END

		//Item drop getting
		bot.addGameEventListener(new GameEventListener(bot)
		{
			public void eventCall(GameEvent p)
			{
				if(p instanceof ItemDropGameEvent)
				{
					goingForItem = true;
				}
			}
		});
		//Item drop getting END
		new java.util.Timer("BotTimer").schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				initialized = true;
			}
		}, 5000);
		// specialistHandler.putOn();
		thread = new Thread() {
			@Override
			public void run() {
				while (threadRun == true) {
					while (run == true) {
						bot.loop();

						if (initialized) {
							try {

								// TODO items getting
								// TODO items selling
								// TODO sitting,eating,healing by pots
								if (shouldBeBotting == true && goingForItem == false && shouldBeSitting == false) {
									if (battleHandler.target == null)
										battleHandler.target = Pos.GetNearestMob(bot);
									SkillData skillToBeUsed = battleHandler.baseSkill;
									if (Pos.getRange(bot.pos, battleHandler.target.Pos) > skillToBeUsed.Range)
										walkHandler.Walk(Pos.getShortestPosInRange(skillToBeUsed.Range,
												battleHandler.target.Pos, bot.pos));
									else
										battleHandler.useSkill(skillToBeUsed);
								}
								
								if(goingForItem==true)
								{
									boolean turnOff = false;
									MapItemInstance item;
									for(int i = 0;i<bot.map.Items.size();i++){
									item = bot.map.Items.values().toArray(new MapItemInstance[0])[i];
									if(item!=null && (item.OwnerID==0 || item.OwnerID==bot.id)){
										turnOff = true;
										if(Pos.getRange(item.Pos, bot.pos)>1)
											walkHandler.Walk(item.Pos);
										else
										{
											bot.sendPacket("get 1 "+bot.id+" "+item.id);
											bot.map.Items.remove(item.id);
										}
												
										break;
									}
									if(turnOff==false){
										goingForItem = false;
										System.out.println("Going for item=false");
									}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

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

	public void run() {
		this.run = true;
		this.threadRun = true;
		if (!thread.isAlive())
			thread.start();
	}

	public void stop() {
		this.run = false;
		this.threadRun = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void restart() {
		stop();
		run();
	}

}
