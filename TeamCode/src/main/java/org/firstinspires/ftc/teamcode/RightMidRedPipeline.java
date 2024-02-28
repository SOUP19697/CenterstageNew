package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RightMidRedPipeline extends OpenCvPipeline {

    //define all variables
    public enum PropPosition {
        LEFT, MIDDLE, RIGHT
    }

    private int rightLeftSide = 1220;
    private int rightTopSide = 100;
    private int rightRightSide = 1270;
    private int rightBottomSide = 150;
    private int midLeftSide = 615;
    private int midTopSide = 90;
    private int midRightSide = 665;
    private int midBottomSide = 140;
    private double crThreshold = 150;
    private long leftCount = 0;
    private long midCount = 0;
    long rightCount = 0;
    private double lastRightColorAverage = 0;
    private double lastMidColorAverage = 0;
    private Rect rightRect;
    private Rect midRect;
    private Mat convertedImage = new Mat();
    private final Mat cr = new Mat();
    private Mat rightImage;
    private Mat midImage;
    private Scalar rightColorAverage;
    private Scalar midColorAverage;
    private PropPosition[] lastTenCount = {null, null, null, null, null, null, null, null, null, null};

    @Override
    public Mat processFrame(Mat input) {
        //convert the image to the other format so the lighting doesn't affect it as much
        Imgproc.cvtColor(input, convertedImage, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(convertedImage, cr, 1);

        //take the average color in each section
        rightColorAverage = Core.mean(rightImage);
        midColorAverage = Core.mean(midImage);

        //assign those values to variables
        lastRightColorAverage = rightColorAverage.val[0];
        lastMidColorAverage = midColorAverage.val[0];

        //use a rotating array to only measure the last 10 values; stops from getting false reading while waiting for randomization
        if (rightColorAverage.val[0]> crThreshold) {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.RIGHT;
        }else if (midColorAverage.val[0]> crThreshold) {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.MIDDLE;
        }else {
            rotateArray(lastTenCount);
            lastTenCount[9] = PropPosition.LEFT;
        }

        //define the areas we look at to check for colors
        Imgproc.rectangle(
                input,
                new Point(
                        rightLeftSide,
                        rightTopSide
                ),
                new Point(
                        rightRightSide,
                        rightBottomSide
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
        if ((rightCount > midCount) && (rightCount > leftCount)){
            return PropPosition.RIGHT;
        }else if (midCount>leftCount){
            return PropPosition.MIDDLE;
        }
        return PropPosition.LEFT;
    }

    @Override
    public void init(Mat input) {
        //draw the rectangles
        rightRect = new Rect(
                (int) (rightLeftSide),
                (int) (rightTopSide),
                (int) (rightRightSide-rightLeftSide),
                (int) (rightBottomSide-rightTopSide)
        );
        midRect = new Rect(
                (int) (midLeftSide),
                (int) (midTopSide),
                (int) (midRightSide-midLeftSide),
                (int) (midBottomSide-midTopSide)
        );

        Imgproc.cvtColor(input, convertedImage, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(convertedImage, cr, 1);

        rightImage = cr.submat(rightRect);
        midImage = cr.submat(midRect);
    }

    //getter functions for telemetry
    public double getLastRightColorAverage() {
        return lastRightColorAverage;
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
