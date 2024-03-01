package org.firstinspires.ftc.teamcode;
//DEMO PROGRAM FOR CAMERA

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "Ze Camera Test")
public class CameraTest extends LinearOpMode {
    OpenCvCamera webcam;
    ExamplePipeline pipeline;
    ExamplePipeline.PropPosition snapshotAnalysis = ExamplePipeline.PropPosition.RIGHT;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new ExamplePipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            public void onError(int errorCode) {}
        });

        /*
            INIT-Loop
            Replaces waitForStart()
         */
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Realtime Analysis", pipeline.getAnalysis());
            telemetry.update();

            // Dont burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        // START pressed
        snapshotAnalysis = pipeline.getAnalysis();

        telemetry.addData("Snapshot post-START analysis", snapshotAnalysis);
        telemetry.update();

        // Dont' include this in your autonomous, it's just to prevent this test from ending
        while(opModeIsActive()) {
            sleep(50);
        }
    }

    public static class ExamplePipeline extends OpenCvPipeline {
        public enum PropPosition {
            LEFT, MIDDLE, RIGHT
        }

        int leftLeftSide = 12;
        int leftTopSide = 411;
        int leftRightSide = 64;
        int leftBottomSide = 432;

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.rectangle(
                    input,
                    new Point(
                            leftLeftSide,
                            leftTopSide
                    ),
                    new Point(
                            leftRightSide,
                            leftBottomSide
                    ),
                    new Scalar(255, 3, 217), 3
            );

            return input;
        }

        public PropPosition getAnalysis() {
            return PropPosition.RIGHT;
        }

        @Override
        public void init(Mat input) {

        }
    }
}
