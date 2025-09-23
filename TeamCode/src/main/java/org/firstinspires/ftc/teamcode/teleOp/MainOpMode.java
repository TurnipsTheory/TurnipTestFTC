package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Intake;



@TeleOp(name="MainOpMode")
public class MainOpMode extends OpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();
    Intake Intake = new Intake();


    public void init(){
        DriveTrain.init(this);
        Intake.init(this);
    }

    @Override
    public void loop() {

        DriveTrain.mecanumDrive();
        Intake.runIntake();


    }
}
