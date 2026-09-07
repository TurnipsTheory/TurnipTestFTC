package org.firstinspires.ftc.teamcode.subSystems;

import java.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.PwmControl;
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
    private double h = 3, v = 0, a, c, z, w = 0, r = 90/320.0;
    private double range = 160;
    private double theta = 0;
    public Servo yaw, pitch1r, pitch1l, pitch2, wrist, slidel,slider;
    int i = 0;


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
        slidel = hardwareMap.get(Servo.class, "sl");
        slider = hardwareMap.get(Servo.class, "sr");

        wrist = hardwareMap.get(Servo.class, "w");
//        pitch2.setDirection(Servo.Direction.REVERSE);
        slider.setDirection(Servo.Direction.REVERSE);
        pitch1l.setDirection(Servo.Direction.REVERSE);
//        yaw.setDirection(Servo.Direction.REVERSE);


    }

    public void pitch1Set(double theta) {
        pitch1r.setPosition(theta);
        pitch1l.setPosition(theta);
    }
    public void horizontalSet(double theta) {
        slidel.setPosition(theta);
        slider.setPosition(theta);
    }
    public void armInit() { //gets it into a starting position
        pitch1Set(0.195);
        pitch2.setPosition(0.38);
        yaw.setPosition(90/320.0);
        wrist.setPosition(0.5);
        horizontalSet(0);
        telemetry.addLine(String.format(Locale.US, "yaw: %.4f", yaw.getPosition()));
        telemetry.update();

    }

    public void horizontal(boolean intake) {
        if (intake) {
            horizontalSet(0);
        } else {
            horizontalSet(0.3);
        }
    }
    public void armFold() { //folds arm up
        wrist.setPosition(0.5);
        pitch1Set(1);
        if (i >=35) {
            pitch2.setPosition(0);
        }
        if (i >=40) { //60
            yaw.setPosition((180/320.0));
            horizontal(false);
        }
        if (i >= 80) {
        ((PwmControl) pitch1l).setPwmDisable();
        ((PwmControl) pitch1r).setPwmDisable();
        ((PwmControl) pitch2).setPwmDisable();
        ((PwmControl) slidel).setPwmDisable();
        ((PwmControl) slider).setPwmDisable();
        } else {
            i+=1;
        }
        telemetry.addLine(String.format(Locale.US, "pitch2: %.4f", pitch2.getPosition()));
        telemetry.addData("i: ", i);
        telemetry.update();
    }
    public void moveArm(double translation, double rotation, double wrotation) {
        ((PwmControl) pitch1l).setPwmEnable();
        ((PwmControl) pitch1r).setPwmEnable();
        ((PwmControl) pitch2).setPwmEnable();
        ((PwmControl) slidel).setPwmEnable();
        ((PwmControl) slider).setPwmEnable();
        i = 0;
        r = rotation;
        w = wrotation;
//        yaw.setPosition();
        yaw.setPosition(r);
        wrist.setPosition(w);


        if (translation == 3) {
            h = 10.5;
        }
        if (translation == 2) {
            h = 9;
        }
        if (translation == 1) {
            h = 7.5;
        }
        c = Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2)); //finds the angle c by reversing law of cosines
        a = Math.toDegrees(Math.asin((Math.sin(c)*segment2)/h)); //finds the

        if (Double.isNaN((a))) {
            a = 89.3580679749;
        }


        c = Math.toDegrees(c);
        pitch2.setPosition(0.38-((180-c)/320.0));
        pitch1Set(0.195+(a/range));

        telemetry.addLine(String.format(Locale.US, "translation: %.4f", translation));
        telemetry.addLine(String.format(Locale.US, "rotation: %.4f", rotation));
        telemetry.addLine(String.format(Locale.US, "wrist: %.4f", wrotation));
        telemetry.addLine(String.format(Locale.US, "yaw: %.4f", yaw.getPosition()));
        telemetry.update();
//
        }
        public void collect() {
            v = -4.25;
            z = Math.sqrt(Math.pow(h, 2) + Math.pow(v, 2));
            c = Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2)); //finds the angle c by reversing law of cosines
            a = (Math.toDegrees(Math.asin(Math.sin(c)*segment2/z)) + Math.toDegrees(Math.asin(v/z)))/range;
            c = Math.toDegrees(c);
            telemetry.addLine(String.format(Locale.US, "a: %.4f", a));
            telemetry.addLine(String.format(Locale.US, "h: %.4f", h));
            telemetry.addLine(String.format(Locale.US, "v: %.4f", v));
            telemetry.addLine(String.format(Locale.US, "c: %.4f", c));

            telemetry.update();

            pitch1Set(0.195+a);
            pitch2.setPosition(0.38-((180-c)/320.0));
        }

}
