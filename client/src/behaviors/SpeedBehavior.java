package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class SpeedBehavior implements Behavior {
	
	private RobotControlCenter rcc;
	private MessageService messageService;
	private Message message;
	
	public SpeedBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.messageService = messageService;
		this.rcc = rcc;
		this.Subscribe();
	}
	
	public void Subscribe(){
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.SET_SPEED))
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
		float speedIncrease = (float) message.value;
		this.rcc.setCurrentSpeed(speedIncrease);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
