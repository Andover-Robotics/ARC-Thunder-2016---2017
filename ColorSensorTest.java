package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cController;

/**
 * Created by citruseel on 11/2/2016.
 */

public class ColorSensorTest extends OpMode{

    float hsvValues[] = {0F,0F,0F};

    ColorSensor colorSensor;

    public void init(){

        colorSensor = hardwareMap.colorSensor.get("Color sensor");
        colorSensor.enableLed(true);

    }

    public void loop(){

        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues);

        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);
    }
}
