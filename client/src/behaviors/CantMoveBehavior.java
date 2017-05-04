package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.Message;

public class CantMoveBehavior implements Behavior {

	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private static final float MIN_DISTANCE = 10;
	private float distance;
	private boolean state = false;
	
	public CantMoveBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		this.distance = this.rcc.getDistance();
		boolean result = distance <= this.MIN_DISTANCE;
		if(!result && state){
			this.state = false;
		}
			
		return distance <= this.MIN_DISTANCE && this.rcc.isMoving();
	}

	@Override
	public void action() {

		if(!state){
			this.rcc.stop();
			this.state = true;
			Message message = new Message(Commands.UNABLE_TO_MOVE, this.distance);
			this.messageService.send(message);
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		this.state = false;
	}

}
