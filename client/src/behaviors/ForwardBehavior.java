package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class ForwardBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	
	public ForwardBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.FORWARD))
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
		if (!this.rcc.isMoving()) {
			this.rcc.forward();
			System.out.println("Driving forward");
		}
		message = null;
	}

	@Override
	public void suppress() { 
		this.rcc.stop();
	}
	
}
