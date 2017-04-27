package client;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class MoveController {
	
	private final RegulatedMotor leftMotor;
	private final RegulatedMotor rightMotor;
	private MovePilot movePilot;
	
	private static final int ROTATION_SPEED = 15;
	private static final int LINEAR_SPEED = 60; 
	
	public MoveController(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	
		this.leftMotor.synchronizeWith(new RegulatedMotor[] { this.rightMotor });
		this.leftMotor.resetTachoCount();
		this.rightMotor.resetTachoCount();
		
		this.setupMovePilot();
		this.setup();
	}
	
	private void setup() {
		this.setAngularSpeed(ROTATION_SPEED);
		this.setLinearSpeed(LINEAR_SPEED);
	}
	
	private void setupMovePilot() {
		//Move pilot config.
		double wheelDiameter = MovePilot.WHEEL_SIZE_EV3;
		double robotWidth = 12;
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
		this.leftMotor.startSynchronization();
		this.leftMotor.forward();
		this.rightMotor.forward();
		this.leftMotor.endSynchronization();
	}
	
	public void forward(double distance) {
		this.movePilot.travel(distance);
	}
	
	public void backward() {
		this.leftMotor.startSynchronization();
		this.leftMotor.backward();
		this.rightMotor.backward();
		this.leftMotor.endSynchronization();
	}
	
	public void rotate(int angle) {
		this.movePilot.rotate(angle);
	}
	
	public void setAngularSpeed(int speed) {
		this.movePilot.setAngularSpeed(speed);
	}
	
	public void setLinearSpeed(int speed) {
		this.movePilot.setLinearSpeed(speed / 10);
		this.leftMotor.setSpeed(speed);
		this.rightMotor.setSpeed(speed);
	}
}
