package teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import ftclib.command.CommandBase;
import teamcode.Subsystems.DriveSubsystem;
import teamcode.Subsystems.TrackAprilTagSubsystem;

public class DriveToTag extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final TrackAprilTagSubsystem aprilTagSubsystem;
    private final int desiredTagID;
    private final double distance;
    private final double m_speed;
    private AprilTagDetection currentDetection = null;
    private boolean stopRequested = false;

    /**
     * Creates a new DriveToTag.
     *
     * @param tagID  The ID of the April Tag that we are targeting 
     * @param inches The number of inches the robot will drive
     * @param speed  The speed at which the robot will drive
     * @param drive  The drive subsystem on which this command will run
     * @param vision The April Tag visioning subsystem
     */
    public DriveToTag(int tagID, double inches, double speed, DriveSubsystem drive, TrackAprilTagSubsystem vision) {
        desiredTagID = tagID;
        distance = inches;
        m_speed = speed;
        driveSubsystem = drive;
        aprilTagSubsystem = vision;
        aprilTagSubsystem.setDesiredTagID(desiredTagID);
    }

    @Override
    public void initialize() {
        driveSubsystem.resetEncoders();
        driveSubsystem.drive(0, 0, 0);
        currentDetection = aprilTagSubsystem.getDesiredTag();
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0, 0, 0);
    }
    @Override
    public void execute() {
        // Get latest reading from our AprilTagSubsystem
        if (aprilTagSubsystem.wasDesiredTagFound()) {
            currentDetection = aprilTagSubsystem.getDesiredTag();
        }

        // Make sure we have an AprilTag pointer
        if (currentDetection == null) {
            return;
        }

        // apply output
        driveSubsystem.driveToHeading(distance, currentDetection.ftcPose.range, currentDetection.ftcPose.bearing, currentDetection.ftcPose.yaw);
    }
    @Override
    public boolean isFinished() {
        boolean stopMovement = false;

        if (currentDetection == null || !aprilTagSubsystem.wasDesiredTagFound()) {
            stopMovement = true;
        } else if (Math.abs(currentDetection.ftcPose.range) >= distance) {
            stopMovement = false;
        } else if (stopRequested) {
            stopMovement = true;
        } else {
            stopMovement = true;
        }

        if (stopMovement)
            driveSubsystem.drive(0,0,0);

        return stopMovement;
    }

    public void stopCommand() {
        stopRequested = true;
    }
}
