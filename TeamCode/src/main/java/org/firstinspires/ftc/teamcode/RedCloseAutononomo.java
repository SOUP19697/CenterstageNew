package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Red Close Score")
public class RedCloseAutononomo extends LinearOpMode {
    private SOUPHardwares_CenterStage soupRobot;
    OpenCvCamera webcam;
    RightMidRedPipeline pipeline;
    RightMidRedPipeline.PropPosition snapshotAnalysis = RightMidRedPipeline.PropPosition.LEFT;

    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map
        soupRobot = new SOUPHardwares_CenterStage(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new RightMidRedPipeline();
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
            telemetry.addData("Last Right Color Average", pipeline.getLastRightColorAverage());
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
                soupRobot.driveForward(0.6, 1, 1100);
                soupRobot.turnRight(0.6, 1, 1000);
                soupRobot.driveBackwards(0.5, 0.15, 800);
                //drop purple pixel
                soupRobot.placePixelDelicatelyRed();
                //head towards board
                soupRobot.driveForward(0.8, 1.4, 1500);
                soupRobot.driveLeft(0.7, 0.4, 800);
                //score yellow pixel
                soupRobot.scorePixel(0.7, 1400, 1000);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.liftToPosition(0.8, 1800, 500);
                soupRobot.driveBackwards(0.1, 1, 1000);
                //pray the servos do what they're supposed to
                soupRobot.grab1.setPosition(0);
                soupRobot.grab2.setPosition(0.05);
                soupRobot.grab1.setPosition(0);
                //close lift
                soupRobot.lifterDCMotor_Left.setPower(0.4);
                soupRobot.lifterDCMotor_Right.setPower(0.4);
                soupRobot.lifterDCMotor_Left.setTargetPosition(0);
                soupRobot.lifterDCMotor_Right.setTargetPosition(0);
                soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //go backstage
                soupRobot.driveRight(0.7,1.4, 2000);
                soupRobot.driveForward(0.7, 0.6, 1000);
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
                soupRobot.driveForward(0.8, 1.1, 1500);
                soupRobot.turnLeft(0.6, 1.95, 1500);
                //drop purple pixel
                soupRobot.placePixelDelicatelyRed();
                //head towards board
                soupRobot.driveLeft(0.8, 1, 1000);
                soupRobot.turnLeft(0.7, 0.95, 1000);
                soupRobot.driveForward(0.7, 0.2, 600);;
                soupRobot.driveLeft(0.5, 0.1, 500);
                //score yellow pixel
                soupRobot.scorePixel(0.7, 1400, 1000);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.liftToPosition(0.8, 1800, 500);
                soupRobot.driveBackwards(0.1, 1, 1000);
                //pray the servos do what they're supposed to
                soupRobot.grab1.setPosition(0);
                soupRobot.grab2.setPosition(0.05);
                soupRobot.grab1.setPosition(0);
                //close lift
                soupRobot.lifterDCMotor_Left.setPower(0.4);
                soupRobot.lifterDCMotor_Right.setPower(0.4);
                soupRobot.lifterDCMotor_Left.setTargetPosition(0);
                soupRobot.lifterDCMotor_Right.setTargetPosition(0);
                soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //go backstage
                soupRobot.driveRight(0.8,1, 1500);
                soupRobot.driveForward(0.8, 0.6, 1000);
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
                soupRobot.driveForward(0.4, 0.1, 800);
                soupRobot.driveRight(0.7, 0.7, 1200);
                soupRobot.driveForward(0.7, 0.9, 1200);
                soupRobot.turnRight(0.5, 1, 1000);
                //drop purple pixel
                soupRobot.placePixelDelicatelyRed();
                //head towards board
                soupRobot.driveForward(0.7, 0.5, 1000);
                soupRobot.driveRight(0.6, 0.15, 800);
                //score yellow pixel
                soupRobot.scorePixel(0.7, 1400, 1000);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.grab2.setPosition(0.2);
                soupRobot.liftToPosition(0.8, 1800, 500);
                soupRobot.driveBackwards(0.1, 1, 1000);
                //pray the servos do what they're supposed to
                soupRobot.grab1.setPosition(0);
                soupRobot.grab2.setPosition(0.05);
                soupRobot.grab1.setPosition(0);
                //close lift
                soupRobot.lifterDCMotor_Left.setPower(0.4);
                soupRobot.lifterDCMotor_Right.setPower(0.4);
                soupRobot.lifterDCMotor_Left.setTargetPosition(0);
                soupRobot.lifterDCMotor_Right.setTargetPosition(0);
                soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //go backstage
                soupRobot.driveRight(0.8, 0.7, 1500);
                soupRobot.driveForward(0.8, 0.7, 1000);
                break;
        }
    }
}

//one square forward/back is about 1100
//one square side to side is about 1300
//one 90 degree rotation is about 850