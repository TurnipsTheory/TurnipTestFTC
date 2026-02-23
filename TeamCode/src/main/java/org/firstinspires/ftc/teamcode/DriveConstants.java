package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

/*
 * Constants shared between multiple drive types.
 *
 * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
 * fields may be changed during tuning (such as kA, kStatic, and kV). The others are
 * more fundamental and may not require tuning, but feel free to tune them as well.
 */
public class DriveConstants {
    /*
     * These are motor constants that should be listed online for your motors.
     */
    public static final double TICKS_PER_REV = 537.7;
    public static final double MAX_RPM = 312;

    /*
     * For https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motors/
     *
     * 0: 200 RPM - 435 RPM
     * 1: 312 RPM - 538 RPM
     * 2: 435 RPM - 615 RPM
     *
     * For https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motors/
     *
     * 0: 19.2 RPM - 26.4 RPM
     * 1: 38.4 RPM - 52.8 RPM
     * 2: 115.2 RPM - 143.6 RPM
     * 3: 312 RPM - 410 RPM
     *
     * For https://www.gobilda.com/5201-series-yellow-jacket-planetary-gear-motors/
     *
     * 0: 435 RPM - 538 RPM
     * 1: 615 RPM - 744 RPM
     * 2: 840 RPM - 990 RPM
     * 3: 1100 RPM - 1273 RPM
     * 4: 1243 RPM - 1461 RPM
     * 5: 1620 RPM - 1899 RPM
     */

    public static final boolean RUN_USING_ENCODER = true;
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;
    public static double kVSlip = 0;
    public static double kASlip = 0;

    // These values are used to generate the trajectories for you robot. To ensure proper operation,
    // the constraints should never exceed the calculated limits for your motors.
    // These values are used for the automatic path following commands. Since there are additional
    // factors such as response time, you may need to tune these values for your robot.
    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(0, 0, 0);
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(0, 0, 0);

    public static double LATERAL_MULTIPLIER = 1;

    public static double VX_WEIGHT = 1;
    public static double VY_WEIGHT = 1;
    public static double OMEGA_WEIGHT = 1;

    /*
     * Set to true if using three wheel odometry, or false if using two wheel odometry
     */
    public static final boolean USING_THREE_WHEEL_ODOMETRY = true;

    /*
     * These values are used to generate the trajectories for you robot. To ensure proper operation,
     * the constraints should never exceed the calculated limits for your motors.
     * These values are used for the automatic path following commands. Since there are additional
     * factors such as response time, you may need to tune these values for your robot.
     */
    public static double MAX_VEL = rpmToVelocity(MAX_RPM);
    public static double MAX_ACCEL = MAX_VEL;
    public static double MAX_ANG_VEL = Math.toRadians(180);
    public static double MAX_ANG_ACCEL = Math.toRadians(180);

    /*
     * Adjust these values to match your mounting configuration.
     */
    public static double X_WHEEL_OFFSET = 0; // in inches
    public static double Y_WHEEL_OFFSET = 0; // in inches

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * WHEEL_RADIUS * 2 * Math.PI / 60.0;
    }

    // TODO: Fill in these values based on your robot configuration
    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 1; // output (wheel) / input (motor)
    public static double TRACK_WIDTH = 15; // in

    /*
     * These are the feedforward parameters used to model the drive system. If you are using the
     * built-in velocity PID, these values are used as the kF (feedforward) constant. These values
     * are specific to each robot, so they will need to be tuned. The same kF and kP values can be
     * used for velocity control and position control.
     *
     * kV: The gain that compensates for velocity. This should be set to 1 / (maximum velocity)
     * kA: The gain that compensates for acceleration. This should be set to 1 / (maximum acceleration)
     * kStatic: The gain that compensates for static friction. This should be set to a small value
     *          that allows the robot to overcome static friction.
     *
     * The kV and kA values can be determined by running the DriveVelocityPIDTuner opmode.
     * The kStatic value can be determined by running the DriveFeedforwardTuner opmode.
     *
     * If you are not using the built-in velocity PID, you can set these values to 0.
     */
}
