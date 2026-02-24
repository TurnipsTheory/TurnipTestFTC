package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;


//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//
//import java.util.List;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//
//import java.util.Locale;

import java.util.Locale;

import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    public DcMotor OuttakeMotorRight = null;
    public DcMotor OuttakeMotorLeft = null;


//    private AprilTagProcessor aprilTag;
//    List<AprilTagDetection> detection = aprilTag.getDetections();
    //double bearingVal = AprilTagTest.camBearing;

    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        OuttakeMotorRight = hardwareMap.get(DcMotorEx.class, "outtake_motor_right");
        OuttakeMotorRight.setDirection(DcMotor.Direction.REVERSE);
        OuttakeMotorLeft = hardwareMap.get(DcMotorEx.class, "outtake_motor_left");
        OuttakeMotorLeft.setDirection(DcMotor.Direction.FORWARD);
        //OuttakeMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
    double power = 0.53;
    public void runOuttake() {
        if(gamepad1.rightBumperWasPressed()){
            power += 0.01;
            if(power > 1.0){
                power = 1.0;
            }
        }
        else if(gamepad1.leftBumperWasPressed()){
            power -= 0.01;
            if(power < 0.0){
                power = 0.0;
            }
        }
        telemetry.addData("Flywheel Power: ", power);

        if (gamepad2.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotorRight.setPower(0.0);
                OuttakeMotorLeft.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                OuttakeMotorRight.setPower(power);
                OuttakeMotorLeft.setPower(power);
                telemetry.addLine("on");
            }
        }
    }
}


