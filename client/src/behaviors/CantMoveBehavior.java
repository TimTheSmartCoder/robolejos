package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class CantMoveBehavior implements Behavior {

	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	private boolean cantMove;
	private static final float MIN_DISTANCE = 10;
	
	public CantMoveBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
	}
	
	public void ultrasonicDistance(){
		float distance = this.rcc.getDistance();
		
		if(distance <= this.MIN_DISTANCE){
			this.cantMove = true;
		}
		else
			this.cantMove = false;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return cantMove;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
