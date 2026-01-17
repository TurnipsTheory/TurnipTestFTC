package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.teamcode.teleOp.RTPAxon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
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


@TeleOp(name="visionTest2", group="Vision")

public class visionTest2 extends LinearOpMode {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    double bearingVal = 0;
    double xVal = 0;
    int direction = 0;
    private RTPAxon axon = null;




    public void runOpMode() {
        initAprilTag();
        CRServo turretServo = hardwareMap.get(CRServo.class, "turret_servo");
        AnalogInput turretEncoder = hardwareMap.get(AnalogInput.class, "turret_servo_encoder");
        axon = new RTPAxon(turretServo, turretEncoder);
        axon.setPidCoeffs(0.021, 0.002, 0.0003);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            // Get list of detected AprilTags
            List<AprilTagDetection> detections = aprilTag.getDetections();
            axon.update();
//
            telemetry.addData("# AprilTags Detected", detections.size());
            telemetry.addData("running centering of turret #", bearingVal);
            telemetry.addData("target rotation", axon.getTargetRotation());
            telemetry.addData("angle", axon.getCurrentAngle());

//            // Display info for each detected tag
            for (AprilTagDetection detection : detections) {
                if (detection.metadata != null && detection.ftcPose != null) {
                    bearingVal = Math.round(detection.ftcPose.bearing*10)/10.0;
                    xVal = detection.ftcPose.x;
                } else {
                    telemetry.addLine(String.format(Locale.US, "\n==== Unknown Tag ID %d ====", detection.id));
                    telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)",

                            detection.center.x, detection.center.y));
                }
            }

        if (aprilTag.getDetections().isEmpty()) {
            bearingVal = 0.0;
        }
        else if (!axon.isAtTarget(0.5)) {
            axon.changeTargetRotation(bearingVal*3);
            telemetry.addLine(axon.log());
        }
            telemetry.update();
        }

        // Close the vision portal when done
        visionPortal.close();
    }

    public void turretCenter() {
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
