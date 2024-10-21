package teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;
import java.util.concurrent.TimeUnit;

// This Subsystem controls the two webcams on the left and right of the robot.  These two
    // webcams are to help provide AprilTag information, especially during autonomous, but
    // but can be useful also during Teleop for automating a movement to a location.
public class TrackAprilTagSubsystem extends SubsystemBase {
    private boolean ableToRunVision = false;
    // *******************************************************************
    // Camera variables
    private String name_leftcamera = "Webcam_back", name_rightcamera = "Webcam_front";  // Holds the names of the cameras as defined in the Hub's configuration file.
    private WebcamName hw_leftcamera, hw_rightcamera;      // Holds the hardware handles of the cameras.
    private String currentCamera;                         // Holds the value of the current camera streaming.
    // *******************************************************************
    // Vision processors
    private VisionPortal visionPortal = null;        // The variable to store our instance of the vision portal. Manages the video source.
    private AprilTagProcessor aprilTagProcessor = null;       // The variable to store our instance of the AprilTag processor. Manages the AprilTag
    private boolean manualExposureSet = false;       // Reduces motion blur
    private CameraName switchableCamera = null;           // Holds both cameras for the vision portal.
    // *******************************************************************
    // April Tag variables
    private int desiredTagID = 0;  // Used to hold the ID of the requested desired tag to detect.
    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
    public static Telemetry tl;

    public TrackAprilTagSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        hw_leftcamera = hardwareMap.get(WebcamName.class, name_leftcamera);
        hw_rightcamera = hardwareMap.get(WebcamName.class, name_rightcamera);
        // Let's default to the left side camera
        currentCamera = name_leftcamera;

        tl = telemetry;
    }

    // Set the April Tag ID that we want to track
    public void setDesiredTagID(int desiredTagID) {
        this.desiredTagID = desiredTagID;
    }

    //  Allow requestor to get current AprilTagDetection
    public AprilTagDetection getDesiredTag() {
        return desiredTag;
    }

    public boolean wasDesiredTagFound() {
        if (desiredTag == null)
            return false;
        else
            return true;
    }

    // This method will be called once per scheduler run
    @Override
    public void periodic() {
        tl.addData("VISION", "In periodic() method.");
        desiredTag = null;

        if (!ableToRunVision || aprilTagProcessor == null || desiredTagID <= 0 || visionPortal == null) {
            tl.addData("VISION", "Unable to run vision for AprilTags.");
            return;
        }

        // SHOULD ONLY RUN ONCE. Use low exposure time to reduce motion blur.
        if (!manualExposureSet) {
            manualExposureSet = setManualExposure(6, 250);
        }

        // Get the latest list of detected tags
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

        // Make sure we found any detections
        if (currentDetections == null) {
            tl.addData("VISION", "Unable to find any AprilTags.");
            return;
        }

        // Step through the list of detected tags and look for a matching tag
        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                //  Check to see if we want to track towards this tag.
                if ((desiredTagID > 0) || (detection.id == desiredTagID)) {
                    // Yes, we want to use this tag.
                    desiredTag = detection;
                    // Tag is found!!!
                    tl.addData("Found", "Tag ID %d found.", detection.id);
                    break; // don't look any further.

                } else {
                    // This tag is in the library, but we do not want to track it right now.
                    tl.addData("Skipping", "Tag ID %d is not desired", detection.id);
                }
            } else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                tl.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }
    }

    /*
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
    */
    private boolean setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (!ableToRunVision || visionPortal == null) {
            tl.addData("VISION", "Unable to set manual exposure on cameras.");
            return false;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            tl.addData("VISION", "Unable to set manual exposure on cameras. Not streaming.");
            tl.update();
            return false;
        }

        // Set camera controls unless we are stopping.
        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
            exposureControl.setMode(ExposureControl.Mode.Manual);
        }
        exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);

        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(gain);

        tl.addData("VISION", "Set manual exposure on cameras.");
        return true;
    }

    public void initialize() {
        // Create the AprilTag processor by using a builder.
        aprilTagProcessor = new AprilTagProcessor.Builder().build();
        if (aprilTagProcessor == null) {
            ableToRunVision = false;
            tl.addData("VISION", "Unable to build the aprilTagProcessor for vision.");
            return;
        }

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTagProcessor.setDecimation(2);

        // Next, create a VisionPortal.Builder and set attributes related to the camera.
        switchableCamera = ClassFactory.getInstance().getCameraManager().nameForSwitchableCamera(hw_leftcamera, hw_rightcamera);

        // Create the vision portal by using a builder.
        if (switchableCamera != null) {
            visionPortal = new VisionPortal.Builder().setCamera(switchableCamera).addProcessors(aprilTagProcessor).build();
            ableToRunVision = true;

            // Wait for driver to press start
            tl.addData("Camera preview on/off", "3 dots, Camera Stream");
            tl.addData(">", "Touch Play to start OpMode");
        } else {
            // Wait for driver to press start
            tl.addData("VISION", "Unable to initialize cameras for vision.");
            ableToRunVision = false;
        }
    }

}


