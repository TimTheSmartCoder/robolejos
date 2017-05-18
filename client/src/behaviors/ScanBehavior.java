package behaviors;

import java.util.ArrayList;
import java.util.List;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.IMessageListener;
import messageservice.MessageService.Message;

public class ScanBehavior implements Behavior {
	
	private final RobotControlCenter rcc;
	private final MessageService messageService;
	private Message message;
	
	private int headTurn = 10;
	private int headSteps = 180/headTurn;
	private List<Float> data = new ArrayList<>();
	
	public ScanBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.rcc = rcc;
		this.messageService = messageService;
		this.subscribe();
	}
	
	public void subscribe() {
		messageService.addMessageListener(new IMessageListener() {
			@Override
			public void onMessageReceived(Message msg) {
				if (msg.command.equals(Commands.SCAN))
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
		this.data.clear();
		int current = this.rcc.getCurentHeadAngle();
		int turn = -90-current;
		this.rcc.rotateHead(turn, false);
		for(int i = 0; i < this.headSteps; i ++){
			this.data.add(rcc.getDistance());
			this.rcc.rotateHead(headTurn, false);
		}
		this.messageService.send(new Message(Commands.SCAN, data));
		this.rcc.rotateHead(-90, false);
		this.message = null;	
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
