package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
import org.firstinspires.ftc.teamcode.subSystems.Intake;
import org.firstinspires.ftc.teamcode.subSystems.MotorOuttake;
import org.firstinspires.ftc.teamcode.subSystems.Slides;
import org.firstinspires.ftc.teamcode.subSystems.Turret;


@TeleOp(name="MainOpMode")
public class MainOpMode extends OpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();
    Intake Intake = new Intake();
    MotorOuttake MotorOuttake = new MotorOuttake();
    Turret Turret = new Turret();
    Slides Slides = new Slides();



    public void init(){
        Turret.init(this);
        DriveTrain.init(this);
        Intake.init(this);
        MotorOuttake.init(this);
        Slides.init(this);
    }

    @Override
    public void loop() {
        Turret.centerTurret();
        DriveTrain.mecanumDrive();
        Intake.runIntake();
        MotorOuttake.runOuttake();
        telemetry.update();
        Slides.testing();
    }
}
