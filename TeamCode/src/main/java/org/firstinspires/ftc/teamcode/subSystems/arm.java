package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import robotcore.Subsystem;
//made by the goat (dan)
//https://www.desmos.com/calculator/epskbu3qj6
public class arm extends Subsystem {
    public final double segment1 = 5.25, segment2 = 6; //initializes lengths of the arms
    private double h = 3, //'h' is the variable i use for horizontal distance which is the independent variable/input
    // that i use to calculate the angle that the servos have to turn to.
    // i set it to three here so it has a starting value and the calculations dont return errors
            a, //'a' is the angle that the first servo should turn to based on the desmos graph
            c; //'c' is the angle the second servo at the second joint should turn to based on the desmos graph
    private final double range = 160; //this is max range of rotation for the savox sg1230 "monster torque" servos, which are the
    //servos at the first joint. its a useful unit for later calculations.
    public Servo yaw, pitch1r, pitch1l, pitch2, wrist, slidel,slider; //creates servo objects for all the servos invovled with the subsystem
    int i = 0; //i use this to count time to delay certain actions relative to others since damn java sucks at counting time

    @Override
    public void init(OpMode opMode) { // this is the init method (short for initilize). links all the servo objects to their respective servos and sets their direction.
        instantiateSubsystem(opMode);
        yaw = hardwareMap.get(Servo.class, "y");
        pitch1r = hardwareMap.get(Servo.class, "p1r");
        pitch1l = hardwareMap.get(Servo.class, "p1l");
        pitch2 = hardwareMap.get(Servo.class, "p2");
        slidel = hardwareMap.get(Servo.class, "sl");
        slider = hardwareMap.get(Servo.class, "sr");
        wrist = hardwareMap.get(Servo.class, "w");
        //you can see how vague i tend to be with my nameing conventions.
        // anything with an odd 'r' or 'l' at the end indicates left or right.
        //for example, 'pitch1l' is the left pitch servo of the first joint and 'pitch1r'is the right pitch servo of the first jiont

        slider.setDirection(Servo.Direction.REVERSE); //idk these servos are js weird so i had to reverse their directions so its easier on my brain
        pitch1l.setDirection(Servo.Direction.REVERSE);
    }

    public void pitch1Set(double theta) {
        pitch1r.setPosition(theta);
        pitch1l.setPosition(theta);
    } //since two servos are driving a single object, they must be in sync and always have the same exact value passed to them.
    //to keep things simple for myself, i js made a method to set both of the positions at the same time with one line.
    public void horizontalSet(double theta) {
        slidel.setPosition(theta);
        slider.setPosition(theta);
    }// same thing as 'pitch1Set' method above. i set both servos at the same time with one method.
    public void armInit() { //gets it into a starting position
        pitch1Set(0.195);
        pitch2.setPosition(0.38);
        yaw.setPosition(90/320.0);
        wrist.setPosition(0.5);
        horizontalSet(0);
    } //this is a funciton is used during testing to get the arm completely straight, and based my angle calcuations from there.
    //pretty much useless now

    public void horizontal(boolean intake) {
        if (intake) {
            horizontalSet(0);
        } else {
            horizontalSet(0.3);
        }
    } //the main method for driving the horizontal slides. yeah not a whole lot, all the gamepad logic is handled in fieldCentricDrive.
    // just two states for the slides based on whether the bots actively intaking or not. 0.3 = closed; 0 = open. counterintuitive, but it works. dont ask.
    public void armFold() { //folds arm up using set positions
        wrist.setPosition(0.5);
        pitch1Set(1);
        // all these if conditiosn with i basically is a timer, and is saying that at specific poitns in time, this action should
        // happen. this lets me put in delays between actions.
        if (i >=35) { //here, the second joint folds up 35 cycles/loops after the first joint folds up.
            pitch2.setPosition(0);
        }
        if (i >=40) { //60
            yaw.setPosition((180/320.0));
            horizontal(false);
        }
        if (i >= 80) { //this ones intresting. at the end of 80 loops, which the arm (hopefullY) has finisehd folding up, i kill
            //all power to the servos in the arm to 1. save power 2. they start tweaking out when i dont
        ((PwmControl) pitch1l).setPwmDisable();
        ((PwmControl) pitch1r).setPwmDisable();
        ((PwmControl) pitch2).setPwmDisable();
        ((PwmControl) slidel).setPwmDisable();
        ((PwmControl) slider).setPwmDisable();
        } else { //this thing makes it all work, after every loop it ups 'i' by one. it acts as a loop counter.
            i+=1;
        }
//        telemetry.addData("i: ", i);
//        telemetry.update();
        //debug telemetry
    }
    public void moveArm(double translation, double rotation, double wrotation) { //wowie this is the main guy over here
        ((PwmControl) pitch1l).setPwmEnable(); //first we gotta make sure everythigns turned on and everythings up and running after an armfold.
        ((PwmControl) pitch1r).setPwmEnable();
        ((PwmControl) pitch2).setPwmEnable();
        ((PwmControl) slidel).setPwmEnable();
        ((PwmControl) slider).setPwmEnable();
        i = 0;

// all the big math is all below here. this is the graph that i used to visualize it and work it out:
// https://www.desmos.com/calculator/epskbu3qj6
        h = translation*1.5 + 6; //calculates h based on the button gamepad control logic wtv wtv in fieldCentricDrive
        c = Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2)); //finds the angle c by reversing law of cosines
        a = Math.toDegrees(Math.asin((Math.sin(c)*segment2)/h)); //finds angle a by some funny trigonometry
        c = Math.toDegrees(c); //gotta convert acos to degrees

        if (Double.isNaN((a))) { //handles the case where a ends up being undefined since h is 0. unnecesssary now that it has discrete set positions, but cant hurt ig.
            a = 89.3580679749;
        }

        pitch2.setPosition(0.38-((180-c)/320.0)); //idk waht i did here, but it works.
        pitch1Set(0.195+(a/range)); //adding the angle to the orientation of the servo that makes it straight so its more like drawing from the x-axis.
        yaw.setPosition(rotation); //these are simple to control cos i can js use joysticks (thank god)
        wrist.setPosition(wrotation);

//        telemetry.addLine(String.format(Locale.US, "translation: %.4f", translation));
//        telemetry.addLine(String.format(Locale.US, "rotation: %.4f", rotation));
//        telemetry.addLine(String.format(Locale.US, "wrist: %.4f", wrotation));
//        telemetry.addLine(String.format(Locale.US, "yaw: %.4f", yaw.getPosition()));
//        telemetry.update();
//      more debug telmetry
        }
        public void collect() { //collection function thats SUPPOSED to make it drive straight down onto the sample. lowk doesnt.
            double v = -4.25;
            double z = Math.sqrt(Math.pow(h, 2) + Math.pow(v, 2));
            c = Math.acos((Math.pow(h,2) - Math.pow(segment1, 2) - Math.pow(segment2,2))/(-2*segment1*segment2)); //finds the angle c by reversing law of cosines
            a = (Math.toDegrees(Math.asin(Math.sin(c)*segment2/ z)) + Math.toDegrees(Math.asin(v / z)))/range;
            c = Math.toDegrees(c); // same math as before
//            telemetry.addLine(String.format(Locale.US, "a: %.4f", a));
//            telemetry.addLine(String.format(Locale.US, "h: %.4f", h));
//            telemetry.addLine(String.format(Locale.US, "v: %.4f", v));
//            telemetry.addLine(String.format(Locale.US, "c: %.4f", c));
//            telemetry.update();
            //yes more debug telmetry

            pitch1Set(0.195+a);
            pitch2.setPosition(0.38-((180-c)/320.0));
        }

}
