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
        register(driveSubsystem);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        // Schedule our automations
        schedule(new TestAutoMotions(driveSubsystem));

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
        }
        reset();
    }
}
