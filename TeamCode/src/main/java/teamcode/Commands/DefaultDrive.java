package teamcode.Commands;

import ftclib.command.CommandBase;

import java.util.function.DoubleSupplier;
import teamcode.Subsystems.DriveSubsystem;

/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class DefaultDrive extends CommandBase {

    private final DriveSubsystem m_drive;
    private DoubleSupplier move_left;
    private DoubleSupplier move_forward;
    private DoubleSupplier move_turn;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command will run on.
     * @param left      The control input for strafing left (negative for right)
     * @param forward   The control input for driving forwards (negative for backwards)
     * @param rotation  The control input for turning
     */
    public DefaultDrive(DriveSubsystem subsystem, DoubleSupplier left, DoubleSupplier forward, DoubleSupplier rotation) {
        m_drive = subsystem;
        move_left = left;
        move_forward = forward;
        move_turn = rotation;
        addRequirements(m_drive);
    }

    @Override
    public void execute() {
        m_drive.drive(move_left.getAsDouble(), move_forward.getAsDouble(), move_turn.getAsDouble());
    }

}