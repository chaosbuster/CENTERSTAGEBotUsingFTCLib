package teamcode.CommandGroups;

import ftclib.command.SequentialCommandGroup;
import teamcode.Commands.DriveDistance;
import teamcode.Commands.DriveToTag;
import teamcode.Commands.TurnAngleCommand;
import teamcode.Subsystems.DriveSubsystem;
import teamcode.Subsystems.TrackAprilTagSubsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *  A group of commands to test automating multiple tasks.
 */
public class TestAutoMotions extends SequentialCommandGroup {

    public TestAutoMotions(DriveSubsystem driveSubsystem, TrackAprilTagSubsystem aprilTagSubsystem) {

        // Drive Forward for 10 inches
        //new DriveDistance(12, 0.25, driveSubsystem)
        // Turn 90 degrees
        //new TurnAngleCommand(90.0, driveSubsystem)

        //new DriveToTag(12, 12.0, 0.25, driveSubsystem, aprilTagSubsystem);
        addCommands(
                new DriveToTag(12, 12.0, 0.25, driveSubsystem, aprilTagSubsystem)
        );
        addRequirements(driveSubsystem, aprilTagSubsystem);

    }
}
