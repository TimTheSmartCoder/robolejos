package behaviors;

import lejos.robotics.subsumption.Behavior;
import messageservice.MessageService;


public class IdleBehavior implements Behavior{

	private final MessageService messageService;
	
	public IdleBehavior(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action() {
		this.messageService.run();
	}

	@Override
	public void suppress() {
		
	}
}
