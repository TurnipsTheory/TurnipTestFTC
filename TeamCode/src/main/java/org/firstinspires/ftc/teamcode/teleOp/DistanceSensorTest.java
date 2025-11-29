package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name="DistanceMotorTest")
public class DistanceSensorTest extends OpMode {
    private DistanceSensor distanceSensor = null;



    public void init(){

        distanceSensor = hardwareMap.get(DistanceSensor.class,"dan_motor");
    }

    @Override
    public void loop() {
            double distance;

            distance = distanceSensor.getDistance(DistanceUnit.CM);

            telemetry.addData("distance",distance);

        }
    }


