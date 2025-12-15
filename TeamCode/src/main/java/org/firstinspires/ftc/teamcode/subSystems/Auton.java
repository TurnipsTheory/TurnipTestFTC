package org.firstinspires.ftc.teamcode.subSystems;

//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.InstantFunction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.Trajectory;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.drive.DriveConstants;
//import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
//import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name = "SampleAuto")
//@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "SampleAuto")
public class Auton extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    //    private DcMotor shooterMotor = null;
//    private Servo intakeServo = null; // Example for an intake/indexing servo
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor intakeMotor;
    DcMotor TransferMotor;
    DcMotor OuttakeMotor;

    boolean intakeOn = false;
    boolean shootingOn = false;

//    public class runIntake implements InstantFunction{
//
//        @Override
//        public void run(){
//            if (intakeOn) {
//                intakeOn = false;
//                intakeMotor.setPower(0.0);
//            }
//            if (!intakeOn) {
//                intakeOn = true;
//                intakeMotor.setPower(0.6);
//            }
//        }
//    }

//    public class shooting implements  InstantFunction{
//
//        @Override
//        public void run(){
//            if (!shootingOn){
//                shootingOn = false;
//                OuttakeMotor.setPower(0.6);
//                ElapsedTime outtakeTimer = new ElapsedTime();
//                if (outtakeTimer.time() >= 500){
//                    TransferMotor.setPower(1.0);
//                }
//                ElapsedTime shootingTimer = new ElapsedTime();
//            }
//            else{
//                shootingOn = true;
//                OuttakeMotor.setPower(0.0);
//                TransferMotor.setPower(0.0);
//            }
//        }
//    }



    @Override
    public void runOpMode() throws InterruptedException{

        frontLeft  = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        backLeft   = hardwareMap.get(DcMotor.class, "leftBack");
        backRight  = hardwareMap.get(DcMotor.class, "rightBack");
        intakeMotor = hardwareMap.get(DcMotor.class,"intake_motor");
        TransferMotor = hardwareMap.get(DcMotor.class, "transfer_motor");
        OuttakeMotor = hardwareMap.get(DcMotor.class, "outtake_motor");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

//        private ElapsedTime runtime = new ElapsedTime();
//        // Constants for encoder calculations (YOU MUST TUNE THESE VALUES)
//        static final double COUNTS_PER_MOTOR_REV = 537.6; // GoBilda 5202 series motor
//        static final double DRIVE_GEAR_REDUCTION = 1.0; // This is a 1:1 gear ratio (adjust if needed)
//        static final double WHEEL_DIAMETER_INCHES = 4.0; // Your wheel diameter
//        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
//        static final double DRIVE_SPEED = 0.5;
//        static final double SHOOT_POWER = 0.8;
//        static final long SHOOT_TIME_MS = 2000; // Time the shooter runs (adjust as needed)


//        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        frontRight.setPower(-1.0);
        frontLeft.setPower(-1.0);
        backLeft.setPower(-1.0);
        backRight.setPower(-1.0);
        OuttakeMotor.setPower(0.55);
        sleep(615);
        frontRight.setPower(0.0);
        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
//        //RED: Strafe RIGHT  BLUE: Strafe LEFT
//        frontRight.setPower(-1.0);  //RED: +  BLUE: -
//        frontLeft.setPower(1.0);  //RED: -  BLUE: +
//        backRight.setPower(1.0);  //RED: +  BlUE: -
//        backLeft.setPower(-1.0);    //RED: -  BLUE: +
//        sleep(300);
        frontRight.setPower(0.0);
        frontLeft.setPower(0.0);
        backRight.setPower(0.0);
        backLeft.setPower(0.0);
        sleep(1000);
        TransferMotor.setPower(1.0);
        sleep(750);
        intakeMotor.setPower(1.0);
        sleep(10000);
        OuttakeMotor.setPower(0.0);
        intakeMotor.setPower(0.0);
        TransferMotor.setPower(0.0);

//        //starting pose
//        Pose2d beginPose = new Pose2d(new Vector2d(55,50), Math.toRadians(315));
//
//        //create RR drive obj
//        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
//        waitForStart();
//        //Auton Path
//        Action path = drive.actionBuilder(beginPose)
//                //test Auton
//                .splineTo(new Vector2d(26.5,11), Math.toRadians(315))
//                //.splineToLinearHeading(26.5,11)
//                .stopAndAdd(new shooting())
//                .stopAndAdd(new runIntake())
//                .build();
//        Actions.runBlocking(new SequentialAction((path)));
//        intakeMotor.setPower(0.0);
//        TransferMotor.setPower(0.0);
    }
}