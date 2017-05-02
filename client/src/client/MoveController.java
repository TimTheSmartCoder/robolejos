package client;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import shared.Configuration;

public class MoveController {
	
	/**
	 * Regulated Motors of the robot.
	 */
	private final RegulatedMotor leftMotor;
	private final RegulatedMotor rightMotor;
	
	private Direction direction;
	
	/**
	 * Enum for representing the motors.
	 */
	public enum Motor {
		LEFT,
		RIGHT
	}
	
	public enum Direction {
		LEFT,
		RIGHT,
		FORWARD,
		BACKWARD
	}
	
	/**
	 * MovePilot to control the robot.
	 */
	private MovePilot movePilot;
	
	/**
	 * Constructs an MoveController with the given motors.
	 * @param leftMotor - Left motor.
	 * @param rightMotor - Right motor.
	 */
	public MoveController(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	
		
		this.setupMovePilot();
		this.setup();
	}
	
	private void setup() {
		
		//Reset the left and right motor tacho count for more precise start.
		this.leftMotor.resetTachoCount();
		this.rightMotor.resetTachoCount();
		
		this.setAngularSpeed(Configuration.ROBOT_ADJUSTING_SPEED);
		this.setLinearSpeed(Configuration.ROBOT_LINEAR_SPEED);
	}
	
	private void setupMovePilot() {
		//Move pilot config.
		double wheelDiameter = MovePilot.WHEEL_SIZE_EV3;
		double robotWidth = 24;
		Wheel wheelLeft = WheeledChassis.modelWheel(leftMotor, wheelDiameter).offset((-robotWidth) / 2);
		Wheel wheelRight = WheeledChassis.modelWheel(rightMotor, wheelDiameter).offset(robotWidth / 2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wheelLeft, wheelRight}, WheeledChassis.TYPE_DIFFERENTIAL);
		this.movePilot = new MovePilot(chassis);
	}
	
	public boolean isMoving() {
		return this.movePilot.isMoving();
	}
	
	public void stop() {
		this.movePilot.stop();
	}
	
	public void forward() {
		System.out.println("forwd method.");
		this.movePilot.forward();
	}
	
	public void forward(double distance) {
		this.movePilot.travel(distance);
	}
	
	public void backward() {
		this.movePilot.backward();
	}
	
	public void rotate(int angle) {
		this.movePilot.rotate(angle);
	}
	
	public void setAngularSpeed(int speed) {
		this.movePilot.setAngularSpeed(speed);
	}
	
	public void setLinearSpeed(int speed) {
		this.movePilot.setLinearSpeed(speed);
	}
}
