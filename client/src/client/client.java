package client;

import behaviors.BackwardsBehavior;
import behaviors.CantMoveBehavior;
import behaviors.ForwardBehavior;
import behaviors.IdleBehavior;
import behaviors.LeftBehavior;
import behaviors.RightBehavior;
import behaviors.ScanBehavior;
import behaviors.SoundBehavior;
import behaviors.StopBehavior;
import behaviors.StopHeadBehavior;
import behaviors.TurnHeadLeftBehavior;
import behaviors.TurnHeadRightBehavior;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class client {

	public static Message message;
	
	public static void main(String[] args) {
		RobotControlCenter rcc = RobotControlCenter.getInstans();
		MessageService messageService = new MessageService("192.168.1.56", 6801, 6800);
		messageService.initialize();
		
		Arbitrator arbitrator = new Arbitrator(new Behavior[] { 
				new IdleBehavior(rcc, messageService),
				new SoundBehavior(rcc, messageService),
				new StopBehavior(rcc, messageService),
				//new StopHeadBehavior(rcc, messageService),
				new ForwardBehavior(rcc, messageService),
				new RightBehavior(rcc, messageService),
				new LeftBehavior(rcc, messageService),
				new BackwardsBehavior(rcc, messageService),
				new CantMoveBehavior(rcc, messageService),
				new TurnHeadLeftBehavior(rcc, messageService),
				new TurnHeadRightBehavior(rcc, messageService),
				new ScanBehavior(rcc, messageService)
		});
		
		try {
			arbitrator.go();
		} catch(Exception e) {
			System.out.println("Crash !!!!");
			e.printStackTrace();
			rcc.stop();
		}
	}

}
