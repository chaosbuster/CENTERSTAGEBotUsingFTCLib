package teamcode;

import ftclib.command.CommandOpMode;
import ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.Subsystems.DriveSubsystem;
import teamcode.Commands.DefaultDrive;

@TeleOp(name = "Driver Period")
@Disabled
public class DriverOpMode extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private GamepadEx driverOp;
    private DefaultDrive driveDefaultCommand;

    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap);

        driverOp = new GamepadEx(gamepad1);
        driveDefaultCommand = new DefaultDrive(driveSubsystem, () -> driverOp.getLeftX(), () -> driverOp.getLeftY(), () -> driverOp.getRightX());

        register(driveSubsystem);
        driveSubsystem.setDefaultCommand(driveDefaultCommand);
    }

}