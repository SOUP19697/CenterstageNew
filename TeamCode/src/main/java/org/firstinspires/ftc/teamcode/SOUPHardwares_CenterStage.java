package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SOUPHardwares_CenterStage {

    // DC Motors - chassis motors
    public DcMotor drivetrainDCMotor_RearLeft = null;
    public DcMotor drivetrainDCMotor_FrontLeft = null;
    public DcMotor drivetrainDCMotor_FrontRight = null;
    public DcMotor drivetrainDCMotor_RearRight = null;

    // DC Motors - the lift
    public DcMotor lifterDCMotor_Left = null;
    public DcMotor lifterDCMotor_Right = null;

    //pixel placer
    public Servo pixeler1;
    public Servo pixeler2;

    //airplane
    public DcMotor airplane = null;

    //grabby guys
    public Servo grab1;
    public Servo grab2;

    //intake
    public DcMotor intake = null;

    // System variables
    public HardwareMap hardwareMap = null;
    public ElapsedTime runtime = new ElapsedTime();

    //auto constants
    int turnConstant = 850;
    int sideDriveConstant = 1300;
    int mainDriveConstant = 1100;

    public SOUPHardwares_CenterStage(HardwareMap hwMap) {
        initialize( hwMap );
    }

    private void initialize(HardwareMap hwMap){
        hardwareMap = hwMap;

        // map to hardware in the REV control
        // map to DC motors for the chassis
        drivetrainDCMotor_RearLeft   = hardwareMap.get( DcMotor.class, "drivetrainDCMotor_RearLeft");
        drivetrainDCMotor_FrontLeft  = hardwareMap.get( DcMotor.class, "drivetrainDCMotor_FrontLeft");
        drivetrainDCMotor_FrontRight = hardwareMap.get( DcMotor.class, "drivetrainDCMotor_FrontRight");
        drivetrainDCMotor_RearRight  = hardwareMap.get( DcMotor.class, "drivetrainDCMotor_RearRight");


        // map to DC motors for the lifter/slider
        lifterDCMotor_Left  = hardwareMap.get( DcMotor.class, "lifterDCMotor_Left");
        lifterDCMotor_Right = hardwareMap.get( DcMotor.class, "lifterDCMotor_Right");

        //pixel placer
        pixeler1 = hardwareMap.get(Servo.class, "pixeler1");
        pixeler2 = hardwareMap.get(Servo.class, "pixeler2");

        //airplane launcher
        airplane = hardwareMap.get(DcMotor.class, "airplane");

        //grabby guys
        grab1 = hardwareMap.get(Servo.class, "grab1");
        grab2 = hardwareMap.get(Servo.class, "grab2");

        //intake
        intake = hardwareMap.get(DcMotor.class, "intake");

        // set motor mode
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        lifterDCMotor_Left.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        lifterDCMotor_Right.setMode( DcMotor.RunMode.RUN_USING_ENCODER);

        // set motor directions
        drivetrainDCMotor_RearLeft.setDirection( DcMotor.Direction.FORWARD);
        drivetrainDCMotor_FrontLeft.setDirection( DcMotor.Direction.FORWARD);
        drivetrainDCMotor_FrontRight.setDirection( DcMotor.Direction.REVERSE);
        drivetrainDCMotor_RearRight.setDirection( DcMotor.Direction.REVERSE);
        lifterDCMotor_Left.setDirection(  DcMotor.Direction.FORWARD);
        lifterDCMotor_Right.setDirection(  DcMotor.Direction.REVERSE);
        pixeler1.setDirection(Servo.Direction.FORWARD);
        pixeler2.setDirection(Servo.Direction.REVERSE);

        // SET ZERO POWER BEHAVIOR
        // chassis motors
        drivetrainDCMotor_RearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drivetrainDCMotor_FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drivetrainDCMotor_FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drivetrainDCMotor_RearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // lift motors
        lifterDCMotor_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lifterDCMotor_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // SET POWER = 0 for all motors at the beginning
        drivetrainDCMotor_RearLeft.setPower( 0 );
        drivetrainDCMotor_FrontLeft.setPower( 0 );
        drivetrainDCMotor_FrontRight.setPower( 0 );
        drivetrainDCMotor_RearRight.setPower( 0 );
        lifterDCMotor_Left.setPower( 0 );
        lifterDCMotor_Right.setPower( 0 );
    }
    public void driveForward(double power, double squares, long timeMs) throws InterruptedException {
        //drive forward in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition(-(int)(squares * mainDriveConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition(-(int)(squares * mainDriveConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition(-(int)(squares * mainDriveConstant));
        drivetrainDCMotor_RearRight.setTargetPosition(-(int)(squares * mainDriveConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    public void driveRight(double power, double squares, long timeMs) throws InterruptedException {
        //drive right in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition(-(int)(squares * sideDriveConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition((int)(squares * sideDriveConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition((int)(squares * sideDriveConstant));
        drivetrainDCMotor_RearRight.setTargetPosition(-(int)(squares * sideDriveConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void driveBackwards(double power, double squares, long timeMs) throws InterruptedException {
        //drive back in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition((int)(squares * mainDriveConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition((int)(squares * mainDriveConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition((int)(squares * mainDriveConstant));
        drivetrainDCMotor_RearRight.setTargetPosition((int)(squares * mainDriveConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void driveLeft (double power, double squares, long timeMs) throws InterruptedException {
        //drive left in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition((int)(squares * sideDriveConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition(-(int)(squares * sideDriveConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition(-(int)(squares * sideDriveConstant));
        drivetrainDCMotor_RearRight.setTargetPosition((int)(squares * sideDriveConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void turnRight (double power, double turns, long timeMs) throws InterruptedException {
        //drive left in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition(-(int)(turns * turnConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition((int)(turns * turnConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition(-(int)(turns * turnConstant));
        drivetrainDCMotor_RearRight.setTargetPosition((int)(turns * turnConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void turnLeft (double power, double turns, long timeMs) throws InterruptedException {
        //drive left in auto
        drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(power);

        drivetrainDCMotor_FrontLeft.setTargetPosition((int)(turns * turnConstant));
        drivetrainDCMotor_FrontRight.setTargetPosition(-(int)(turns * turnConstant));
        drivetrainDCMotor_RearLeft.setTargetPosition((int)(turns * turnConstant));
        drivetrainDCMotor_RearRight.setTargetPosition(-(int)(turns * turnConstant));

        drivetrainDCMotor_FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrainDCMotor_RearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMs);

        drivetrainDCMotor_FrontRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearRight.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_FrontLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrainDCMotor_RearLeft.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);drivetrainDCMotor_FrontLeft.setPower(power);
        drivetrainDCMotor_FrontRight.setPower(-power);
        drivetrainDCMotor_RearLeft.setPower(power);
        drivetrainDCMotor_RearRight.setPower(-power);
        Thread.sleep(timeMs);
        drivetrainDCMotor_FrontLeft.setPower(0);
        drivetrainDCMotor_FrontRight.setPower(0);
        drivetrainDCMotor_RearLeft.setPower(0);
        drivetrainDCMotor_RearRight.setPower(0);
    }

    public void liftToPosition (double power, int position, long timeMS) throws InterruptedException {
        lifterDCMotor_Left.setPower(power);
        lifterDCMotor_Right.setPower(power);

        lifterDCMotor_Left.setTargetPosition(position);
        lifterDCMotor_Right.setTargetPosition(position);

        lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMS);

    }

    public void returnToClosed (double power, long timeMS) throws InterruptedException {
        grab2.setPosition(0.05);
        Thread.sleep(500);
        grab1.setPosition(0);

        lifterDCMotor_Left.setPower(power);
        lifterDCMotor_Right.setPower(power);

        lifterDCMotor_Left.setTargetPosition(0);
        lifterDCMotor_Right.setTargetPosition(0);

        lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMS);
    }

    public void scorePixel (double power, int position, long timeMS) throws InterruptedException {
        lifterDCMotor_Left.setPower(power);
        lifterDCMotor_Right.setPower(power);

        lifterDCMotor_Left.setTargetPosition(position);
        lifterDCMotor_Right.setTargetPosition(position);

        lifterDCMotor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterDCMotor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Thread.sleep(timeMS);

        grab1.setPosition(0.4);
        Thread.sleep(1500);
        grab2.setPosition(0.2);
        Thread.sleep(1500);
    }

    public void placePixelDelicatelyBlue () throws InterruptedException {
        pixeler1.setPosition(1);
        Thread.sleep(2000);
        pixeler1.setPosition(0);

    }

    public void placePixelDelicatelyRed () throws InterruptedException {
        pixeler2.setPosition(1);
        Thread.sleep(2000);
        pixeler2.setPosition(0);

    }


}
