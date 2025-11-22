package org.firstinspires.ftc.teamcode.teleOp;

import android.text.method.TransformationMethod;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Flywheel;
import org.firstinspires.ftc.teamcode.subSystems.Intake;
import org.firstinspires.ftc.teamcode.subSystems.MotorOuttake;
import org.firstinspires.ftc.teamcode.subSystems.MotorTransfer;
import org.firstinspires.ftc.teamcode.subSystems.Transfer;


@TeleOp(name="RoboTwoOpMode")
public class RoboTwoOpMode extends OpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();
    Intake Intake = new Intake();
    MotorOuttake MotorOuttake = new MotorOuttake();
    MotorTransfer MotorTransfer = new MotorTransfer();


    public void init(){
        DriveTrain.init(this);
        Intake.init(this);
        MotorOuttake.init(this);
        MotorTransfer.init(this);
    }

    @Override
    public void loop() {

        DriveTrain.mecanumDrive();
        Intake.runIntake();
        MotorOuttake.runOuttake();
        MotorTransfer.runTransfer();

    }
}
