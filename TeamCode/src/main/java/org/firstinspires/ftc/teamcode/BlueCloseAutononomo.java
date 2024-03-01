package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Blue Close Score")
public class BlueCloseAutononomo extends LinearOpMode {
    private SOUPHardwares_CenterStage soupRobot;
    OpenCvCamera webcam;
    LeftMidBluePipeline pipeline;
    LeftMidBluePipeline.PropPosition snapshotAnalysis = LeftMidBluePipeline.PropPosition.RIGHT;

    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map
        soupRobot = new SOUPHardwares_CenterStage(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new LeftMidBluePipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            public void onError(int errorCode) {}
        });

        /*  INIT-Loop
            Replaces waitForStart() */

        while (!isStarted() && !isStopRequested()) {
            //grab all the data
            telemetry.addData("Left Count", pipeline.getLeftCount());
            telemetry.addData("Middle Count", pipeline.getMidCount());
            telemetry.addData("Right Count", pipeline.getRightCount());
            telemetry.addData("Last Left Color Average", pipeline.getLastLeftColorAverage());
            telemetry.addData("Last Mid Color Average", pipeline.getLastMidColorAverage());
            telemetry.addData("Realtime Analysis", pipeline.getAnalysis());
            telemetry.update();

            // Don't murder your CPU by running this over and over
            sleep(50);
        }

        snapshotAnalysis = pipeline.getAnalysis();

        switch (snapshotAnalysis) {
            //check where the prop is and run its respective auto
            case LEFT:
                //reset encoders on wheels
                soupRobot.drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addLine("it went left");
                telemetry.update();
                //line up with left spike tape
                soupRobot.driveForward(0.4, 0.1, 800);
                soupRobot.driveLeft(0.4, 0.75, 2500);
                soupRobot.driveForward(0.4, 0.9, 1500);
                soupRobot.turnLeft(0.5, 1, 1000);
                //drop purple pixel
                soupRobot.placePixelDelicatelyBlue();
                //head towards board
                soupRobot.driveForward(0.4, 0.5, 1500);
                soupRobot.driveLeft(0.6, 0.1, 800);
                //score yellow pixel
                soupRobot.scorePixel(0.5, 1400, 2000);
                soupRobot.liftToPosition(0.5, 1800, 1000);
                soupRobot.driveBackwards(0.1, 1, 1000);
                soupRobot.returnToClosed(0.3, 3000);
                //go backstage
                soupRobot.driveLeft(0.8, 0.7, 1500);
                soupRobot.driveForward(0.8, 0.7, 1000);
                break;

            case MIDDLE:
                telemetry.addLine("it went middle");
                telemetry.update();
                //reset encoders on wheels
                soupRobot.drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //line up with spike tape and dodge prop
                soupRobot.driveForward(0.7, 0.95, 1000);
                soupRobot.turnRight(0.5, 2, 3000);
                //drop purple pixel
                soupRobot.placePixelDelicatelyBlue();
                //head towards board
                soupRobot.driveRight(0.7, 1, 1000);
                soupRobot.turnRight(0.7, 0.9, 1000);
                soupRobot.driveForward(0.7, 0.3, 800);
                soupRobot.driveRight(0.5, 0.1, 800);
                //score yellow pixel
                soupRobot.scorePixel(0.5, 1300, 2000);
                soupRobot.liftToPosition(0.5, 1800, 1000);
                soupRobot.driveBackwards(0.2, 1, 1000);
                soupRobot.returnToClosed(0.3, 3000);
                //go backstage
                soupRobot.driveLeft(0.8,1, 1500);
                soupRobot.driveForward(0.8, 0.8, 1000);
                break;
            case RIGHT:
                //reset encoders on wheels
                soupRobot.drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addLine("it went right");
                telemetry.update();
                //line up with right spike tape
                soupRobot.driveForward(0.5, 1, 1500);
                soupRobot.turnLeft(0.5, 1, 1000);
                soupRobot.driveBackwards(0.5, 0.1, 800);
                //drop purple pixel
                soupRobot.placePixelDelicatelyBlue();
                //head towards board
                soupRobot.driveForward(0.5, 1.3, 2000);
                soupRobot.driveRight(0.5, 0.45, 800);
                //score yellow pixel
                soupRobot.scorePixel(0.5, 1400, 2000);
                soupRobot.liftToPosition(0.5, 1800, 1000);
                soupRobot.driveBackwards(0.1, 1, 1000);
                soupRobot.returnToClosed(0.3, 3000);
                //go backstage
                soupRobot.driveLeft(0.5,1.25, 2500);
                soupRobot.driveForward(0.5, 0.8, 1000);
                break;
        }
    }
}

//one square forward/back is about 1100
//one square side to side is about 1300
//one 90 degree rotation is about 850