package behaviors;

import client.RobotControlCenter;
import Arbitrator.Arbitrator.Behavior;
import client.MoveController.Motor;
import shared.Configuration;

public class DriveForward implements Behavior {
	private RobotControlCenter rcc;
	private static final float DISTANCE = Configuration.ROBOT_ADJUSTING_DISTANCE;
	private static final float FAIL_MARGIN = Configuration.ROBOT_ADJUSTING_FAIL_MARGIN;
	
	public DriveForward(RobotControlCenter rcc){
		this.rcc = rcc;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() { 
		if (!this.rcc.isMoving())
			this.rcc.forward();
	}

	@Override
	public void suppress() {
		System.out.println("supress Forward");		
	}

}
