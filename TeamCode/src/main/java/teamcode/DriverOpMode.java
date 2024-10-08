package teamcode;

import ftclib.command.CommandOpMode;
import ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.Subsystems.DriveSubsystem;
import teamcode.Commands.DefaultDrive;

@TeleOp(name = "Driver Period")
@Disabled
public class DriverOpMode extends CommandOpMode {

    private DriveSubsystem m_drive;
    private GamepadEx m_driverOp;
    private DefaultDrive m_driveCommand;

    @Override
    public void initialize() {
        m_drive = new DriveSubsystem(hardwareMap);

        m_driverOp = new GamepadEx(gamepad1);
        m_driveCommand = new DefaultDrive(m_drive, () -> m_driverOp.getLeftX(), () -> m_driverOp.getLeftY(), () -> m_driverOp.getRightX());

        register(m_drive);
        m_drive.setDefaultCommand(m_driveCommand);
    }

}