package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;

import robotcore.Subsystem;

@TeleOp(name="MainOpMode")
public class MainOpMode extends LinearOpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();

    public void initialize() {
        DriveTrain.init();
    }

    @Override
    public void runOpMode() {
        waitForStart();
        DriveTrain.mecanumDrive();

    }
}
