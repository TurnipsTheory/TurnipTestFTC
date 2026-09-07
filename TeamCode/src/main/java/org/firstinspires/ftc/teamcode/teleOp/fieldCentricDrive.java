package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subSystems.Slides;
import org.firstinspires.ftc.teamcode.subSystems.fieldCentricMecanum;
import org.firstinspires.ftc.teamcode.subSystems.arm;

import java.util.Locale;

@TeleOp(name="field centric drive")
public class fieldCentricDrive extends OpMode{
    public double oldTime = 0;
    public final double num = 10;
    public boolean collecting = false, intake = true;
    fieldCentricMecanum drive = new fieldCentricMecanum();
    Slides slide = new Slides();
    arm arm = new arm();
    double axial, lateral, yaw, translation = 1, rotation = 90/320.0, wrist = 0.5;

    public void init() {
        drive.instantiateSubsystem(this);
        slide.instantiateSubsystem(this);
        drive.init(hardwareMap, telemetry);
        arm.init(this);
        arm.armInit();
        slide.init();
        arm.horizontal(intake);
    }

    public void loop() {
        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        //intake logic (yes disgusting if loop spam, i know)
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
        //drive train (oh so clean)
        drive.fieldCentric(axial, lateral, yaw);

        //slides (beautiful)
        slide.runSlides(); //runs slides
        slide.update(); //PID engine stuff
        slide.telemetry(telemetry); //sends telemetry to Driver Station

        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
    }
}
