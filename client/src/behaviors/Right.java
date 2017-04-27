package behaviors;

import client.RobotControlCenter;
import client.RobotControlCenter.Direction;
import lejos.robotics.subsumption.Behavior;

public class Right implements Behavior {
	
	private RobotControlCenter rcc;
	
	public Right(RobotControlCenter rcc) {
		this.rcc = rcc;
	}

	@Override
	public boolean takeControl() {		
		return !this.rcc.isObject(Direction.RIGHT, 20);
	}

	@Override
	public void action() {
		this.rcc.rotate(90);
		this.rcc.forward(15);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
}
