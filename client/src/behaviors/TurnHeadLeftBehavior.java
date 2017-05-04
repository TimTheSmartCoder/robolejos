package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class TurnHeadLeftBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	private int angle = -1;
	private final int MAX_LEFT_ROTATE = -90;
	
	public TurnHeadLeftBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.HEAD_LEFT))
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
		if(!this.rcc.isHeadMoving() && this.rcc.getCurentHeadAngle() > MAX_LEFT_ROTATE){
			this.rcc.rotateHead(angle);
		}
		else if(this.rcc.getCurentHeadAngle() == MAX_LEFT_ROTATE){
			Message errorMessage = new Message(Commands.UNABLE_TO_ROTATE_LEFT, null);
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
