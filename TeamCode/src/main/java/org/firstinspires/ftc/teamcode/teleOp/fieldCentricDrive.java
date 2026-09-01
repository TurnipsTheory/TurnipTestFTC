package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subSystems.fieldCentricMecanum;
import org.firstinspires.ftc.teamcode.subSystems.arm;
@TeleOp(name="field centric drive")
public class fieldCentricDrive extends OpMode{
    double oldTime = 0;
    public final double num = 10;

    fieldCentricMecanum drive = new fieldCentricMecanum();
    arm arm = new arm();
    double axial, lateral, yaw, translation, rotation, wrist;

    public void init() {
        drive.init(hardwareMap, telemetry);
        arm.init(this);
        arm.armInit();
    }

    public void loop() {

        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        translation = gamepad2.left_stick_y;
        rotation = gamepad2. left_stick_x;
        wrist = gamepad2.right_stick_x;

        drive.fieldCentric(axial, lateral, yaw);
        arm.moveArm(translation, rotation, wrist);
//        arm.armFold();

        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
    }
}
