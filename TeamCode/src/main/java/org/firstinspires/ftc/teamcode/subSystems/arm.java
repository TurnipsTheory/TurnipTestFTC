package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.teleOp.RTPAxon;

import java.util.List;
import java.util.Locale;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotcore.Subsystem;
public class arm extends Subsystem {
    public double segment1 = 5.25; // INCHES. final: finalizes vaianblr; public: everyone can use; =: important
    public double segment2 = 6;// in
    public double h;

    public Servo yaw, pitch1r, pitch1l, pitch2, wrist;
    AnalogInput yEncoder, p1Encoder, p2Encoder, wEncoder;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
//        yEncoder = hardwareMap.get(AnalogInput.class, "yEncoder");
//        p1Encoder = hardwareMap.get(AnalogInput.class, "p1Encoder");
//        p2Encoder = hardwareMap.get(AnalogInput.class, "p2Encoder");
//        wEncoder= hardwareMap.get(AnalogInput.class, "wEncoder");
        yaw = hardwareMap.get(Servo.class, "y");
        pitch1r = hardwareMap.get(Servo.class, "p1r");
        pitch1l = hardwareMap.get(Servo.class, "p1l");
        pitch2 = hardwareMap.get(Servo.class, "p2");
//        wrist = hardwareMap.get(Servo.class, "w");
//        pitch2.setDirection(Servo.Direction.REVERSE);
        pitch1l.setDirection(Servo.Direction.REVERSE);

    }
    public void armInit() { //gets it into a starting position
        pitch1r.setPosition(0.195);
        pitch1l.setPosition(0.195);
        pitch2.setPosition(0.375);
        yaw.setPosition(0);
//        wrist.setPosition(2);

    }
    public void armFold() { //folds arm up
        pitch2.setPosition(0);
        pitch1r.setPosition(1);
        pitch1l.setPosition(1);


//        pitch1.setPosition(2);
//        pitch2.setPosition(4);
//        yaw.setPosition(3);
//        wrist.setPosition(2);
    }
    public void moveArm(double translation, double rotation, double wrotation) {
        h = h + translation/10;
        if (h <= 3) {
            h = 3;
        if (h >= 11.25) {
            h = 11.25;
        }
        double v = 0;
        double z = Math.sqrt(Math.pow(h, 2) + Math.pow(v, 2));
        double c = Math.toDegrees(Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2))); //finds the angle c by reversing law of cosines
        double a = Math.abs(Math.toDegrees(Math.asin(Math.sin(c)*segment1/h) + Math.asin(v/z))/200); //finds the
        if (Double.isNaN((a))) {
            a = 89.3580679749/200;
        }
        double r = rotation;
        double w = wrotation;
//        pitch1.setPosition(a);
        telemetry.addLine(String.format(Locale.US, "a: %.4f", a));
//        telemetry.addLine(String.format(Locale.US, "a/150: %6.0f", a/150));


        pitch1r.setPosition(0.195+a);
        pitch1l.setPosition(0.195+a);
//        pitch2.setPosition(c);
//            yaw.setPosition(r);
//        wrist.setPosition(w);
//        if (gamepad2.aWasPressed()) {
//
//        } else{
////
//        }
    }
//    public void armRead() {
//        double voltage = yEncoder.getVoltage(); // Returns voltage (typically 0.0 to 3.3V)
//        double maxVoltage = yEncoder.getMaxVoltage();
//        double positionFraction = voltage / maxVoltage;
//        telemetry.addLine(String.format(Locale.US, "y: %6.0f", positionFraction));
//        voltage = p1Encoder.getVoltage(); // Returns voltage (typically 0.0 to 3.3V)
//        maxVoltage = p1Encoder.getMaxVoltage();
//        positionFraction = voltage / maxVoltage;
//        telemetry.addLine(String.format(Locale.US, "p1: %6.0f", positionFraction));
//        voltage = p2Encoder.getVoltage(); // Returns voltage (typically 0.0 to 3.3V)
//        maxVoltage = p2Encoder.getMaxVoltage();
//        positionFraction = voltage / maxVoltage;
//        telemetry.addLine(String.format(Locale.US, "p2: %6.0f", positionFraction));
//        voltage = wEncoder.getVoltage(); // Returns voltage (typically 0.0 to 3.3V)
//        maxVoltage = wEncoder.getMaxVoltage();
//        positionFraction = voltage / maxVoltage;
//        telemetry.addLine(String.format(Locale.US, "w: %6.0f", positionFraction));
//
    }
}
