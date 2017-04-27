package client;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
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

private final MoveController moveController;

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
		
		//pilot = new MovePilot(chassis);
		
		rfa = new RangeFinderAdapter(ultraSonic);
		//pilot.setLinearSpeed(20);
		//pilot.setAngularSpeed(20);
		
		this.moveController = new MoveController(leftMotor, rightMotor);
	}
	
	
	public void test() {
		this.leftMotor.synchronizeWith(new RegulatedMotor[] { this.rightMotor });
		this.leftMotor.setSpeed(100);
		this.rightMotor.setSpeed(100);
		this.leftMotor.startSynchronization();
		this.leftMotor.forward();
		this.rightMotor.forward();
		this.leftMotor.endSynchronization();
	}
	
	public enum Direction {
		LEFT,
		RIGHT,
		INFORNT,
		BEHIND
	}
	
	private static final int LEFT_ROTATION = -180;
	private static final int RIGHT_ROTATION = 0;
	private static final int INFRONT_ROTATION = -90;
	private static final int BEHIND_ROTATION = 90;
	
	public float getUltraSonicSample() {
		return this.rfa.getRange();
	}
	
	public boolean isObject(Direction direction, float distance) {
		
		int rotation = getDirectionAngle(direction);
		
		if (rotation == 0)
			this.smallMotor.rotate(rotation);
		
		boolean result = this.rfa.getRange() <= distance;
		
		if (rotation == 0)
			this.smallMotor.rotate(-rotation);
		
		return result;
	}
	
	private int getDirectionAngle(Direction direction) {
		switch (direction) {
		case LEFT:
			return LEFT_ROTATION;
		case RIGHT:
			return RIGHT_ROTATION;
		case BEHIND:
			return BEHIND_ROTATION;
		case INFORNT:
			return INFRONT_ROTATION;
		default:
			return 0;
		}
	}
	
	public void rotate(int angle) {
		this.moveController.rotate(angle);
	}
	
	public void forward(){
		this.moveController.forward();
	}
	
	public void forward(double distance) {
		this.moveController.forward(distance);
	}
	
	public void stop(){
		this.moveController.stop();
	}
	
	public boolean isMoving(){
		return this.moveController.isMoving();
	}
	
	public void setAngularSpeed(int speed) {
		this.moveController.setAngularSpeed(speed);
	}
	
	public static RobotControlCenter getInstans(){
		if (instant == null) {
			instant = new RobotControlCenter();
		}
		return instant;
	}

}
