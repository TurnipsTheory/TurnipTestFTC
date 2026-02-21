package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.teleOp.RTPAxon;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Locale;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import robotcore.Subsystem;

public class Turret extends Subsystem {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    double bearingVal = 0;
    private RTPAxon axon = null;
    boolean moving = false;
    boolean manuel = false;

    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        initAprilTag();
        CRServo turretServo = hardwareMap.get(CRServo.class, "turret_servo");
        AnalogInput turretEncoder = hardwareMap.get(AnalogInput.class, "turret_servo_encoder");
        axon = new RTPAxon(turretServo, turretEncoder);
//      axon.setPidCoeffs(0.019, 0.0428, 0.0001);
        axon.setPidCoeffs(0.021, 0.002, 0.0003);
    }

    public void centerTurret() {
        // Get list of detected AprilTags
        List<AprilTagDetection> detections = aprilTag.getDetections();
        axon.update();

        telemetry.addData("# AprilTags Detected", detections.size());
        telemetry.addData("manuel", manuel);
        telemetry.update();

        //            // Display info for each detected tag
        for (AprilTagDetection detection : detections) {
            if (detection.metadata != null && detection.ftcPose != null) {
                bearingVal = Math.round(detection.ftcPose.bearing * 10) / 10.0;
            } else {
                telemetry.addLine(String.format(Locale.US, "\n==== Unknown Tag ID %d ====", detection.id));
                telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)",

                        detection.center.x, detection.center.y));
            }
        }
        if (gamepad2.rightBumperWasPressed()){
            if (manuel) {
                manuel = false;
            }
            else if (!manuel) {
                manuel = true;
                axon.setTargetRotation(0);
            }

        }
        else if (!manuel && aprilTag.getDetections().isEmpty()) {
            axon.setPower(0.0);
        }
        else if (!manuel && Math.abs(bearingVal) > 2.5 && !moving && Math.abs(axon.getTotalRotation()/3 - bearingVal)< 60) {
            axon.changeTargetRotation(bearingVal * -3);
            moving = true;
        } else if (!manuel && axon.isAtTarget(2.8)) {
            moving = false;
        }
        else if (gamepad2.leftBumperWasPressed()){
            moving = false;
            if(manuel){
                manuel = false;
            }
            axon.setPower(0.0);
        }
//    // Close the vision portal when done
//    visionPortal.close();
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







