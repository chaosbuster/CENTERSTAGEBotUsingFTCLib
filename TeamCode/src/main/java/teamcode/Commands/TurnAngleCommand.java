package teamcode.Commands;

import ftclib.command.CommandBase;
import ftclib.controller.PController;
import teamcode.Subsystems.DriveSubsystem;

public class TurnAngleCommand extends CommandBase {

    DriveSubsystem driveSubsystem;
    double angle;

    // Proportional Controller for correcting for gyro error
    PController headingController;

    public TurnAngleCommand(double angle, DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.angle = angle;
        // At 180 degrees, we should spin almost as fast as we can to correct
        // 1 is full power. 180 * 0.05 = 0.9
        headingController = new PController(0.05, angle, driveSubsystem.getHeading());
        headingController.setSetPoint(angle);

    }


    @Override
    public void initialize() {
        // Reset gyro and encoders
        driveSubsystem.resetEncoders();

        // If within 5 degrees of setpoint, the target is considered reached
        headingController.setTolerance(5);
    }

    @Override
    public void execute() {
        // Calculate output
        double rotate = headingController.calculate(driveSubsystem.getHeading());

        // apply output
        driveSubsystem.getRobotDrive().driveRobotCentric(0, 0, rotate);
    }

    @Override
    public void end() {
        driveSubsystem.getRobotDrive().driveRobotCentric(0, 0, 0);

    }

    @Override
    public boolean isFinished() {
        boolean angleReached = headingController.atSetPoint();
        return angleReached;
    }
}
