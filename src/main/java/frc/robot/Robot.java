// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private CANdle mLed = new CANdle(0);
  private CANdleConfiguration config = new CANdleConfiguration();
  
  SendableChooser<Boolean> intake_chooser = new SendableChooser<>();
  
  AddressableLED led;
  AddressableLEDBuffer buffer;
  private RobotContainer m_robotContainer;

  boolean isCone;
  boolean whatPiece;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();


    config.brightnessScalar = 0.5;
    config.stripType = LEDStripType.GRB;
    config.disableWhenLOS = true;
    config.statusLedOffWhenActive = true;
    config.vBatOutputMode = VBatOutputMode.On;
    mLed.configAllSettings(config);
    led = new AddressableLED(4);
    

    buffer = new AddressableLEDBuffer(60);
    led.setLength(buffer.getLength());

    led.setData(buffer);
    led.start();

    intake_chooser.addOption("Cone Intake", isCone = true);
    intake_chooser.addOption("Cube Intake", isCone = false);

    intake_chooser.setDefaultOption("Cone Intake", isCone = true);

    SmartDashboard.putData(intake_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    whatPiece = intake_chooser.getSelected();
    //System.out.println(whatPiece);
    
    if(whatPiece){
      mLed.setLEDs(255, 255, 0);
    } else{
      mLed.setLEDs(255, 0, 255);
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    
   
    // for (int i = 0; i < buffer.getLength(); i++) {
    //   buffer.setRGB(i, 255, 0, 0);
    //   led.setData(buffer);
    // }

    

    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
    //mLed.setLEDs(0, 0, 255);

    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
