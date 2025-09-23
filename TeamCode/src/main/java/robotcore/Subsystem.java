package robotcore;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;



public abstract class Subsystem {
    protected HardwareMap hardwareMap;

    protected Telemetry telemetry;

    protected Gamepad gamepad1;

    protected Gamepad gamepad2;

    public abstract void init();

 }
