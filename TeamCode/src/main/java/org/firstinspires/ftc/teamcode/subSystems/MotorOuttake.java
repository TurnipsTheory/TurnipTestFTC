package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    private DcMotor OuttakeMotor = null;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        OuttakeMotor = hardwareMap.get(DcMotor.class, "outtake_motor");
        OuttakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void runOuttake(){
        if (gamepad2.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                OuttakeMotor.setPower(0.7);
                telemetry.addLine("on");
            }
        }



    }
}
