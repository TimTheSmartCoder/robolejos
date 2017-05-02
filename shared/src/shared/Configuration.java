package shared;

public class Configuration {
	
	/**
	 * Speed of the robot when it is driving in a linear direction.
	 */
	public static final int ROBOT_LINEAR_SPEED = 50;
	
	/**
	 * Speed of the robot when it is turning.
	 */
	public static final int ROBOT_ANGULAR_SPEED = 30;
	
	/**
	 * Speed of the robot when adjusting its direction.
	 */
	public static final int ROBOT_ADJUSTING_SPEED = ROBOT_LINEAR_SPEED + 40;
	
	/**
	 * The distance for the auto adjusting of the robots direction.
	 */
	public static final float ROBOT_ADJUSTING_DISTANCE = 10;
	
	/**
	 * The fail margin for the auto adjusting of the robots direction.
	 */
	public static final float ROBOT_ADJUSTING_FAIL_MARGIN = 1;
}
