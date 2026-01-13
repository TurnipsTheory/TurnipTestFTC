package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Locale;

////@TeleOp(name= "Shared Values", group="Vision")
//public class SharedVariables extends LinearOpMode{
//    public static double camBearing = 0.0;
//    private AprilTagProcessor aprilTag;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        List<AprilTagDetection> detections = aprilTag.getDetections();
//
//        camBearing = detections.get();
//    }
//}
//    public class Global{
//    public static double camBearing;
//}
@TeleOp(name="AprilTag Detection Test", group="Vision")
public class AprilTagTest extends LinearOpMode {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    double bearingVal = 0;
    double xVal = 0;
    int direction = 0;
    private CRServo turretServo = null;

    // Smoothing variables
    private double smoothedBearing = 0;
    private long lastDetectionTime = 0;
    private static final double SMOOTHING_FACTOR = 0.9;  // 0.0 = no smoothing, 1.0 = max smoothing
    private double lastPower = 0;  // Track last power for derivative control


    @Override

    public void runOpMode() {
        turretServo = hardwareMap.get(CRServo.class, "turret_servo");
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
                if (detection.metadata != null && detection.ftcPose != null) {
                    telemetry.addLine(String.format(Locale.US, "\n==== Tag ID %d (%s) ====",
                            detection.id, detection.metadata.name));
                    telemetry.addLine(String.format(Locale.US, "XYZ %6.1f %6.1f %6.1f  (inch)",
                            detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format(Locale.US, "PRY %6.1f %6.1f %6.1f  (deg)",
                            detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format(Locale.US, "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)",
                            detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                    bearingVal = detection.ftcPose.bearing;
                    xVal = detection.ftcPose.x;

                } else {
                    telemetry.addLine(String.format(Locale.US, "\n==== Unknown Tag ID %d ====", detection.id));
                    telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)",
                            detection.center.x, detection.center.y));
                }
            }

            telemetry.addLine("\nKey:\nXYZ = Translation");
            telemetry.addLine("PRY = Pitch, Roll, Yaw (Rotation)");
            telemetry.addLine("RBE = Range, Bearing, Elevation");

            turretCenter();

            telemetry.update();
        }

        // Close the vision portal when done
        visionPortal.close();

    }

    public void turretCenter() {
        telemetry.addLine("running centering of turret");

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        long currentTime = System.currentTimeMillis();

        if (!currentDetections.isEmpty() && currentDetections.get(0).ftcPose != null) {
            // Get fresh bearing value from current detection
            double rawBearing = currentDetections.get(0).ftcPose.bearing;

            // Apply exponential smoothing to reduce jitter
            if (lastDetectionTime == 0) {
                // First detection - initialize
                smoothedBearing = rawBearing;
            } else {
                smoothedBearing = (SMOOTHING_FACTOR * smoothedBearing) + ((1 - SMOOTHING_FACTOR) * rawBearing);
            }

            lastDetectionTime = currentTime;

            telemetry.addLine(String.format(Locale.US, "Raw bearing: %.2f", rawBearing));
            telemetry.addLine(String.format(Locale.US, "Smoothed: %.2f", smoothedBearing));
        }

        // Grace period: continue tracking for 200ms after losing tag
        long timeSinceLastDetection = currentTime - lastDetectionTime;
        boolean hasRecentDetection = (lastDetectionTime > 0) && (timeSinceLastDetection < 200);

        if (!hasRecentDetection) {
            // No tag detected recently - stop the servo
            turretServo.setPower(0.0);
            telemetry.addLine("No tag - stopped");
        } else {
            // Tag detected recently - use proportional control on smoothed bearing
            double deadband = 5.0;  // Stop when within 3.5 degrees (wider to prevent oscillation)
            double kP = 0.08;        // Proportional gain (reduced from 0.015)

            if (Math.abs(smoothedBearing) < deadband) {
                turretServo.setPower(0.0);  // Centered!
                lastPower = 0;
                telemetry.addLine("CENTERED");
            } else {
                // Proportional control: power proportional to bearing error
                double power = -kP * smoothedBearing;

                // Only add minimum power if we're far from target
                if (Math.abs(smoothedBearing) > 10.0) {
                    double minPower = 0.05;  // Reduced from 0.08
                    if (Math.abs(power) > 0 && Math.abs(power) < minPower) {
                        power = Math.signum(power) * minPower;
                    }
                }

                // Clamp power to reasonable range (reduced max)
                power = Math.max(-0.15, Math.min(0.15, power));

                // Damping: reduce power change rate to prevent oscillation
                double maxPowerChange = 0.05;
                double powerChange = power - lastPower;
                if (Math.abs(powerChange) > maxPowerChange) {
                    power = lastPower + Math.signum(powerChange) * maxPowerChange;
                }

                lastPower = power;
                turretServo.setPower(power);
                telemetry.addLine(String.format(Locale.US, "Bearing: %.1f° Power: %.3f", smoothedBearing, power));
            }
        }
    }
//
//    }
//        if (bearingVal < -0.5) {
//                turretServo.setPower(1.0);
//            } else if (bearingVal > 0.5) {
//                turretServo.setPower(-1.0);
//            } else {
//                turretServo.setPower(0.0);
//            }
//    }

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
