package teamcode;

import ftclib.command.CommandOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import teamcode.CommandGroups.TestAutoMotions;
import teamcode.Subsystems.DriveSubsystem;
import teamcode.Subsystems.TrackAprilTagSubsystem;

@Autonomous(name = "Auto")
public class AutoOpMode extends CommandOpMode {

    DriveSubsystem driveSubsystem;
    TrackAprilTagSubsystem trackAprilTagSubsystem;

    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap, telemetry);
        trackAprilTagSubsystem = new TrackAprilTagSubsystem(hardwareMap, telemetry);

        driveSubsystem.initialize();
        trackAprilTagSubsystem.initialize();

        register(driveSubsystem);
        register(trackAprilTagSubsystem);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        // Schedule our automations
        schedule(new TestAutoMotions(driveSubsystem, trackAprilTagSubsystem));

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
            telemetry.update();
        }
        reset();
    }
}
