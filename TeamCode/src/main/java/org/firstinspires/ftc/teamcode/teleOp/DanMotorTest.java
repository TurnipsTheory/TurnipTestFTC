package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Flywheel;
import org.firstinspires.ftc.teamcode.subSystems.Intake;
import org.firstinspires.ftc.teamcode.subSystems.Transfer;


@TeleOp(name="DanMotorTest")
public class DanMotorTest extends OpMode {
    private DcMotor danMotor = null;
    boolean isOn = false;


    public void init(){

        danMotor = hardwareMap.get(DcMotor.class,"dan_motor");
        danMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()){
            if (isOn){
                isOn = false;
                danMotor.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                danMotor.setPower(1.0);
                telemetry.addLine("on");
            }

        }
    }

}
