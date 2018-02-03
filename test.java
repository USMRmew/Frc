@@ -1,51 +1,61 @@
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6823.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PWMSpeedController;

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
public class Robot extends IterativeRobot {
	private DifferentialDrive m_myRobot;
	private Joystick m_leftStick;
	//	private Joystick m_rightStick;

	@Override
	public class Robot extends SampleRobot {
		RobotDrive myRobot = new RobotDrive(1, 2, 3, 4); // class that handles basic drive
		// operations
		Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation, Using Port 0
		//	Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation

		public void robotInit() {
			CameraServer.getInstance().startAutomaticCapture();
			m_myRobot = new DifferentialDrive(new Spark(0), new Spark(1));
			m_myAttach = new DifferentialDrive(new Spark(2), new Spark(3));
			m_leftStick = new Joystick(0);
			//	m_rightStick = new Joystick(1);
			CameraServer.getInstance().startAutomaticCapture();
		}

		// Single-called method to begin the autonomous period for the robot
		public void autonomousInit() {
			// Autonomous command
			if (autonomousCommand !=null) autonomousCommand.start();
			// Restart autonomous timer and pattern command
			timer.reset();
			timer.start();
		}
		
		// Autonomous method that is called from the autonomousInit method
		public void autonomousPeriodic() {
			if (timer.get() < 2.0) {
				myRobot.drive(0.5, 0.0);
			} else {
				myRobot.drive(0.0, 0.0);
			}
		}	
				
		}
	}
		
		public Robot() {
			myRobot.setExpiration(0.1);
		}

		@Override
		public void teleopPeriodic() {
			//m_myRobot.tankDrive(m_leftStick.getRawAxis(5)*1*m_leftStick.getRawAxis(2), m_leftStick.getRawAxis(1)*1*m_leftStick.getRawAxis(2));
			m_myRobot.tankDrive(m_leftStick.getRawAxis(5), m_leftStick.getRawAxis(1));
			//m_myRobot.arcadeDrive(m_leftStick.getRawAxis(1)*.75, m_leftStick.getRawAxis(2)*.75);
			m_myAttach.tankDrive(m_leftStick.getRawAxis(2)*-1, m_leftStick.getRawAxis(3));
		}
	}
