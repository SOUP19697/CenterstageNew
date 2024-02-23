package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class LeftMidBluePipeline extends OpenCvPipeline {

    //define all variables
    public enum PropPosition {
        LEFT, MIDDLE, RIGHT
    }

    private int leftLeftSide = 462;
    private int leftTopSide = 100;
    private int leftRightSide = 512;
    private int leftBottomSide = 150;
    private int midLeftSide = 962;
    private int midTopSide = 90;
    private int midRightSide = 1012;
    private int midBottomSide = 140;
    private double cbThreshold = 150;
    private long leftCount = 0;
    private long midCount = 0;
    long rightCount = 0;
    private double lastLeftColorAverage = 0;
    private double lastMidColorAverage = 0;
    private Rect leftRect;
    private Rect midRect;
    private Mat convertedImage = new Mat();
    private Mat cb = new Mat();
    private Mat leftImage;
    private Mat midImage;
    private Scalar leftColorAverage;
    private Scalar midColorAverage;
    private PropPosition[] lastTenCount = {null, null, null, null, null, null, null, null, null, null};

    @Override
    public Mat processFrame(Mat input) {
        //convert the image to the other format so the lighting doesn't affect it as much
        Imgproc.cvtColor(input, convertedImage, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(convertedImage, cb, 2);

        //take the average color in each section
        leftColorAverage = Core.mean(leftImage);
        midColorAverage = Core.mean(midImage);

        //assign those values to variables
        lastLeftColorAverage = leftColorAverage.val[0];
        lastMidColorAverage = midColorAverage.val[0];

        //use a rotating array to only measure the last 10 values; stops from getting false reading while waiting for randomization
        if (leftColorAverage.val[0]> cbThreshold) {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.LEFT;
        }else if (midColorAverage.val[0]> cbThreshold) {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.MIDDLE;
        }else {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.RIGHT;
        }

        //define the areas we look at to check for colors
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
        Imgproc.rectangle(
                input,
                new Point(
                        midLeftSide,
                        midTopSide
                ),
                new Point(
                        midRightSide,
                        midBottomSide
                ),
                new Scalar(255, 200, 0), 3
        );
        return input;
    }
    public PropPosition getAnalysis() {
        //check array and determine where the prop is
        int leftCount = 0;
        int midCount = 0;
        int rightCount = 0;
        for (int i = 0; i < 10; i++) {
            if (lastTenCount[i] == PropPosition.LEFT) {
                leftCount++;
            } else if (lastTenCount[i] == PropPosition.MIDDLE) {
                midCount++;
            } else if (lastTenCount[i] == PropPosition.RIGHT) {
                rightCount++;
            }
        }
        if ((leftCount > midCount) && (leftCount > rightCount)){
            return PropPosition.LEFT;
        }else if (midCount>rightCount){
            return PropPosition.MIDDLE;
        }
        return PropPosition.RIGHT;
    }

    @Override
    public void init(Mat input) {
        //draw the rectangles
        leftRect = new Rect(
                (int) (leftLeftSide),
                (int) (leftTopSide),
                (int) (leftRightSide-leftLeftSide),
                (int) (leftBottomSide-leftTopSide)
        );
        midRect = new Rect(
                (int) (midLeftSide),
                (int) (midTopSide),
                (int) (midRightSide-midLeftSide),
                (int) (midBottomSide-midTopSide)
        );

        Imgproc.cvtColor(input, convertedImage, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(convertedImage, cb, 2);

        leftImage = cb.submat(leftRect);
        midImage = cb.submat(midRect);
    }

    //getter functions for telemetry
    public double getLastLeftColorAverage() {
        return lastLeftColorAverage;
    }
    public double getLastMidColorAverage() {
        return lastMidColorAverage;
    }
    public long getLeftCount() {
        return leftCount;
    }
    public long getMidCount() {
        return midCount;
    }
    public long getRightCount() {
        return rightCount;
    }

    private void rotateArray(PropPosition[] arr) {
        //program to rotate array
        int d = 1; //how much you rotate
        int n = arr.length;

        PropPosition[] temp = new PropPosition[d];

        System.arraycopy(arr, 0, temp, 0, d);

        for (int i = d; i < n; i++) {
            arr[i - d] = arr[i];
        }

        for (int i = 0; i < d; i++) {
            arr[i + n - d] = temp[i];
        }
    }
}
