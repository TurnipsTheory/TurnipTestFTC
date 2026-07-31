package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class Flywheel extends OpMode{

    boolean isOn;
    public DcMotorEx OuttakeMotorRight = null;
    public DcMotorEx OuttakeMotorLeft = null;
    public double highVelocity = 1500;
    public double lowVelocity = 900;
    public double curTargetVelocity = highVelocity;
    double FR= 0;
    double PR = 0;
    double FL = 0;
    double PL = 0;
    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;

    boolean rightTuning = true;


    @Override
    public void init() {
        //instantiateSubsystem(opMode);
        OuttakeMotorRight = hardwareMap.get(DcMotorEx.class, "outtake_motor_right");
        OuttakeMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeMotorRight.setDirection(DcMotor.Direction.REVERSE);
        PIDFCoefficients pidfCoefficientsR = new PIDFCoefficients(PR,0,0,FR);
        OuttakeMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficientsR);

        OuttakeMotorLeft = hardwareMap.get(DcMotorEx.class, "outtake_motor_left");
        OuttakeMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeMotorLeft.setDirection(DcMotor.Direction.FORWARD);
        PIDFCoefficients pidfCoefficientsL = new PIDFCoefficients(PL,0,0,FL);
        OuttakeMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficientsL);
        telemetry.addLine("Init  complete");
    }

    @Override
    public void loop() {
        //get all our gamepad commands
        // set target velocity
        // update telemetry

        if (gamepad1.yWasPressed()){
            if (curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            }else {curTargetVelocity = highVelocity; }
        }

        if(gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if(gamepad1.xWasPressed()){
            if (rightTuning){
                rightTuning = false;
            }
            else{
                rightTuning = true;
            }
        }

        if(gamepad1.dpadLeftWasPressed()){
            if(rightTuning){
                FR += stepSizes[stepIndex];
            } else{ FL += stepSizes[stepIndex];}

        }
        if (gamepad1.dpadRightWasPressed()){
            if(rightTuning){
                FR -= stepSizes[stepIndex];
            } else{ FL -= stepSizes[stepIndex];}
        }

        if(gamepad1.dpadUpWasPressed()){
            if(rightTuning){
                PR += stepSizes[stepIndex];
            } else{ PL += stepSizes[stepIndex];}
        }
        if(gamepad1.dpadDownWasPressed()){
            if(rightTuning){
                PR -= stepSizes[stepIndex];
            } else{ PL -= stepSizes[stepIndex];}
        }

        //set new PIDF coefficients
        PIDFCoefficients pidfCoefficientsR = new PIDFCoefficients(PR,0,0,FR);
        OuttakeMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficientsR);
        PIDFCoefficients pidfCoefficientsL = new PIDFCoefficients(PL,0,0,FL);
        OuttakeMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficientsL);

        //set velocity
        OuttakeMotorRight.setVelocity(curTargetVelocity);
        OuttakeMotorLeft.setVelocity(curTargetVelocity);

        double curVelocity = OuttakeMotorLeft.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity","%.2f", curVelocity);
        telemetry.addData("Error","%.2f",error);
        telemetry.addLine("---------------------------");
        telemetry.addData("Right Motor is being tuned: ", "%b", rightTuning);
        telemetry.addData("Tuning PR", "%.4f (D-Pad U/D)", PR);
        telemetry.addData("Tuning FR", "%.4f (D-Pad L/R)", FR);
        telemetry.addData("Step Size", "%.4f (B Button)",stepSizes[stepIndex]);
    }
}
