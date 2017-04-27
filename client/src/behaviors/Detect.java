package behaviors;

import client.RobotControlCenter;
import client.RobotControlCenter.Direction;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	private RobotControlCenter rcc;
	private static final float DISTANCE = 10;
	
	public Detect(RobotControlCenter rcc){
		this.rcc = rcc;
		
	}

	@Override
	public boolean takeControl() {
		float range = this.rcc.getUltraSonicSample();
		System.out.println(!(DISTANCE + 2 >= range && DISTANCE - 2 <= range));
		return !(DISTANCE + 2 >= range && DISTANCE - 2 <= range);
	}

	@Override
	public void action() {
		
		float range = this.rcc.getUltraSonicSample();
		
		if (DISTANCE - 2 < range) {
			this.rcc.rotate(+10);
			this.rcc.forward(5);
		} else if (DISTANCE + 2 > range) {
			this.rcc.rotate(-10);
			this.rcc.forward(5);
		}
	}

	@Override
	public void suppress() {
		rcc.stop();
	}
}
