package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotcore.Subsystem;

public class Flywheel extends Subsystem{

    private DcMotor flywheelLeft = null;
    private DcMotor flywheelRight = null;

    boolean isOn = false;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        flywheelLeft = hardwareMap.get(DcMotor.class, "flywheel_left");
        flywheelRight = hardwareMap.get(DcMotor.class, "flywheel_right");

        flywheelRight.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void runFlywheel(){
        if (gamepad1.bWasPressed()){
            if (isOn){
                flywheelLeft.setPower(0.0);
                flywheelRight.setPower(0.0);
                isOn = false;
            } else {
                flywheelRight.setPower(1.0);
                flywheelLeft.setPower(1.0);
                isOn = true;
            }
        }
    }
}
