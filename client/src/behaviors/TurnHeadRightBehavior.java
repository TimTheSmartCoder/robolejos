package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class TurnHeadRightBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	private int angle = 90;
	private final int MAX_RIGHT_ROTATE = 90;
	
	public TurnHeadRightBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.HEAD_RIGHT))
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
		System.out.println("Right angle: " + this.rcc.getCurentHeadAngle());
		// TODO Auto-generated method stub
		if(!this.rcc.isHeadMoving() && this.rcc.getCurentHeadAngle() < MAX_RIGHT_ROTATE){
			this.rcc.rotateHead(angle);
		}
		else if(this.rcc.getCurentHeadAngle() >= MAX_RIGHT_ROTATE){
			Message errorMessage = new Message(Commands.UNABLE_TO_ROTATE_RIGHT, null);
			this.messageService.send(errorMessage);
		}
		message = null;	
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		this.rcc.stopHead();
	}
	

}
