package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//
//import java.util.List;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//
//import java.util.Locale;

import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    private DcMotor OuttakeMotor = null;
    //private CRServo turretServo = null;
//    private AprilTagProcessor aprilTag;
//    List<AprilTagDetection> detection = aprilTag.getDetections();
    //double bearingVal = AprilTagTest.camBearing;

    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        OuttakeMotor = hardwareMap.get(DcMotor.class, "outtake_motor");
        //turretServo   = hardwareMap.get(CRServo.class, "turret_servo");
    }

//    public void turretCenter(){
//        if(bearingVal < 0){
//            turretServo.setPower(1.0);
//        }
//        else if(bearingVal > 0){
//            turretServo.setPower(-1.0);
//        }
//        else{
//            turretServo.setPower(0.0);
//        }
//    }

    public void runOuttake(){
        if (gamepad1.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                OuttakeMotor.setPower(0.6);
                telemetry.addLine("on");
            }
        }



    }
}
