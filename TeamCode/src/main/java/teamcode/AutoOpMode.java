package teamcode;

import com.arcrobotics.ftclib.command.old.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import teamcode.Subsystems.DriveSubsystem;
import teamcode.Commands.DriveDistance;
import teamcode.Commands.TurnAngleCommand;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Command-based Autonomous Sample")
// @Autonomous(...) is the other common choice
public class AutoOpMode extends CommandOpMode {

    DriveSubsystem driveSubsystem;
    GamepadEx driverGamepad;

    @Override
    public void initialize() {
        driverGamepad = new GamepadEx(gamepad1);
        driveSubsystem = new DriveSubsystem(hardwareMap);

        driveSubsystem.initialize();
    }

    @Override
    public void run() {
        // Drive Forward for 10 inches with a timeout of 4 seconds.
        addSequential(new DriveDistance(10, 0.5, driveSubsystem), 2);
        // Turn 90 degrees with a timeout of 2 seconds
        addSequential(new TurnAngleCommand(90, driveSubsystem), 2);

        // DO SOME ACTION

        sleep(500);

        // Drive Forward for -10 inches with a timeout of 4 seconds.
        addSequential(new DriveDistance(-10, 0.5, driveSubsystem), 4);

        addSequential(new DriveDistance(10, 0.75, driveSubsystem), 3);

    }
}
