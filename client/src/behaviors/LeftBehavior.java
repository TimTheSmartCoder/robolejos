package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;

public class LeftBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private String message;
	
	public LeftBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(String msg) {
				if (msg.equals("left"))
					message = msg;
			}
		});
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return message != null;
	}

	@Override
	public void action() {
		this.rcc.rotate(-360, true);
		message = null;
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
