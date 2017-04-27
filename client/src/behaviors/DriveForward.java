package behaviors;

import client.RobotControlCenter;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {
	private RobotControlCenter rcc;
	
	public DriveForward(RobotControlCenter rcc){
		this.rcc = rcc;
	}

	@Override
	public boolean takeControl() {
		
		return true;
	}

	@Override
	public void action() {
		if(!rcc.isMoving())
			rcc.forward();		
	}

	@Override
	public void suppress() {
		rcc.stop();		
	}

}
