package teamcode.CommandGroups;

import ftclib.command.SequentialCommandGroup;
import teamcode.Commands.DriveDistance;
import teamcode.Commands.TurnAngleCommand;
import teamcode.Subsystems.DriveSubsystem;

/**
 *  A group of commands to test automating multiple tasks.
 */
public class TestAutoMotions extends SequentialCommandGroup {

    public TestAutoMotions(DriveSubsystem drive) {

        // Drive Forward for 10 inches
        // Turn 90 degrees
        addCommands(
            new DriveDistance(10, 0.5, drive),
            new TurnAngleCommand(90, drive)
        );
        addRequirements(drive);

    }
}
