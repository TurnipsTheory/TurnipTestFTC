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
    private final double segment1 = 2;
    public final double segment2 = 3;
    public Servo yaw, pitch1, pitch2, wrist;

    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        yaw = hardwareMap.get(Servo.class, "y");
        pitch1 = hardwareMap.get(Servo.class, "p1");
        pitch2 = hardwareMap.get(Servo.class, "p2");
        wrist = hardwareMap.get(Servo.class, "w");
    }

    public void moveArm(double translation, double rotation, double wrist) {
        double h = translation; //needs some sort adjusting to go rom 0-1 range --> actual distance values.
        double v = 0;
        double z = Math.sqrt(Math.pow(h, 2) + Math.pow(v, 2));
        double c = Math.toDegrees(Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2))); //finds the angle c by reversing law of cosines
        double a = Math.toDegrees(Math.asin(Math.sin(c)*segment1/h) + Math.asin(v/z)); //finds the

        pitch1.setPosition(a);
        pitch2.setPosition(c);


    }

}
