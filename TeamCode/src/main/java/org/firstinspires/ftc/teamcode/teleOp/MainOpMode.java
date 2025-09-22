package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Intake;

//import robotcore.Subsystem;

@TeleOp(name="MainOpMode")
public class MainOpMode extends LinearOpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();
    Intake Intake = new Intake();

    public void initialize() {
        DriveTrain.init();
    }

    @Override
    public void runOpMode() {
        waitForStart();
        DriveTrain.mecanumDrive();
        Intake.runIntake();


    }
}
