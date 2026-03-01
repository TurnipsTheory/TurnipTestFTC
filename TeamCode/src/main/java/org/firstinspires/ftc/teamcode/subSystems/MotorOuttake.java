package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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
    public static double rpm = 3000;
    FtcDashboard dashboard;
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

        dashboard = FtcDashboard.getInstance();
    }

    public void runOuttake() {
        double targetTicksPerSec = (rpm / 60.0) * 28.0;
        double actualTicksPerSec = -1*OuttakeMotorLeft.getVelocity();
        double actualRPM = (actualTicksPerSec / 28.0) * 60.0;

        if(gamepad1.rightBumperWasPressed()){
            rpm += 100;
        }
        else if(gamepad1.leftBumperWasPressed()){
            rpm -= 100;
        }


        if (gamepad2.xWasPressed()) {
            integralSum = 0;
            lastError = 0;
            timer.reset();
            if (isOn) {
                isOn = false;
            } else {
                isOn = true;
            }
        }
        if (isOn) {
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

            OuttakeMotorLeft.setPower(output);
            OuttakeMotorRight.setPower(-1*output);
        } else {
            OuttakeMotorLeft.setPower(0);
            OuttakeMotorRight.setPower(0);
        }

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("rpm:", rpm);
        packet.put("actualRPM", actualRPM);
        dashboard.sendTelemetryPacket(packet);
        telemetry.addData("rpm", rpm);
        telemetry.update();
    }
}




