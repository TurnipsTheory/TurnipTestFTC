package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subSystems.fieldCentricMecanum;
import org.firstinspires.ftc.teamcode.subSystems.arm;

import java.util.Locale;

@TeleOp(name="field centric drive")
public class fieldCentricDrive extends OpMode{
    public double oldTime = 0;
    public final double num = 10;
    public boolean collecting = false, intake = true;
    fieldCentricMecanum drive = new fieldCentricMecanum();
    arm arm = new arm();
    double axial, lateral, yaw, translation, rotation = 90/320.0, wrist = 0.5;

    public void init() {
        translation = 1;
        drive.init(hardwareMap, telemetry);
        arm.init(this);
        arm.horizontal(intake);
        arm.armInit();
    }

    public void loop() {
        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        if (intake) {
            arm.horizontal(true);
            if (!collecting) {
                if (gamepad2.right_bumper && wrist >= 0.31 && wrist < 0.68) {
                    wrist += 0.01;
                } else if (gamepad2.left_bumper && wrist > 0.32 && wrist <= 0.69) {
                    wrist -= 0.01;
                }
                if (gamepad2.left_stick_x > 0 && rotation <= 180/320.0 || gamepad2.left_stick_x < 0 && rotation >= 0) {
                    rotation = rotation + gamepad2.left_stick_x/100.0;
                }
                if (gamepad2.dpadUpWasPressed() && translation >= 1 && translation != 3) {
                    translation += 1;
                } else if (gamepad2.dpadDownWasPressed() && translation <= 3 && translation != 1) {
                    translation -= 1;
                }
                arm.moveArm(translation, rotation, wrist);
            } else {
                arm.collect();
            }
            if (gamepad2.aWasPressed()) {
                collecting = !collecting;
            }
        } else {
            rotation = 90/320.0;
            wrist = 0.5;
            arm.armFold();
        }
        if (gamepad2.bWasPressed()) {
            intake = !intake;
            if (!intake) {
                collecting = false;
            }
        }

        drive.fieldCentric(axial, lateral, yaw);

        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
    }
}
