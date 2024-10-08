package teamcode;

import ftclib.command.CommandOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.CommandGroups.TestAutoMotions;
import teamcode.Subsystems.DriveSubsystem;

@Autonomous(name = "Auto")
public class AutoOpMode extends CommandOpMode {

    DriveSubsystem driveSubsystem;

    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap);

        driveSubsystem.initialize();

        schedule(new TestAutoMotions(driveSubsystem));
    }

    @Override
    public void run() {

    }
}
