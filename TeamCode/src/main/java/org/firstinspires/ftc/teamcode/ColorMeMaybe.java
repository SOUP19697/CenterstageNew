package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ColorMeMaybe {
    //color sensor variables
    public enum pixelColor {
        GREEN, PURPLE, YELLOW, WHITE, NONE
    }

    private pixelColor bottomPixel = pixelColor.NONE;

    private float gain = 90;

    private RevColorSensorV3 colorSesnor;

    public final double upperBound = 100;

    public final double lowerBound = 50;

    //private LinearOpMode opmode;

    public ColorMeMaybe (RevColorSensorV3 colorSesnor) {
        this.colorSesnor = colorSesnor;

        //set gain
        colorSesnor.setGain(gain);
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float newGain) {
        gain = newGain;
        colorSesnor.setGain(gain);
    }

    public pixelColor findPixelColor() {
        NormalizedRGBA color = colorSesnor.getNormalizedColors();
        if (color.red > 0.6 && color.green > 0.9 && color.blue > 0.9) {
            return pixelColor.WHITE;
        }  else if (color.red > 0.4 && color.blue > 0.75) {
            return pixelColor.PURPLE;
        }  else if (color.red > 0.48 && color.green > 0.6) {
            return pixelColor.YELLOW;
        } else if (color.red < 0.23 && color.green > 0.38) {
            return pixelColor.GREEN;
        } else {
            return pixelColor.NONE;
        }
    }

    public NormalizedRGBA returnColors() {
        NormalizedRGBA color = colorSesnor.getNormalizedColors();
        return color;
    }
}