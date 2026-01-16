package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    private DcMotor OuttakeMotorRight = null;
    private DcMotor OuttakeMotorLeft = null;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        OuttakeMotorRight = hardwareMap.get(DcMotor.class, "outtake_motor_right");
        OuttakeMotorRight.setDirection(DcMotor.Direction.FORWARD);
        OuttakeMotorLeft = hardwareMap.get(DcMotor.class, "outtake_motor_left");
        OuttakeMotorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void runOuttake(){
        if (gamepad2.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotorRight.setPower(0.0);
                OuttakeMotorLeft.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                OuttakeMotorRight.setPower(0.8);
                OuttakeMotorLeft.setPower(0.8);
                telemetry.addLine("on");
            }
        }



    }
}
