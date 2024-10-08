package teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import ftclib.command.SubsystemBase;
import ftclib.drivebase.MecanumDrive;
import ftclib.hardware.motors.Motor.Encoder;
import ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {

    private final String mName_frontleft = "left_front_drive", mName_frontright = "right_front_drive";
    private final String mName_backleft = "left_back_drive", mName_backright = "right_back_drive";

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example for a REV HEX motor with a 3:1 Gear and a 4:1 gear on it would mean a
    // DRIVE_GEAR_REDUCTION =
    static final double     COUNTS_PER_MOTOR_REV    = 28.0 ;   //  For a REV HD Hex Motor No Gearbox)
    static final double     DRIVE_GEAR_REDUCTION    = 12.0 ;     // 3:1 and a 4:1 Gear Box
    static final double     WHEEL_DIAMETER_INCHES   = 2.952756 ;     // For figuring circumference;  75 mm REV mecanum wheels
    static final double     INCHES_PER_PULSE        = (WHEEL_DIAMETER_INCHES * 3.1415) /
                                                      (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION);
    private final Boolean mInverted_frontleft = true, mInverted_frontright = false;
    private final Boolean mInverted_backleft = true, mInverted_backright = false;

    private Motor motor_frontleft = null, motor_frontright = null;
    private Motor motor_backleft = null, motor_backright = null;
    private MecanumDrive m_drive;

    private Encoder en_frontleft, en_frontright;
    private Encoder en_backleft, en_backright;

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem(HardwareMap hardwareMap) {

        // Using the hardware map that was passed to us, let's get handles for all of our motors
        motor_frontleft = new Motor(hardwareMap, mName_frontleft);
        motor_frontright = new Motor(hardwareMap, mName_frontright);
        motor_backleft = new Motor(hardwareMap, mName_backleft);
        motor_backright = new Motor(hardwareMap, mName_backright);

        // Set the distance per pulse for each motor based on wheel diameter, motor and gearing
        motor_frontleft.setDistancePerPulse(INCHES_PER_PULSE);
        motor_frontright.setDistancePerPulse(INCHES_PER_PULSE);
        motor_backleft.setDistancePerPulse(INCHES_PER_PULSE);
        motor_backright.setDistancePerPulse(INCHES_PER_PULSE);

        // Using the motor handle let's store our encoder handles for later retrieval
        en_frontleft = motor_frontleft.encoder;
        en_frontright = motor_frontright.encoder;
        en_backleft = motor_backleft.encoder;
        en_backright = motor_backright.encoder;

        // Now let's finally instantiate our MecanumDrive
        m_drive = new MecanumDrive(motor_frontleft, motor_frontright, motor_backleft, motor_backright);
    }

    /**
     * Drives the robot using controls.
     *
     * @param left the commanded strafe movement
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation
     */
    public void drive(double left, double fwd, double rot) {
        m_drive.driveRobotCentric(left, fwd, rot);
    }

    public double getFrontLeftEncoderDistance() {
        return en_frontleft.getDistance();
    }

    public double getFrontRightEncoderDistance() {
        return en_frontright.getDistance();
    }

    public double getBackLeftEncoderDistance() {
        return en_backleft.getDistance();
    }

    public double getBackRightEncoderDistance() {
        return en_backright.getDistance();
    }

    public void resetEncoders() {
        en_frontleft.reset();
        en_frontright.reset();
        en_backleft.reset();
        en_backright.reset();
    }

    public double getAverageEncoderDistance() {
        return (getFrontLeftEncoderDistance() + getFrontRightEncoderDistance() +
                getBackLeftEncoderDistance() + getBackRightEncoderDistance()) / 4.0;
    }

}
