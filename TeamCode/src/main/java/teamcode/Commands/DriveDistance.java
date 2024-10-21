package teamcode.Commands;

import ftclib.command.CommandBase;

import teamcode.Subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final double m_distance;
    private final double m_speed;

    /**
     * Creates a new DriveDistance.
     *
     * @param inches The number of inches the robot will drive
     * @param speed  The speed at which the robot will drive
     * @param drive  The drive subsystem on which this command will run
     */
    public DriveDistance(double inches, double speed, DriveSubsystem drive) {
        m_distance = inches;
        m_speed = speed;
        driveSubsystem = drive;
    }

    @Override
    public void initialize() {
        driveSubsystem.resetEncoders();
    }

    @Override
    public void execute() {
        driveSubsystem.drive(0, m_speed, 0);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(driveSubsystem.getAverageEncoderDistance()) >= m_distance;
    }

}
