/*
 * Thank you to 2023 Titan Robotics Club (http://www.titanrobotics.com)
 * for starting us on this RobotParams class to help collect many of our robot
 * parameters.
 */

package teamcode;

import android.os.Environment;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.*;

import org.openftc.easyopencv.OpenCvCameraRotation;

import ftclib.drivebase.FtcRobotDrive;
import ftclib.drivebase.FtcMecanumDrive;
import ftclib.motor.FtcMotorActuator.MotorType;


/**
 * This class contains robot and subsystem constants and parameters.
 */
public class RobotParams
{
    /**
     * This class contains miscellaneous robot info.
     */
    public static class Robot
    {
        public static final double DASHBOARD_UPDATE_INTERVAL    = 0.1;      // in msec
        public static final String ROBOT_CODEBASE               = "CoachCENTERSTAGE";
        public static final double ROBOT_LENGTH                 = 17.0;
        public static final double ROBOT_WIDTH                  = 17.0;
        // Robot Drive Parameters.
        public static final double DRIVE_SLOW_SCALE             = 0.3;
        public static final double DRIVE_NORMAL_SCALE           = 1.0;
        public static final double TURN_SLOW_SCALE              = 0.3;
        public static final double TURN_NORMAL_SCALE            = 0.6;
        public static final DriveMode ROBOT_DRIVEMODE = DriveMode.ArcadeMode;
        public static final DriveOrientation DRIVE_ORIENTATION = DriveOrientation.ROBOT;

    }   //class Robot

    /**
     * When the season starts, the competition robot may not be ready for programmers. It's crucial to save time by
     * developing code on robots of previous seasons. By adding previous robots to the list of RobotType, one can
     * easily switch the code to handle different robots.
     */
    public enum RobotType
    {
        // This is useful for developing Vision code where all you need is a Control Hub and camera.
        VisionOnly,
        // Generic Mecanum Drive Base Robot
        MecanumRobot
    }   //enum RobotType

    /**
     * This class contains robot preferences. It enables/disables various robot features. This is especially useful
     * during robot development where some subsystems may not be available or ready yet. By disabling unavailable
     * subsystems, one can test the rest of the robot without the fear of code crashing when some subsystems are not
     * found.
     */
    public static class Preferences
    {
        // Global config
        public static final RobotType robotType                 = RobotType.MecanumRobot;
        public static final boolean inCompetition               = false;
        public static final boolean useTraceLog                 = true;
        public static final boolean useLoopPerformanceMonitor   = true;
        public static final boolean useBatteryMonitor           = false;
        // Status Update: Status Update may affect robot loop time, don't do it when in competition.
        public static final boolean doStatusUpdate              = !inCompetition;
        public static final boolean showSubsystems              = true;
        // Vision
        public static final boolean useVision                   = false;
        public static final boolean useWebCam                   = false;    // false to use Android phone camera.
        public static final boolean useBuiltinCamBack           = false;    // For Android Phone as Robot Controller.
        public static final boolean tuneColorBlobVision         = false;
        public static final boolean useAprilTagVision           = false;
        public static final boolean useColorBlobVision          = false;
        public static final boolean useLimelightVision          = false;
        public static final boolean showVisionView              = !inCompetition;
        public static final boolean showVisionStat              = false;
        // Drive Base
        public static final boolean useDriveBase                = true;
        // Subsystems
        public static final boolean useSubsystems               = true;
    }   //class Preferences

    //
    // Robot Parameters.
    //

    /**
     * This class contains the parameters of the front camera.
     */
    public static class FrontCamParams extends FtcRobotDrive.VisionInfo
    {
        public FrontCamParams()
        {
            camName = "Webcam_front";
            camImageWidth = 640;
            camImageHeight = 480;
            camXOffset = 0.0;                   // Inches to the right from robot center
            camYOffset = 2.0;                   // Inches forward from robot center
            camZOffset = 9.75;                  // Inches up from the floor
            camPitch = 15.0;                    // degrees down from horizontal
            camYaw = 0.0;                       // degrees clockwise from robot front
            camRoll = 0.0;
            camPose = new TrcPose3D(camXOffset, camYOffset, camZOffset, camYaw, camPitch, camRoll);
            camOrientation = OpenCvCameraRotation.UPRIGHT;
            // Homography: cameraRect in pixels, worldRect in inches
            cameraRect = new TrcHomographyMapper.Rectangle(
                0.0, 120.0,                                             // Camera Top Left
                camImageWidth -1, 120.0,                                // Camera Top Right
                0.0, camImageHeight - 1,                                // Camera Bottom Left
                camImageWidth - 1, camImageHeight - 1);                 // Camera Bottom Right
            worldRect = new TrcHomographyMapper.Rectangle(
                -12.5626, 48.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset,   // World Top Left
                11.4375, 44.75 - Robot.ROBOT_LENGTH/2.0 - camYOffset,   // World Top Right
                -2.5625, 21.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset,    // World Bottom Left
                2.5626, 21.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset);    // World Bottom Right
        }   //FrontCamParams
    }   //class FrontCamParams

    /**
     * This class contains the parameters of the back camera.
     */
    public static class BackCamParams extends FtcRobotDrive.VisionInfo
    {
        public BackCamParams()
        {
            camName = "Webcam 2";
            camImageWidth = 640;
            camImageHeight = 480;
            camXOffset = 0.0;                   // Inches to the right from robot center
            camYOffset = 2.0;                   // Inches forward from robot center
            camZOffset = 9.75;                  // Inches up from the floor
            camPitch = 15.0;                    // degrees down from horizontal
            camYaw = 0.0;                       // degrees clockwise from robot front
            camRoll = 0.0;
            camPose = new TrcPose3D(camXOffset, camYOffset, camZOffset, camYaw, camPitch, camRoll);
            camOrientation = OpenCvCameraRotation.UPRIGHT;
            // Homography: cameraRect in pixels, worldRect in inches
            cameraRect = new TrcHomographyMapper.Rectangle(
                0.0, 120.0,                                             // Camera Top Left
                camImageWidth -1, 120.0,                                // Camera Top Right
                0.0, camImageHeight - 1,                                // Camera Bottom Left
                camImageWidth - 1, camImageHeight - 1);                 // Camera Bottom Right
            worldRect = new TrcHomographyMapper.Rectangle(
                -12.5626, 48.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset,   // World Top Left
                11.4375, 44.75 - Robot.ROBOT_LENGTH/2.0 - camYOffset,   // World Top Right
                -2.5625, 21.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset,    // World Bottom Left
                2.5626, 21.0 - Robot.ROBOT_LENGTH/2.0 - camYOffset);    // World Bottom Right
        }   //BackCamParams
    }   //class BackCamParams

    public static class VisionOnlyParams extends FtcRobotDrive.RobotInfo
    {
        public VisionOnlyParams()
        {
            robotName = "VisionOnly";
            // Front Camera
            webCam1 = new FrontCamParams();
            // Back Camera
            webCam2 = null;
            // Limelight  TODO: CHANGE TO HUSKYLENS
            limelight = null;
        }   //VisionOnlyParams
    }   //class VisionOnlyParams

    /**
     * This class contains the Mecanum Robot Parameters.
     */
    public static class MecanumParams extends FtcRobotDrive.RobotInfo
    {
        public MecanumParams()
        {
            robotName = "MecanumRobot";
            // Robot Dimensions
            robotLength = Robot.ROBOT_LENGTH;
            robotWidth = Robot.ROBOT_WIDTH;
            wheelBaseLength = (24.0 * 14)*TrcUtil.INCHES_PER_MM;
            wheelBaseWidth = 16.0;
            // IMU
            imuName = "imu";
            hubLogoDirection = LogoFacingDirection.UP;
            hubUsbDirection = UsbFacingDirection.FORWARD;
            // Drive Motors
            driveMotorType = MotorType.DcMotor;
            // Order of motor names are [Left Front, Right Front, Left Back, Right Back]
            driveMotorNames = new String[] {"left_front_drive", "right_front_drive", "left_back_drive", "right_back_drive"};
            driveMotorInverted = new boolean[] {true, false, true, false};
            odometryType = TrcDriveBase.OdometryType.MotorOdometry;
            absoluteOdometry = null;  // Using Motor Odometry

            // Drive Motor Odometry
            xDrivePosScale = 0.01924724265461924299065420560748;        // in/count
            yDrivePosScale = 0.02166184604662450653409090909091;        // in/count
            // Robot Drive Characteristics
            robotMaxVelocity = 23.0;        // inches/sec
            robotMaxAcceleration  = 500.0;  // inches/sec2
            robotMaxTurnRate = 100.0;       // degrees/sec
            profiledMaxVelocity = robotMaxVelocity;
            profiledMaxAcceleration = robotMaxAcceleration;
            profiledMaxTurnRate = robotMaxTurnRate;
            // DriveBase PID Parameters
            drivePidTolerance = 1.0;
            turnPidTolerance = 1.0;
            xDrivePidCoeffs = new PidCoefficients(0.95, 0.0, 0.001, 0.0, 0.0);
            xDrivePidPowerLimit = 1.0;
            xDriveMaxPidRampRate = null;
            yDrivePidCoeffs = new PidCoefficients(0.06, 0.0, 0.002, 0.0, 0.0);
            yDrivePidPowerLimit = 1.0;
            yDriveMaxPidRampRate = null;
            turnPidCoeffs = new PidCoefficients(0.02, 0.0, 0.002, 0.0, 0.0);
            turnPidPowerLimit = 0.5;
            turnMaxPidRampRate = null;
            // PID Stall Detection
            pidStallDetectionEnabled = true;
            // PurePursuit Parameters
            ppdFollowingDistance = 6.0;
            velPidCoeffs = new PidCoefficients(0.0, 0.0, 0.0, 1.0 / profiledMaxVelocity, 0.0);
            // Vision
            webCam1 = new FrontCamParams();
            webCam2 = null;
            limelight = null;
            // Miscellaneous
            blinkinName = null;  // Not implemented
        }   //MecanumParams
    }   //class MecanumParams

    //
    // Subsystems.
    //

}   //class RobotParams
