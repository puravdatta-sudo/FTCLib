package org.firstinspires.ftc.robotcontroller.external.samples.OldCommandSample;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.SimpleMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp(name = "Command-Based Teleop Sample", group = "Command")
public class Teleop extends OpMode {

    public static final double kP = 0.003;
    public static final double kI = 0.12;
    public static final double kD = 0.053;
    public static final double kThreshold = 8;

    private DriveSubsystem driveSubsystem;
    private GamepadEx driverGamepad;
    private PIDLiftController liftController;
    private SimpleLinearLift lift;
    private SimpleMotorEx liftMotor;

    public static PIDController pid = new PIDController(
            new double[]{kP, kI, kD}
    );

    @Override
    public void init() {
        driverGamepad = new GamepadEx(gamepad1);
        driveSubsystem = new DriveSubsystem(driverGamepad, hardwareMap, telemetry);

        driveSubsystem.initialize();

        pid.setTolerance(kThreshold);
        pid.reset();

        liftMotor = new SimpleMotorEx("lift", hardwareMap, 537.6, pid);
        lift = new SimpleLinearLift(liftMotor);
        liftController = new PIDLiftController(lift);
    }

    @Override
    public void loop() {
        driveSubsystem.loop();
        liftController.power(gamepad2.left_stick_y);

        // set the lift states through the bumpers
        if (gamepad2.a) {
            liftController.setStageOne();
        } else if (gamepad2.b) {
            liftController.setStageTwo();
        } else if (gamepad2.x) {
            liftController.setStageThree();
        } else if (gamepad2.y) {
            liftController.resetStage();
        }
    }
}
