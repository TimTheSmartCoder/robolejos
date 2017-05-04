package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class RightBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	
	public RightBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.RIGHT))
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
		// TODO Auto-generated method stub
		this.rcc.rotate(360, true);
		
		message = null;
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		this.rcc.stop();
	}

}
