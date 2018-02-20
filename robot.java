/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6823.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private Timer timer;
	private Timer autoTimer;
	private DifferentialDrive myRobot;
	private DifferentialDrive Robot; // Use TANKDRIVE and ARCADEDRIVE on this
	  	  private DifferentialDrive forkLift;// Used for the Forklift forkLiftment
	  	  private DifferentialDrive Intake;
	  	  private DifferentialDrive Climb;
	double Lmod = 1.0; // Modify to match ratios !NOT USED!
	double Rmod = 1.0; // Modify to match ratios !NOT USED!
	private double var;
	private Joystick m_driveStick;
	private Joystick m_operatorControl;
	Joystick driveStick = new Joystick(0);
	  	Joystick operatorControl = new Joystick(1);
	Command autonomousCommand;
	Encoder encoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k4X); // create new encoder on inputs 0 and 1
	// Encoder autoEncoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder encoderRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X); // create new encoder on inputs 2 and 3
	private ArrayList<Double> listLeft = new ArrayList<Double>();
	private ArrayList<Double> listRight = new ArrayList<Double>();
	private int count = 0;
	private boolean toggle1 = false;
	private boolean toggle2 = false;
	private double k = 0;
	private double intakeSpeed = 0;
	double leftMoreThanRight = 0;
	double speedDiference = 0;
	int forkLiftSpeed = 0;
	//I2C i2c = new I2C(Port.kMXP, 0x70);
	//DigitalInput buttonA = new DigitalInput(19);
	//DigitalInput buttonB = new DigitalInput(20);
	private final static int case1 = 1;
	private final static int case2 = 2;
	private double mode = 1;
	private int autoCase = 0;
	private boolean done = false;
	//REVDigitBoard digit = new REVDigitBoard();
	private AnalogGyro gyro = new AnalogGyro(0);
	

	
	
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		CameraServer.getInstance().startAutomaticCapture();
		/**
		 * Encoder connections for robot 0,1 for tankdriveMethod 2,3 for forkliftMethod
		 * 4,5 for intakeMethod
		 */
		Robot = new DifferentialDrive(new Spark(0), new Spark(1));
  		forkLift = new DifferentialDrive(new Spark(2), new Spark(3));
  		Intake = new DifferentialDrive(new Spark(4), new Spark(5));
		Climb = new DifferentialDrive(new Spark(6), new Spark(7));

		m_driveStick = new Joystick(0);
		m_operatorControl = new Joystick(1);

		SmartDashboard.putNumber("DriveSpeed", 1.0);
		SmartDashboard.putNumber("K", k);
		SmartDashboard.putNumber("Intake Speed ", intakeSpeed);
		// SmartDashboard.putBoolean("Calibrate", false);
		//CameraServer.getInstance().startAutomaticCapture();
		// disp.display("var");
		// SmartDashboard.putNumber("Kvalue", 0);
		encoderLeft.setDistancePerPulse(0.05235987755); // 360 pulses per revolution -=---- should be 0.05235987755 for
														// 6 in =-==== 0.03490658503 4 in
		encoderRight.setDistancePerPulse(0.05235987755); // 360 pulses per revolution -=---- should be 0.05235987755 for
															// 6 in =-==== 0.03490658503 4 in
		encoderLeft.setSamplesToAverage(11);
		encoderRight.setSamplesToAverage(11);
		encoderLeft.reset();
		encoderRight.reset();
		// double leftRate = encoderLeft.getRate();
		// double rightRate = encoderRight.getRate();
		
		gyro.calibrate();
		//gyro.reset();
		gyro.initGyro();
		
		SmartDashboard.putNumber("Starting Position (1 2 3)", 1.0);
		System.out.println(mode);
		//Robot.setSafetyEnabled(false);
		REVDigitBoard digit = new REVDigitBoard();
		digit.clear();
		digit.display(DriverStation.getInstance().getGameSpecificMessage());
		 
		
	
	//	i2c.free();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		if (autonomousCommand != null) autonomousCommand.start();
		mode = SmartDashboard.getNumber("Starting Position (1 2 3)", 1.0);
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		/**
		 * Nested if statements to determine and send autonomous code for robot
		 */
		done = false;
		if (mode == 1) {
			if (gameData.substring(0, 1).equals("L")) {
				// Autonomous code for 1L
				System.out.println("1L");
				autoCase = 4;
			} else if (gameData.substring(0, 1).equals("R")) {
				// Autonomous code for 1N
				System.out.println("1N");
				autoCase = 1;
			} else {
				System.out.println("AUTO ERROR");
			}
		} else if (mode == 2) {
			// Autonomous code for 2N
			System.out.println("2N");
			autoCase = 2;
		} else if (mode == 3) {
			if (gameData.substring(0, 1).equals("L")) {
				// Autonomous code for 3N
				System.out.println("3N");
				autoCase = 3;
			} else if (gameData.substring(0, 1).equals("R")) {
				// Autonomous code for 3R
				System.out.println("3R");
				autoCase = 5;
			} else {
				System.out.println("AUTO ERROR");
			}
		} else {
			System.out.println("CODE ERROR");
		}
	}

	/**
	 * Method used in autonomous for straight driving.
	 * 
	 * @param input
	 *            Amount to go forward in "Inches" (As the distance traveled increases, the scaler value of the goForward method decreases, meaning that if you want to
	 *            travel 130inches, you would need 150 entered for it to be successful.
	 */
	public void goForward(double input) {

		encoderRight.reset();
		// System.out.println("It worked");
		while (encoderRight.getDistance() < input*0.835) {
			Robot.tankDrive(-.3, -.3, false); // Speed to goForeward at
			SmartDashboard.putNumber("Encoder right test 1", encoderRight.getDistance()); // Bugfizing
		}
	}

	/**
	 * method to turn robot in clockwise direction
	 * 
	 * @param input
	 *            value either 90, 180, 270, or 360 degrees
	 */
	public void turnLeft(double input) {
		input /= 200;
		double rotate = 17.5 * input;
		encoderRight.reset();
		while (encoderRight.getDistance() < rotate) {
			Robot.tankDrive(-.3, .3, false);
			//17.5
		}
	}
	
	/**
	 * method to turn robot in counterclockwise direction
	 * 
	 * @param input
	 *            value either 90, 180, 270, or 360 degrees
	 */
	public void turnRight(double input) {
		input = input / 200;
		double rotat = 20 * input;
		//20
		encoderRight.reset();
		while (-encoderRight.getDistance() < rotat) {
			Robot.tankDrive(.3, -.3, false);
		}
	}

	// Use SmartDash to run, sets values and prints.

	/**
	 * Unused method to find the ratios between the the two motors, an attempt on
	 * motor correction (DEPRICATED)
	 */
	public void calibrate() {
		double L = encoderLeft.getRate();
		double R = encoderRight.getRate();
		if (L < R) {
			Rmod = L / R;
		} else {
			Lmod = R / L;
		}
		System.out.println("Right Mod = " + Rmod);
		System.out.println("Left Mod = " + Lmod);

	}

	public void startPos1() {
		goForward(100);
		Timer.delay(1);
		turnLeft(90);
		Timer.delay(1);
		goForward(28);
		Timer.delay(1);
		turnRight(90);
		Timer.delay(1);
		goForward(110); 
	}
	
	public void startPos2() {
		goForward(100);
		Timer.delay(1);
		turnRight(270);
		Timer.delay(1);
		goForward(150);
		Timer.delay(1);
		turnRight(90);
		Timer.delay(1);
		goForward(98); 
	}
	
	public void startPos3() {
		goForward(100);
		Timer.delay(1);
		turnRight(90);
		Timer.delay(1);
		goForward(12);
		Timer.delay(1);
		turnRight(270);
		Timer.delay(1);
		goForward(98);
	}
	
	/** 
	 * This function is called every 20ms during the beginning 15seconds of the game. It takes indirect parameters stored in instance variables from the robotInit
	 * class, in order to determine the case needing to be run in order to successfully complete the autonomous round.
	 */
	public void autonomousPeriodic() {
		//digit.display(RobotController.getBatteryVoltage());
		
		/**
			SWITCH/CASE METHOD FOR AUTONOMOUS
				5 cases that are called from the autonomousInit class (determined by starting position [mode] and switch/scale configuration [gameData]
					case1 - startPos 1 with no switch mechanism
					case2 - startPos 2 with no switch mechanism
					case3 - startPos 3 with no switch mechanism
					case4 - startPos 1 with switch mechanism on left side
					case5 - startPos 3 with switch mechanism on right side
					
				Runs the corresponding case from the autonomousInit class (can add more cases if wanting to attempt switch on opposite ends or from startPos 2
		*/
		switch(autoCase) {
		case 1: {
			System.out.println("Case 1N worked");
			if(!done) {
				goForward(100);
				Timer.delay(0.5);
				turnLeft(90);
				Timer.delay(0.5);
				goForward(28);
				Timer.delay(0.5);
				turnRight(90);
				Timer.delay(0.5);
				goForward(65); 
			}
			done = true;
			break;
		}
		case 2: {
			System.out.println("Case 2N worked");
			if(!done) {
				goForward(90);
				Timer.delay(0.5);
				turnRight(90);
				Timer.delay(0.5);
				goForward(90);
				Timer.delay(0.5);
				turnLeft(100);
				Timer.delay(0.5);
				goForward(50);
			}
			done = true;
			break;
		}
		case 3: {
			System.out.println("Case 3N worked");
			System.out.println(done);
			if(!done) {
				goForward(100);
				Timer.delay(0.5);
				turnRight(90);
				Timer.delay(0.5);
				goForward(28);
				Timer.delay(0.5);
				turnLeft(90);
				Timer.delay(0.5);
				goForward(65);
			}
			done = true;
			break;
		}
		case 4: {
			System.out.println("Case 1L worked");
			if(!done) {
				goForward(100);
				Timer.delay(1);
				turnLeft(90);
				Timer.delay(1);
				goForward(28);
				Timer.delay(1);
				turnRight(90);
				Timer.delay(1);
				goForward(40); 
				Timer.delay(1);
				turnRight(90);
				Timer.delay(1);
				goForward(18);
				Timer.delay(1);
				//LIFT CODE
				Timer.delay(0);
				//SHOOT CODE
			}
			done = true;
			break;
		}
		case 5: {
			System.out.println("Case 3R worked");
			if(!done) {
				goForward(100);
				Timer.delay(1);
				turnRight(90);
				Timer.delay(1);
				goForward(28);
				Timer.delay(1);
				turnLeft(90);
				Timer.delay(1);
				goForward(65);
			}
			done = true;
			break;
		}
		}
	}

	/**
	 * This function is called periodically during operator control. It is generally used for the driveTeam setup, where two controllers are called, one for the driver
	 * and the other for the operator. Joystick1 controls the robots respective movement while Joystick2 corresponds with the operator controls of lifting, gathering, 
	 * and climbing
	 * 
	 * Called every 20ms during the two minute period after autonomousPeriodic code runs for 15seconds
	 */
	@Override
	public void teleopPeriodic() {
		
		//digit.display("6823");
		//digit.display(RobotController.getBatteryVoltage());
		//System.out.println(RobotController.getBatteryVoltage());
	// Go forwards
		if (!m_driveStick.getRawButton(1)) {
			Robot.arcadeDrive(m_driveStick.getRawAxis(1) * 0.8, m_driveStick.getRawAxis(2) * 0.35, false);
			double Ls = encoderLeft.getRate() / 12.0;
			double Rs = encoderRight.getRate() / 12.0;
			leftMoreThanRight += Ls - Rs;
		} else {
			k = SmartDashboard.getNumber("K", 0); // Good k value is 0.23
			double L = 0;
			double R = 0;
			double Ls = encoderLeft.getRate() / 12.0;
			double Rs = encoderRight.getRate() / 12.0;
			leftMoreThanRight += Ls - Rs;

			if (Ls > Rs) {
				L = m_driveStick.getRawAxis(1) - k * (Ls - Rs);
				R = m_driveStick.getRawAxis(1);
			} else {
				R = m_driveStick.getRawAxis(1) - k * (Rs - Ls);
				L = m_driveStick.getRawAxis(1);
			}

			Robot.tankDrive(L, R, false);
		}
		
		
		if (m_driveStick.getRawButton(12)) {
			turnLeft(90);
		}
		
		if (m_driveStick.getRawButton(11)) {
			turnRight(90);
		}
		
		if (m_driveStick.getRawButton(8))
			leftMoreThanRight = 0;
		SmartDashboard.putNumber("leftMoreThanRight", leftMoreThanRight);
		SmartDashboard.putNumber("Left Velocity", encoderLeft.getRate() / 12.0); // send left drive encoder rate to
																					// smart dashboard
		SmartDashboard.putNumber("Right Velocity", encoderRight.getRate() / 12.0); // send right drive encoder rate to
																					// smart dashboard

		listLeft.add(encoderLeft.getRate() / 12.0);
		listRight.add(encoderRight.getRate() / 12.0);
		count++;
		SmartDashboard.putNumber("Count", count);
		if (count == 400) {
			double sumL = 0;
			double sumR = 0;

			for (int i = 0; i < listLeft.size(); i++)
				sumL = sumL + listLeft.get(i);

			for (int i = 0; i < listRight.size(); i++)
				sumR = sumR + listRight.get(i);
			SmartDashboard.putNumber("Right Average", sumR / 400.0);
			SmartDashboard.putNumber("Left Average", sumL / 400.0);
		}

		  		  if (m_operatorControl.getRawAxis(1) > 0 || m_operatorControl.getRawAxis(1) < 0) {
		  		  	forkLift.tankDrive(m_operatorControl.getRawAxis(1), m_operatorControl.getRawAxis(1), false);
		  		  }
		  
		  SmartDashboard.getNumber("Intake Speed ", intakeSpeed);
		  		  SmartDashboard.putNumber("Left Trigger value (Intake OUT)", m_operatorControl.getRawAxis(2));
		  		  SmartDashboard.putNumber("Right Trigger value (Intake IN)", m_operatorControl.getRawAxis(3));
		 
		  		   if (m_operatorControl.getRawAxis(2) > 0) {
		  	//Intake.tankDrive(-intakeSpeed * m_operatorControl.getRawAxis(2), -intakeSpeed * m_operatorControl.getRawAxis(2), false); ///NEED THIS (NOT A TEST)
		  		  								Intake.tankDrive(m_operatorControl.getRawAxis(2),m_operatorControl.getRawAxis(2), false);											
		  		  } else if (m_operatorControl.getRawAxis(3) > 0) {
		  	//Intake.tankDrive(intakeSpeed * m_operatorControl.getRawAxis(3), intakeSpeed * m_operatorControl.getRawAxis(3), false);
		  		  								Intake.tankDrive(-m_operatorControl.getRawAxis(3),-m_operatorControl.getRawAxis(3), false);
		  		  }
		   
		   //ERROR HERE
		   //SmartDashboard.putBoolean("ButtonA?", buttonA.get());
		  // SmartDashboard.putBoolean("ButtonB?", buttonB.get());
		   if(m_operatorControl.getRawButton(4))
		    Climb.tankDrive(1, 1, false);
		   
		 // SmartDashboard.putNumber("Gyro: ",gyro.getAngle() );
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	public void disabledPeriodic() {
		//digit.display(RobotController.getBatteryVoltage());
	}


}
