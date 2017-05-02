package behaviors;

import client.RobotControlCenter;
import client.RobotControlCenter.Direction;
import lejos.robotics.subsumption.Behavior;
import shared.Configuration;

public class Right implements Behavior {
	
	private RobotControlCenter rcc;
	private static final float DISTANCE = Configuration.ROBOT_ADJUSTING_DISTANCE;
	private static final float FAIL_MARGIN = Configuration.ROBOT_ADJUSTING_FAIL_MARGIN;
	private boolean right = false;
	private boolean left = false;
	
	public Right(RobotControlCenter rcc) {
		this.rcc = rcc;
	}

	@Override
	public boolean takeControl() {	
		float range = this.rcc.getUltraSonicSample();
		return !(DISTANCE + FAIL_MARGIN >= range && DISTANCE - FAIL_MARGIN <= range);
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
		
	}
}
