/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6823.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PWMSpeedController;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private Timer timer;
	Command autonomousCommand;
	private DifferentialDrive myRobot;
	private DifferentialDrive m_myRobot;
	private DifferentialDrive m_myAttach;
	private double var;
	private Joystick m_leftStick;
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	REVDigitBoard disp = new REVDigitBoard();
	Encoder encoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k4X);  // create new encoder on inputs 0 and 1
	Encoder encoderRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X); // create new encoder on inputs 2 and 3
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		m_myRobot = new DifferentialDrive(new Spark(0), new Spark(1));
		m_myAttach = new DifferentialDrive(new Spark(2), new Spark(3));
		m_leftStick = new Joystick(0);
		//SmartDashboard.putNumber("reee", .5);
		CameraServer.getInstance().startAutomaticCapture();
		//disp.display("reee");

		encoderLeft.setDistancePerPulse(0.05235987755);  // 360 pulses per revolution   -=----  should be 0.05235987755 for 6 in =-==== 0.03490658503 4 in
		encoderRight.setDistancePerPulse(0.05235987755); // 360 pulses per revolution   -=----  should be 0.05235987755 for 6 in =-==== 0.03490658503 4 in
		encoderLeft.setSamplesToAverage(3);
		encoderRight.setSamplesToAverage(3);
		encoderLeft.reset();
		encoderRight.reset();
		double leftRate = encoderLeft.getRate();
		double rightRate = encoderRight.getRate();
		
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
	
		// m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		// System.out.println("Auto selected: " + m_autoSelected);
		// Autonomous command
		if (autonomousCommand !=null) autonomousCommand.start();
		// Restart autonomous timer and pattern command
		// timer.reset();
	    // System.out.println("test");
		timer.start();
		CameraServer.getInstance().startAutomaticCapture();
		m_myRobot = new DifferentialDrive(new Spark(0), new Spark(1));
		m_myAttach = new DifferentialDrive(new Spark(2), new Spark(3));
		m_leftStick = new Joystick(0);
		 SmartDashboard.putNumber("reee", .5);  // smartdashboard test
		CameraServer.getInstance().startAutomaticCapture();   // startup camera server
		// disp.display("reee");
		 
		if(timer.get()<3 && timer.get()>4)
			{
			m_myRobot.curvatureDrive(1, 1, true);
			}
			else
			{m_myRobot.tankDrive(.5, .5) ;}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//switch (m_autoSelected) {
		//	case kCustomAuto:
		//		// Put custom auto code here
		//		break;
		//	case kDefaultAuto:
		//	default:
		//		// Put default auto code here
		//		break;
		//if (timer.get() < 2.0) {
		////auto();
		////m_myRobot.tankDrive(var,var);
		//m_myRobot.tankDrive(1, 1);

		//} else {
		//	m_myRobot.tankDrive(0.0, 0.0);
		 
		 SmartDashboard.putNumber("reee", var);
		 var = SmartDashboard.getNumber("reee", .0);

		
		}
	//}
	
	

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//if(m_leftStick.getRawAxis(5)>0)
			//m_myRobot.tankDrive(m_leftStick.getRawAxis(5)/1.6, m_leftStick.getRawAxis(5), false);
		//else
			m_myRobot.tankDrive(m_leftStick.getRawAxis(5), m_leftStick.getRawAxis(1), false);
		//m_myAttach.tankDrive(m_leftStick.getRawAxis(2)*-1, m_leftStick.getRawAxis(3));
		//m_myRobot.arcadeDrive(m_leftStick.getRawAxis(1), m_leftStick.getRawAxis(2));

		auto();
		m_myRobot.tankDrive(var*.6,var,false);
		
		SmartDashboard.putNumber("Left Velocity", encoderLeft.getRate()/12.0);  // send left drive encoder rate to smart dashboard
		SmartDashboard.putNumber("Right Velocity", encoderRight.getRate()/12.0);  // send right drive encoder rate to smart dashboard
		
	}

	
	public void auto() {
		// SmartDashboard dash = new SmartDashboard();
		// SmartDashboard.putNumber("Something", m_leftStick.getRawAxis(2));
	
		// dash.putNumber("Something", .69);
		
	}

	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
