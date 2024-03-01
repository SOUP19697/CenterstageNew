package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="testing things", group="Linear Opmode")
public class tester extends LinearOpMode {
    private SOUPHardwares_CenterStage soupRobot = null;


    @Override
    public void runOpMode() throws InterruptedException {
        //during initialization
        soupRobot = new SOUPHardwares_CenterStage( hardwareMap );

        soupRobot.lifterDCMotor_Left.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        soupRobot.lifterDCMotor_Right.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while ( opModeIsActive() ) {
            //run all the functions in the actual loop

            //wheelTest();
            //liftTest();

            servoTest();

            pixel1Test();

            pixel2Test();

            chassisControl();

            grabbingThing();

        }

    }
    private void grabbingThing() throws InterruptedException {
        //control pixel dropper
        //note: grab1 is the plate and grab2 is the stick that drops the pixel
        if (gamepad1.y || gamepad2.y) {
            //open pixel stick
            soupRobot.grab2.setPosition(0.2);
        } else if (gamepad1.x || gamepad2.x) {
            //close pixel stick
            soupRobot.grab2.setPosition(0.05);

        } else if (gamepad1.b || gamepad2.b) {
            //open plate
            soupRobot.grab1.setPosition(0.4);

        } else if (gamepad1.a || gamepad2.a){
            soupRobot.grab1.setPosition(0);
        }
    }

    private void liftTest() {
        if (gamepad1.dpad_up && (soupRobot.lifterDCMotor_Left.getCurrentPosition() <= 1900)) {
            soupRobot.lifterDCMotor_Left.setPower(0.6);
            soupRobot.lifterDCMotor_Right.setPower(0.6);
            soupRobot.lifterDCMotor_Left.setTargetPosition(soupRobot.lifterDCMotor_Left.getCurrentPosition() + 100);
            soupRobot.lifterDCMotor_Right.setTargetPosition(soupRobot.lifterDCMotor_Left.getCurrentPosition() + 100);
            soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad1.dpad_down && (soupRobot.lifterDCMotor_Left.getCurrentPosition() >= 800)) {
            soupRobot.lifterDCMotor_Left.setPower(0.6);
            soupRobot.lifterDCMotor_Right.setPower(0.6);
            soupRobot.lifterDCMotor_Left.setTargetPosition(soupRobot.lifterDCMotor_Left.getCurrentPosition() - 100);
            soupRobot.lifterDCMotor_Right.setTargetPosition(soupRobot.lifterDCMotor_Left.getCurrentPosition() - 100);
            soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if ((soupRobot.lifterDCMotor_Left.getCurrentPosition() < 800) && !gamepad1.dpad_up) {
            soupRobot.grab1.setPosition(0);
            soupRobot.lifterDCMotor_Left.setPower(0.2);
            soupRobot.lifterDCMotor_Right.setPower(0.2);
            soupRobot.lifterDCMotor_Left.setTargetPosition(0);
            soupRobot.lifterDCMotor_Right.setTargetPosition(0);
            soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    private void pixel1Test() {
        if (gamepad1.dpad_right) {
            soupRobot.pixeler1.setPosition(0);
            telemetry.addData("position 0 pixel 1", soupRobot.pixeler1.getPosition());
            telemetry.update();
        }
        if (gamepad1.dpad_up) {
            soupRobot.pixeler1.setPosition(1);
            telemetry.addData("position 1 pixel 1", soupRobot.pixeler1.getPosition());
            telemetry.update();
        }
    }

    private void pixel2Test() {
        if (gamepad1.dpad_left) {
            soupRobot.pixeler2.setPosition(0);
            telemetry.addData("position 0 pixel 2", soupRobot.pixeler2.getPosition());
            telemetry.update();
        }
        if (gamepad1.dpad_down) {
            soupRobot.pixeler2.setPosition(1);
            telemetry.addData("position 1 pixel 2", soupRobot.pixeler2.getPosition());
            telemetry.update();
        }
    }

    /* private void cueTaylorSwift() {
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, hardwareMap.appContext.getResources().getIdentifier("soundFileName", "raw", hardwareMap.appContext.getPackageName()));
    } */

    private void chassisControl() {
        //move the robot (gamepad 1)
        double multipler = 0.60;
        if ( gamepad1.left_bumper )
            multipler *= 0.55;
        if ( gamepad1.right_bumper )
            multipler *= 0.55;


        double lfspeed = Range.clip((gamepad1.left_stick_y - gamepad1.left_stick_x) - gamepad1.right_stick_x, -multipler, multipler);
        double lrspeed = Range.clip((gamepad1.left_stick_y + gamepad1.left_stick_x) - gamepad1.right_stick_x, -multipler, multipler);
        double rfspeed = Range.clip((gamepad1.right_stick_y + gamepad1.left_stick_x) + gamepad1.right_stick_x, -multipler, multipler);
        double rrspeed = Range.clip((gamepad1.right_stick_y - gamepad1.left_stick_x) + gamepad1.right_stick_x, -multipler, multipler);

        soupRobot.drivetrainDCMotor_FrontLeft.setPower( lfspeed );
        soupRobot.drivetrainDCMotor_FrontRight.setPower( rfspeed );
        soupRobot.drivetrainDCMotor_RearLeft.setPower( lrspeed );
        soupRobot.drivetrainDCMotor_RearRight.setPower( rrspeed );

    }

    private void servoTest() throws InterruptedException {
        if (gamepad1.left_trigger > 0) {
            soupRobot.lifterDCMotor_Left.setTargetPosition(0);
            soupRobot.lifterDCMotor_Right.setTargetPosition(0);

            soupRobot.lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            soupRobot.lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }}

    private void wheelTest() {
        //tests wheels to check if there's a wiring/configuration issue
        if (gamepad1.dpad_up) {
            telemetry.addLine("front right");
            telemetry.update();
            soupRobot.drivetrainDCMotor_FrontRight.setPower(1);
        } else if (gamepad1.dpad_right) {
            telemetry.addLine("back right");
            telemetry.update();
            soupRobot.drivetrainDCMotor_RearRight.setPower(1);
        } else if (gamepad1.dpad_down) {
            telemetry.addLine("back left");
            telemetry.update();
            soupRobot.drivetrainDCMotor_RearLeft.setPower(1);
        } else if (gamepad1.dpad_left) {
            telemetry.addLine("front left");
            telemetry.update();
            soupRobot.drivetrainDCMotor_FrontLeft.setPower(1);
        } else {
            soupRobot.drivetrainDCMotor_FrontLeft.setPower(0);
            soupRobot.drivetrainDCMotor_FrontRight.setPower(0);
            soupRobot.drivetrainDCMotor_RearLeft.setPower(0);
            soupRobot.drivetrainDCMotor_RearRight.setPower(0);
        }
    }

}

// To connect with the robot:
// SDK Path:
// Sylvie's laptop: cd /Users/sylvie/Library/Android/sdk/platform-tools/ ...type all at once
// Lana's laptop: cd C:\Users\Lana Chan\appdata\local\android\sdk\platform-tools ...type one at a time
// ./adb connect 192.168.43.1
