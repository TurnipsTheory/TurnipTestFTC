package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Drive Tuning", group="Tuning")
public class DriveTuning extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        TankDrive drive = new TankDrive(hardwareMap, new Pose2d(0, 0, 0));

        telemetry.addLine("Ready for tuning!");
        telemetry.addLine("Use FTC Dashboard to adjust parameters");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.updatePoseEstimate();
            
            telemetry.addData("X", drive.localizer.getPose().position.x);
            telemetry.addData("Y", drive.localizer.getPose().position.y);
            telemetry.addData("Heading (deg)", Math.toDegrees(drive.localizer.getPose().heading.toDouble()));
            
            telemetry.update();
        }
    }
}
