# Welcome!
This repository contains code from the conversion of a CENTERSTAGE Coaches' Bot code to the FTCLib 
Framework.  The Coaches are mentors to The MITEE Network, a collective of three FIRST Tech Challenge 
(FTC) teams, Monument, Colorado.   For more information visit: https://themitee.net

The repository includes the official FTC SDK for the INTO THE DEEP 2024-25 SEASON from 
https://github.com/FIRST-Tech-Challenge/FtcRobotController. 

In addition, it includes the FTCLib from https://github.com/FTCLib/FTCLib. The FTCLib is added as a 
git submodule so it can be independently managed. 

Huge thanks to Titan Robotics Club (TRC492) from Bellevue, Washington, for publishing their FTC 
Template (https://github.com/trc492/FtcTemplate).  It was a great jumpstart in providing examples
for the latest season on how to use FTCLib as we looked to convert features in the Coaches bot.  
Since all three of our teams are new to Java programming we chose not to use their TRCLib to reduce
complexity and focus on some basics.  Thank you again, TRC492, for sharing!

# FTC SDK Release Information

## NOTICE

This repository contains the public FTC SDK for the INTO THE DEEP (2024-2025) competition season.

# Release Information for the version of the FTC SDK in this repository

## Version 10.1 (20240919-122750)

### Enhancements
* Adds new OpenCV-based `VisionProcessor`s (which may be attached to a VisionPortal in either Java or Blocks) to help teams implement color processing via computer vision in the INTO THE DEEP game
  * `ColorBlobLocatorProcessor` implements OpenCV color "blob" detection. A new sample program `ConceptVisionColorLocator` demonstrates its use.
    * A choice is offered between pre-defined color ranges, or creating a custom one in RGB, HSV, or YCrCb color space
    * The ability is provided to restrict detection to a specified Region of Interest on the screen
    * Functions for applying erosion / dilation morphing to the threshold mask are provided
    * Functions for sorting and filtering the returned data are provided
  * `PredominantColorProcessor` allows using a region of the camera as a "long range color sensor" to determine the predominant color of that region. A new sample program `ConceptVisionColorSensor` demonstrates its use.
    * The determined predominant color is selected from a discrete set of color "swatches", similar to the MINDSTORMS NXT color sensor
  * Documentation on this Color Processing feature can be found here: https://ftc-docs.firstinspires.org/color-processing
* Added Blocks sample programs for color sensors: RobotAutoDriveToLine and SensorColor.
* Updated Self-Inspect to identify mismatched RC/DS software versions as a "caution" rather than a "failure."

### Bug Fixes
* Fixes [AngularVelocity conversion regression](https://github.com/FIRST-Tech-Challenge/FtcRobotController/issues/1070)

# Additional Releases of FTCRobot Controller
For additional release information on the FTCRobotController we recommend going to their 
repository: https://github.com/FIRST-Tech-Challenge/FtcRobotController