package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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


@TeleOp(name="visionTest2", group="Vision")

public class visionTest2 extends LinearOpMode {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    double bearingVal = 0;
    double xVal = 0;
    int direction = 0;
    private CRServo turretServo = null;
    private double power = 0.0;

    // Smoothing variables
    private double smoothedBearing = 0;
    private long lastDetectionTime = 0;
    private static final double SMOOTHING_FACTOR = 0.9;  // 0.0 = no smoothing, 1.0 = max smoothing
    private int times = 1;  // Track last power for derivative control




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
        telemetry.addLine(String.format(Locale.US,"running centering of turret %f", power));
        turretServo.setPower(1.0);
        if (times == 1) {
            power = 1.0;
            turretServo.setDirection(CRServo.Direction.FORWARD);
            times += 1;
        } else if (times == 100) {
            power = -1.0;
            turretServo.setDirection(CRServo.Direction.REVERSE);
            times -= 1;
        } else if (times > 1 ) {
            times = times + (int) Math.round(power);
        }
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
