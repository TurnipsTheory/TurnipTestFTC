package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotcore.Subsystem;


public class MotorTransfer extends Subsystem {

    boolean isOn = false;
    boolean revIsOn = false;
    private DcMotor TransferMotor = null;
    private Encoder FlywheelVelocity = null;
    boolean ready = false;


    @Override
    public void init(OpMode opMode) {
        instantiateSubsystem(opMode);
        TransferMotor = hardwareMap.get(DcMotor.class, "transfer_motor");
        TransferMotor.setDirection(DcMotor.Direction.REVERSE);
        FlywheelVelocity = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "outtake_motor_left")));
    }

    public void transferReady() {
        PositionVelocityPair velocityFlywheel = FlywheelVelocity.getPositionAndVelocity();
        double current_velocity = velocityFlywheel.velocity;
        telemetry.addData("Motor Velocity: Ticks/Sec", current_velocity);
        double target_velocity = 2300;
        if (current_velocity <= target_velocity) {
            ready = false;
            TransferMotor.setPower(0.0);
        } else {
            ready = true;
        }
    }

    public void runTransfer() {
        if (gamepad2.b) {
            transferReady();
            //if (ready) {
                TransferMotor.setPower(1.0);
                telemetry.addLine("on");
            }
            else {
                TransferMotor.setPower(0.0);
                telemetry.addLine("off");
            }
        }
    }
//}
