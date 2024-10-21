package teamcode;

import ftclib.command.CommandOpMode;
import ftclib.command.button.Button;
import ftclib.command.button.GamepadButton;
import ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import ftclib.gamepad.GamepadKeys;
import ftclib.gamepad.ToggleButtonReader;
import teamcode.CommandGroups.TestAutoMotions;
import teamcode.Commands.DriveDistance;
import teamcode.Commands.DriveToTag;
import teamcode.Subsystems.DriveSubsystem;
import teamcode.Commands.DefaultDrive;
import teamcode.Subsystems.TrackAprilTagSubsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Driver Period")
public class DriverOpMode extends CommandOpMode {

    private DriveSubsystem driveSubsystem = null;
    private GamepadEx driverOp;
    private DefaultDrive driveDefaultCommand = null;
    private TrackAprilTagSubsystem aprilTagSubsystem = null;
    private DriveToTag driveToTagCommand = null;
    private ToggleButtonReader button_driveToTag = null;
    private DriveDistance driveDistanceCommand = null;
    private ToggleButtonReader button_driveDistance = null;

    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap, telemetry);
        aprilTagSubsystem = new TrackAprilTagSubsystem(hardwareMap, telemetry);

        driverOp = new GamepadEx(gamepad1);
        driveDefaultCommand = new DefaultDrive(driveSubsystem, () -> driverOp.getLeftX(), () -> driverOp.getLeftY(), () -> driverOp.getRightX());

        button_driveToTag = new ToggleButtonReader(driverOp, GamepadKeys.Button.A);
        driveToTagCommand  = new DriveToTag(12, 12.0, 0.25, driveSubsystem, aprilTagSubsystem);

        button_driveDistance = new ToggleButtonReader(driverOp, GamepadKeys.Button.X);
        driveDistanceCommand  = new DriveDistance(12, 0.5, driveSubsystem);

        register(driveSubsystem);
        register(aprilTagSubsystem);
        driveSubsystem.setDefaultCommand(driveDefaultCommand);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {

            // See if driver requested to drive to currently set tag desired
            if (button_driveToTag.getState()) {
                schedule(driveToTagCommand);
            } else {
                driveToTagCommand.stopCommand();
            }

            // See if driver requested to drive a default distance
            if (button_driveDistance.getState()) {
                schedule(driveDistanceCommand);
            }

            run();
        }
        reset();
    }

}