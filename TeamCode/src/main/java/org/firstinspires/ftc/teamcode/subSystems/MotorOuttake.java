package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    private DcMotor OuttakeMotor = null;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        OuttakeMotor = hardwareMap.get(DcMotor.class, "outtake_motor");
    }

    public void runOuttake(){
        if (gamepad1.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                OuttakeMotor.setPower(0.8);
                telemetry.addLine("on");
            }
        }



    }
}
