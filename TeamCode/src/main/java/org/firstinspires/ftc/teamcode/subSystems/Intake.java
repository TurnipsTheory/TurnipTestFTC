package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotcore.Subsystem;

public class Intake extends Subsystem {

    private DcMotor intakeMotor = null;
    boolean isOn = false;


    @Override
    public void init(OpMode opMode) {
        setTelemetry(opMode.telemetry);
        setHardwareMap(opMode.hardwareMap);
        assignGamePads(opMode.gamepad1, opMode.gamepad2);

        intakeMotor = hardwareMap.get(DcMotor.class,"intake_motor");

    }

    public void runIntake(){
        if (gamepad1.aWasPressed()){
            if (isOn){
                isOn = false;
                intakeMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                intakeMotor.setPower(1.0);
                telemetry.addLine("on");
            }
        }



    }

}
