package teamcode.UtilClasses;

public class AprilTag {
    private int id = 0;
    private double range = 0;
    private double bearing = 0;
    private double yaw = 0;

    public AprilTag () {}

    // Sets the ID for this AprilTag
    public void setId(int id) {
        this.id = id;
    }
    // Distance from
    public void setRange(double range) {
        this.range = range;
    }
    // Left or right from
    public void setBearing(double bearing) {
    }
    // Angle from the robot
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    // Gets the ID of this AprilTag
    public int getId() {
        return id;
    }
    // Get distance from
    public double getRange() {
        return range;
    }
    // Get how left or right is the robot from the AprilTag
    public double getBearing() {
        return bearing;
    }
    // Get angle of robot to the AprilTag
    public double getYaw() {
        return yaw;
    }
}
