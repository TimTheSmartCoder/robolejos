package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;

public class ForwardBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private String message;
	
	public ForwardBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(String msg) {
				if (msg.equals("forward"))
					message = msg;
			}
		});
	}

	@Override
	public boolean takeControl() {
		return message != null;
	}

	@Override
	public void action() {
		if (!this.rcc.isMoving())
			this.rcc.forward();
		message = null;
	}

	@Override
	public void suppress() { 
		this.rcc.stop();
	}
	
}
