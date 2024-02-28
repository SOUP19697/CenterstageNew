package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Blue Far Autononomo")
public class BlueFarAutononomo extends LinearOpMode {
    private SOUPHardwares_CenterStage soupRobot;
    OpenCvCamera webcam;
    RightMidBluePipeline pipeline;
    RightMidBluePipeline.PropPosition snapshotAnalysis = RightMidBluePipeline.PropPosition.RIGHT;

    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map
        soupRobot = new SOUPHardwares_CenterStage(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new RightMidBluePipeline();
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
                soupRobot.drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addLine("it went left");
                telemetry.update();
                //line up with left spike tape
                soupRobot.driveForward(0.5, 1.1, 1500);
                soupRobot.turnRight(0.5, 1, 1000);
                soupRobot.driveBackwards(0.5, 0.1, 800);
                //drop purple pixel
                soupRobot.intake.setPower(-0.4);
                sleep(1500);
                soupRobot.intake.setPower(0.4);
                sleep(1000);
                soupRobot.intake.setPower(0);
                soupRobot.driveLeft(0.5, 1, 1500);
                soupRobot.driveBackwards(0.8, 3.5, 6000);
                soupRobot.turnLeft(0.5, 2.2, 2000);
                soupRobot.scorePixel(0.5, 800, 1000);
                soupRobot.returnToClosed(0.1, 6000);
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
                soupRobot.driveForward(0.5, 2, 3000);
                //drop purple pixel
                soupRobot.intake.setPower(-0.4);
                sleep(1500);
                soupRobot.intake.setPower(0.4);
                sleep(1000);
                soupRobot.intake.setPower(0);
                //park
                soupRobot.turnLeft(0.5, 0.95, 1500);
                soupRobot.driveForward(0.8, 3.5, 5000);
                soupRobot.scorePixel(0.5, 600, 1000);
                soupRobot.returnToClosed(0.1, 6000);
                break;
            case RIGHT:
                soupRobot.drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                soupRobot.drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addLine("it went right");
                telemetry.update();
                //line up with left spike tape
                soupRobot.driveForward(0.5, 0.1, 500);
                soupRobot.driveRight(0.5, 0.7, 1500);
                soupRobot.driveForward(0.6, 1, 1500);
                soupRobot.turnRight(0.5, 1, 1000);
                //drop purple pixel
                soupRobot.intake.setPower(-0.4);
                sleep(1500);
                soupRobot.intake.setPower(0.2);
                sleep(1000);
                soupRobot.intake.setPower(0);
                //park
                soupRobot.driveLeft(0.5, 1, 1500);
                soupRobot.driveBackwards(0.8, 4.3, 5000);
                soupRobot.turnLeft(0.5, 2.1, 2000);
                soupRobot.scorePixel(0.5, 800, 1000);
                soupRobot.returnToClosed(0.1, 6000);
                break;
        }

        //soupRobot.driveLeft(0.5, 2000);
    }
}
