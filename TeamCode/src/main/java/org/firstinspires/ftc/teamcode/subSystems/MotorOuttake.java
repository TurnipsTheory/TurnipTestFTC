package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import robotcore.Subsystem;


public class MotorOuttake extends Subsystem {

    boolean isOn;
    public DcMotorEx OuttakeMotorLeft, OuttakeMotorRight;


    double lastError = 0;
    double integralSum = 0;
    double rpm = 3000;

    ElapsedTime timer = new ElapsedTime();


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);

        OuttakeMotorLeft = hardwareMap.get(DcMotorEx.class, "outtake_motor_left");
        OuttakeMotorRight = hardwareMap.get(DcMotorEx.class, "outtake_motor_right");

        OuttakeMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        OuttakeMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        OuttakeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        OuttakeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void runOuttake() {
        if(gamepad1.rightBumperWasPressed()){
            rpm += 100;
            if(rpm > 1.0){
                rpm = 1.0;
            }
        }
        else if(gamepad1.leftBumperWasPressed()){
            rpm -= 100;
            if(rpm < 0.0){
                rpm = 0.0;
            }
        }



        double targetTicksPerSec = (rpm / 60.0) * 28.0;
        double actualTicksPerSec = -1*OuttakeMotorLeft.getVelocity();
        double actualRPM = (actualTicksPerSec / 28.0) * 60.0;
        if (gamepad2.xWasPressed()) {
            if (isOn) {
                isOn = false;
                OuttakeMotorRight.setPower(0.0);
                OuttakeMotorLeft.setPower(0.0);
                telemetry.addLine("off");
            } else {
                isOn = true;
                double error = targetTicksPerSec - actualTicksPerSec;
                double dt = timer.seconds();
                timer.reset();

                integralSum += error * dt;

                double derivative = (error - lastError) / dt;
                lastError = error;

                double output = (12 * targetTicksPerSec)
                        + (200 * error)
                        + (8 * integralSum)
                        + (1 * derivative);

                output = output / 32767.0;
                output = Math.max(-1, Math.min(1, output));

                OuttakeMotorRight.setPower(output);
                OuttakeMotorRight.setPower(-1*output);
                telemetry.addLine("on");
            }
        }
    }
}


