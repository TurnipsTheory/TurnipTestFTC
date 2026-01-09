package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Mat;

@TeleOp(name="Webcam Vision Test", group="Vision")
public class VisionOpMode extends LinearOpMode {
//example code taken from Chat
//    OpenCvWebcam webcam;
//
//    @Override
//    public void runOpMode() {
//        int cameraMonitorViewId = hardwareMap.appContext
//                .getResources().getIdentifier("cameraMonitorViewId","id",
//                        hardwareMap.appContext.getPackageName());
//
//        webcam = OpenCvCameraFactory.getInstance()
//                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"),
//                        cameraMonitorViewId);
//
//        webcam.setPipeline(new OpenCvPipeline() {
//            @Override
//            public Mat processFrame(Mat input) {
//                return input; // no processing yet
//            }
//        });
//
//        webcam.openCameraDeviceAsync(() -> {
//            webcam.startStreaming(640, 480);
//        });
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            telemetry.addLine("Camera streaming...");
//            telemetry.update();
//            sleep(50);
//        }
//    }
//}
