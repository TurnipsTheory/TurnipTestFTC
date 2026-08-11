package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subSystems.fieldCentricMecanum;

@TeleOp(name="field centric drive")
public class fieldCentricDrive extends OpMode{
    double oldTime = 0;
    fieldCentricMecanum drive = new fieldCentricMecanum();
    double axial, lateral, yaw;

    public void init() {
        drive.init(hardwareMap, telemetry);

    }

    public void loop() {
        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        drive.fieldCentric(axial, lateral, yaw);


        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
    }
}
