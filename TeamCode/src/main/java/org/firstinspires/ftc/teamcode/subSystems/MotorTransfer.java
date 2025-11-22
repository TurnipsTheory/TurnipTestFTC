package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotcore.Subsystem;


public class MotorTransfer extends Subsystem {

    boolean isOn;
    private DcMotor TransferMotor = null;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        TransferMotor = hardwareMap.get(DcMotor.class, "transfer_motor");
    }

    public void runTransfer(){
        if (gamepad1.bWasPressed()) {
            if (isOn) {
                isOn = false;
                TransferMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                TransferMotor.setPower(1.0);
                telemetry.addLine("on");
            }
        }



    }
}
