package org.firstinspires.ftc.teamcode.teleOp;

import android.text.method.TransformationMethod;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Flywheel;
import org.firstinspires.ftc.teamcode.subSystems.Intake;
import org.firstinspires.ftc.teamcode.subSystems.Transfer;


@TeleOp(name="MainOpMode")
public class MainOpMode extends OpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();
    Intake Intake = new Intake();
    Flywheel Flywheel = new Flywheel();
    Transfer Transfer = new Transfer();


    public void init(){
        DriveTrain.init(this);
        Intake.init(this);
        Flywheel.init(this);
        Transfer.init(this);
    }

    @Override
    public void loop() {

        DriveTrain.mecanumDrive();
        Intake.runIntake();
        Flywheel.runFlywheel();
        Transfer.runTransfer();



    }
}
