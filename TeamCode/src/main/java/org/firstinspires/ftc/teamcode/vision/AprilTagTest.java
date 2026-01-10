package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Locale;

@TeleOp(name="AprilTag Detection Test", group="Vision")
public class AprilTagTest extends LinearOpMode {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    @Override
    public void runOpMode() {
        initAprilTag();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Get list of detected AprilTags
            List<AprilTagDetection> detections = aprilTag.getDetections();

            telemetry.addData("# AprilTags Detected", detections.size());

            // Display info for each detected tag
            for (AprilTagDetection detection : detections) {
                if (detection.metadata != null) {
                    telemetry.addLine(String.format(Locale.US, "\n==== Tag ID %d (%s) ====",
                            detection.id, detection.metadata.name));
                    telemetry.addLine(String.format(Locale.US, "XYZ %6.1f %6.1f %6.1f  (inch)",
                            detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format(Locale.US, "PRY %6.1f %6.1f %6.1f  (deg)",
                            detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format(Locale.US, "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)",
                            detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format(Locale.US, "\n==== Unknown Tag ID %d ====", detection.id));
                    telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)",
                            detection.center.x, detection.center.y));
                }
            }

            telemetry.addLine("\nKey:\nXYZ = Translation");
            telemetry.addLine("PRY = Pitch, Roll, Yaw (Rotation)");
            telemetry.addLine("RBE = Range, Bearing, Elevation");

            telemetry.update();
            sleep(20);
        }

        // Close the vision portal when done
        visionPortal.close();
    }

    /**
     * Initialize AprilTag detection
     */
    private void initAprilTag() {
        // Create the AprilTag processor
        aprilTag = new AprilTagProcessor.Builder()
                // Use TAG_36h11 (FTC standard) - default tag family and library
                // .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // default, can omit

                // Enable pose estimation to get 3D position
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                .build();

        // Create the vision portal
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }
}
