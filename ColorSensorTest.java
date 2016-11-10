package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cController;

/**
 * Created by citruseel on 11/2/2016. Note, currently this code only works with one color sensor plugged in
 */

@Autonomous(name="ColorSensorTest", group="Autonomous")

public class ColorSensorTest extends LinearOpMode{ //apparetnly LinearOpMode does not equal step by step things but actually autonomous.... OpMode = controller inputs


    private DeviceInterfaceModule interfaceModule; //stated interface module

    ColorSensor colorSensor; //stated colorsensor
    ColorSensor beaconSensor;

    public static final I2cAddr COLOR_SENSOR_ORIGINAL_ADDRESS = I2cAddr.create8bit(0x3c);//this is to create our own i2c address for some reason
    public static final I2cAddr COLOR_SENSOR_CHANGED_ADDRESS = I2cAddr.create8bit(0x3a);

    public void runOpMode(){

        float hsvValues[] = {0F,0F,0F}; //array with (in order) hue, saturation, and value

        // values is a reference to the hsvValues array. Mainly for the background color changing
        final float values[] = hsvValues;

        interfaceModule = hardwareMap.deviceInterfaceModule.get("DIM"); //hardware map the device interface module which controls the color sensor
        colorSensor = hardwareMap.colorSensor.get("Color sensor"); //hardware map the color sensor
        colorSensor.setI2cAddress(COLOR_SENSOR_ORIGINAL_ADDRESS); //we made it so this one has to be this address, need seperate program to change this
        beaconSensor = hardwareMap.colorSensor.get("Beacon Color sensor");
        beaconSensor.setI2cAddress(COLOR_SENSOR_CHANGED_ADDRESS); //we made it so this one has to be this address


        waitForStart();

        colorSensor.enableLed(true);

        while(opModeIsActive()){

            //Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues); //converts the integer values recieved from each color and converts it to the array values

            //hopefully shows on phone what colors are being shown
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            telemetry.update();

        }

    }

}
