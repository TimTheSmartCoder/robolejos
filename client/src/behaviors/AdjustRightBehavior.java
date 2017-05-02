package behaviors;

import Arbitrator.Arbitrator.Behavior;
import client.RobotControlCenter;
import shared.Configuration;

public class AdjustRightBehavior implements Behavior {

	private RobotControlCenter rcc;
	
	public AdjustRightBehavior(RobotControlCenter rcc) {
		this.rcc = rcc;
	}
	
	@Override
	public boolean takeControl() {
		float range = this.rcc.getUltraSonicSample();
		return range > Configuration.ROBOT_ADJUSTING_DISTANCE + Configuration.ROBOT_ADJUSTING_FAIL_MARGIN;
	}

	@Override
	public void action() {
		this.rcc.rotate(10);
		if (!this.rcc.isMoving())
			this.rcc.forward(10);
		System.out.println("Adjust right action.");
	}

	@Override
	public void suppress() {
		this.rcc.stop();
	}
}