package behaviors;

import Arbitrator.Arbitrator.Behavior;
import client.MoveController.Motor;
import client.RobotControlCenter;
import shared.Configuration;

public class AdjustLeftBehavior implements Behavior {

	private RobotControlCenter rcc;
	
	public AdjustLeftBehavior(RobotControlCenter rcc) {
		this.rcc = rcc;
	}
	
	@Override
	public boolean takeControl() {
		float range = this.rcc.getUltraSonicSample();
		return range > 30;
	}

	@Override
	public void action() {
		this.rcc.forward(10);
		this.rcc.rotate(90);
		this.rcc.forward(10);
	}

	@Override
	public void suppress() {
		this.rcc.stop();
	}
}
