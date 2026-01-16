package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import robotcore.Subsystem;


public class MotorTransfer extends Subsystem {

    boolean isOn = false;
    boolean revIsOn = false;
    private DcMotor TransferMotor = null;

    private Servo TransferGate = null;

    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        TransferMotor = hardwareMap.get(DcMotor.class, "transfer_motor");
        TransferMotor.setDirection(DcMotor.Direction.REVERSE);

        TransferGate = hardwareMap.get(Servo.class,"transfer_gate");
        TransferGate.setDirection(Servo.Direction.REVERSE);


    }

    public void runTransfer() {
        if (gamepad2.b) {

            if (TransferGate.getPosition() != 1.0){
                TransferGate.setPosition(1.0);
                runtime.reset();
            }

            if (runtime.time()>0.5) {
                TransferMotor.setPower(1.0);

                telemetry.addLine("on");

            }
        } else{
            TransferMotor.setPower(0.0);

            TransferGate.setPosition(0.0);
            telemetry.addLine("off");
        }



        }


}
