/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6823.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PWMSpeedController;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive m_myRobot;
	private Joystick m_leftStick;
//	private Joystick m_rightStick;

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		m_myRobot = new DifferentialDrive(new Spark(0), new Spark(1));
		m_leftStick = new Joystick(0);
	//	m_rightStick = new Joystick(1);
	}

	@Override
	public void teleopPeriodic() {
		//m_myRobot.tankDrive(m_leftStick.getRawAxis(5)*1*m_leftStick.getRawAxis(2), m_leftStick.getRawAxis(1)*1*m_leftStick.getRawAxis(2));
		//m_myRobot.tankDrive(m_leftStick.getRawAxis(5), m_leftStick.getRawAxis(1));
		m_myRobot.arcadeDrive(m_leftStick.getRawAxis(1)*.75, m_leftStick.getRawAxis(2)*.75);
		//hans
	}
	
	public void autonomous() {
		m_myRobot.arcadeDrive(1, 1);
		Timer.delay(2);
		m_myRobot.arcadeDrive(0, 0);
		
	}
}
