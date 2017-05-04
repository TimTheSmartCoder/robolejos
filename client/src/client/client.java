package client;

import behaviors.BackwardsBehavior;
import behaviors.CantMoveBehavior;
import behaviors.ForwardBehavior;
import behaviors.IdleBehavior;
import behaviors.LeftBehavior;
import behaviors.RightBehavior;
import behaviors.StopBehavior;
import behaviors.TurnHeadLeftBehavior;
import behaviors.TurnHeadRightBehavior;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import messageservice.MessageService;

public class client {

	public static void main(String[] args) {
		RobotControlCenter rcc = RobotControlCenter.getInstans();
		MessageService messageService = new MessageService("192.168.1.56", 6801, 6800);
		messageService.initialize();
		
		Arbitrator arbitrator = new Arbitrator(new Behavior[] { 
				new IdleBehavior(messageService),  
				new ForwardBehavior(rcc, messageService),
				new RightBehavior(rcc, messageService),
				new LeftBehavior(rcc, messageService),
				new BackwardsBehavior(rcc, messageService),
				new StopBehavior(rcc, messageService),
				new CantMoveBehavior(rcc, messageService),
				new TurnHeadLeftBehavior(rcc, messageService),
				new TurnHeadRightBehavior(rcc, messageService)
		});
		
		arbitrator.go();
	}

}
