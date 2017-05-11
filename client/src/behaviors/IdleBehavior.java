package behaviors;

import java.util.ArrayList;
import java.util.List;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;
import messageservice.Commands;
import messageservice.MessageService;
import messageservice.MessageService.Message;


public class IdleBehavior implements Behavior{

	private final MessageService messageService;
	private final RobotControlCenter rcc;
	private long lastUpdate = 0;
	
	public IdleBehavior(RobotControlCenter rcc, MessageService messageService) {
		this.messageService = messageService;
		this.rcc = rcc;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action() {
		
		long update = System.currentTimeMillis() / 1000l;
		if (update - lastUpdate >= 1) {
			List<Float> data = new ArrayList<>();
			data.add((float) rcc.getCurrentSpeed());
			data.add(rcc.getTraveledDistance());
			data.add((float) rcc.getMaxSpeed());
			data.add((float) rcc.getDistance());
			messageService.send(new Message(Commands.DATA, data));
			lastUpdate = update;
		}
		
		this.messageService.run();
	}

	@Override
	public void suppress() {
		
	}
}
