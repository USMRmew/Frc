package org.usfirst.frc.team6823.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	RobotDrive myRobot = new RobotDrive(1, 2, 3, 4); // class that handles basic drive
												// operations
	Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation, Using Port 0
//	Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation
	
	public void robotInit() {
        CameraServer.getInstance().startAutomaticCapture();
    }
	
	public Robot() {
		myRobot.setExpiration(0.1);
	}

	/**
	 * Runs the motors with mechanum steering.
	 */
	
	public void autoControl() {
	while (isOperatorControl()==false && isEnabled())	{
		//myRobot.mecanumDrive_Cartesian(x, y, rotation, gyroAngle); //Sample, to add based on measured dimensions.
		//myRobot.exchange(tbd);
        //while(distanceTraveled< distanceToLine){ (forward)	
	}
	}
	@Override

	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		
		while (isOperatorControl() && isEnabled()) {
			myRobot.mecanumDrive_Cartesian((leftStick.getX())*-1, (leftStick.getY())*1, (leftStick.getTwist())*1,0);
			Timer.delay(0.0005); // wait for a motor update time

		}
	}
}
