//stolen from brogan m pratt vid

package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.auton.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import robotcore.Subsystem;

import java.util.Locale;

public class fieldCentricMecanum extends Subsystem{
    private Telemetry telemetry;

    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer

    double oldTime = 0;

    private DcMotor frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        //connect actual motors to programmable objects
        frontLeftDrive = hwMap.get(DcMotor.class, "leftFront");
        backLeftDrive = hwMap.get(DcMotor.class, "leftBack");
        frontRightDrive = hwMap.get(DcMotor.class, "rightFront");
        backRightDrive = hwMap.get(DcMotor.class, "rightBack");
        odo = hwMap.get(GoBildaPinpointDriver.class,"odo");

        odo.setOffsets(-142.93, 30.469, DistanceUnit.MM);
        odo.setOffsets(-156.325, 91.442, DistanceUnit.MM);
        odo.setEncoderResolution(74.5027025034, DistanceUnit.MM);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();


        //set directions (stole from DriveTrainTest hopefully works)
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(double axial, double lateral, double yaw) {
        odo.update();

        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;

        double maxPower = 1.0;
        double maxSpeed = 0.3; //for outreach events, etc. so kids can drive without breaking anything or hurting anyone

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        //planning to pid tune to get velocity >> power to prevent weird drift. also could double for auton. actuall ycan i js auton tune and use the same controlelrs here? that would be cool.
        frontLeftDrive.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeftDrive.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRightDrive.setPower(maxSpeed * (frontRightPower / maxPower));
        backRightDrive.setPower(maxSpeed * (backRightPower / maxPower));

    }

    public void fieldCentric(double axial, double lateral, double yaw) {
        double theta = Math.atan2(axial, lateral);
        double r = Math.hypot(lateral, axial);
        Pose2D pos = odo.getPosition();

        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Position", data);
        telemetry.update();

        theta = AngleUnit.normalizeRadians(theta - pos.getHeading(AngleUnit.RADIANS));

        double newAxial = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newAxial, newStrafe, yaw);
    }

    @Override
    public void init(OpMode opMode) {

    }
}
