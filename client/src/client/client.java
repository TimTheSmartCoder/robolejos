package client;

import Arbitrator.Arbitrator;
import Arbitrator.Arbitrator.Behavior;
import behaviors.AdjustLeftBehavior;
import behaviors.AdjustRightBehavior;
import behaviors.DriveForward;
import behaviors.Right;

import java.util.ArrayList;
import java.util.List;

public class client {

	public static void main(String[] args) {
		RobotControlCenter rcc = RobotControlCenter.getInstans();
		List<Behavior> behaviorList = new ArrayList<Behavior>();

		behaviorList.add(new DriveForward(rcc));
		behaviorList.add(new AdjustLeftBehavior(rcc));
		
		Arbitrator arb = new Arbitrator(behaviorList);
		
		//rcc.forward();
		arb.run();
	}

}
