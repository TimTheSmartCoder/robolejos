package client;

import behaviors.Detect;
import behaviors.DriveForward;
import behaviors.Right;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class client {

	public static void main(String[] args) {
		RobotControlCenter rcc = RobotControlCenter.getInstans();
		
		Behavior[] behaviorList = new Behavior[] {new DriveForward(rcc), new Detect(rcc), new Right(rcc)};
		
		Arbitrator arb = new Arbitrator(behaviorList);
		
		arb.go();

	}

}
