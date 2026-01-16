package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotcore.Subsystem;


public class MotorTransfer extends Subsystem {

    boolean isOn = false;
    boolean revIsOn = false;
    private DcMotor TransferMotor = null;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        TransferMotor = hardwareMap.get(DcMotor.class, "transfer_motor");
        TransferMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void runTransfer(){
        if (gamepad2.bWasPressed()) {
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
        if (gamepad2.left_bumper){
            if(revIsOn) {
                revIsOn = false;
                TransferMotor.setPower(0.0);
            }
            else {
                revIsOn = true;
                isOn = false;
                TransferMotor.setPower(-1.0);
            }
        }
    }
}
