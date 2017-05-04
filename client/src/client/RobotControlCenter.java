package client;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class RobotControlCenter {
	
private static RobotControlCenter instant;

private final EV3 brick;

private final RegulatedMotor leftMotor;
private final RegulatedMotor rightMotor;
private final RegulatedMotor smallMotor;

private final EV3UltrasonicSensor ultraSonic;
private final EV3TouchSensor frontTouch;
private final EV3TouchSensor backTouch;

private  MovePilot pilot;
private final Chassis chassis;

private final RangeFinderAdapter rfa;

	private RobotControlCenter(){		
		
		brick = (EV3) BrickFinder.getDefault();
		
		leftMotor = new EV3LargeRegulatedMotor(brick.getPort(("D")));
		rightMotor = new EV3LargeRegulatedMotor(brick.getPort(("B")));
		smallMotor = new EV3MediumRegulatedMotor(brick.getPort(("C")));
		
		ultraSonic = new EV3UltrasonicSensor(brick.getPort(("S1")));
		
		frontTouch = new EV3TouchSensor(brick.getPort(("S2")));
		backTouch = new EV3TouchSensor(brick.getPort(("S3")));
		
		double wheelDiameter = MovePilot.WHEEL_SIZE_EV3;
		double robotWidth = 26;
		
		Wheel wheelLeft = WheeledChassis.modelWheel(leftMotor, wheelDiameter).offset((-robotWidth) / 2);
		Wheel wheelRight = WheeledChassis.modelWheel(rightMotor, wheelDiameter).offset(robotWidth / 2);
		
		chassis = new WheeledChassis(new Wheel[] {wheelLeft, wheelRight}, WheeledChassis.TYPE_DIFFERENTIAL);
		
		pilot = new MovePilot(chassis);
		
		rfa = new RangeFinderAdapter(ultraSonic);
		pilot.setLinearSpeed(20);
		pilot.setAngularSpeed(20);
	}
	
	public void rotate(int angle) {
		this.pilot.rotate(angle);
	}
	
	public void rotate(int angle, boolean immediateReturn) {
		this.pilot.rotate(angle, immediateReturn);
	}
	
	public void forward(){
		this.pilot.forward();
	}
	
	public void forward(double distance) {
		this.pilot.travel(distance);
	}
	
	public void backward(){
		this.pilot.backward();
	}
	
	public void stop(){
		this.pilot.stop();
	}
	
	public boolean isMoving(){
		return this.pilot.isMoving();
	}
	
	public static RobotControlCenter getInstans(){
		if (instant == null) {
			instant = new RobotControlCenter();
		}
		return instant;
	}
	
	public float getDistance(){
		return this.rfa.getRange();
	}

}
