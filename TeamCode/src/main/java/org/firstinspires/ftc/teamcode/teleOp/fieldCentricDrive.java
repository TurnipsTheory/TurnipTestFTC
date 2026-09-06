package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subSystems.Slides;
import org.firstinspires.ftc.teamcode.subSystems.fieldCentricMecanum;

@TeleOp(name="field centric drive")
public class fieldCentricDrive extends OpMode{
    double oldTime = 0;
    fieldCentricMecanum drive = new fieldCentricMecanum();
    Slides slide;
    double axial, lateral, yaw;

    public void init() {
        drive.instantiateSubsystem(this);
        drive.init(hardwareMap, telemetry);


        slide = new Slides();
        slide.instantiateSubsystem(this);
        slide.init();
    }

    public void loop() {
        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        drive.fieldCentric(axial, lateral, yaw);

        slide.runSlides(); //runs slides
        slide.update(); //PID engine stuff
        slide.telemetry(telemetry); //sends telemetry to Driver Station

        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
    }
}
