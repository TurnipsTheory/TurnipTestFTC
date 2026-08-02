package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subSystems.DriveTrainTest;
@TeleOp(name="drivetrain")

public class drivetrain extends OpMode {
    DriveTrainTest DriveTrain = new DriveTrainTest();

    public void init(){
        DriveTrain.init(this);
    }

    @Override
    public void loop() {
        DriveTrain.mecanumDrive();
        telemetry.update();
    }

}
