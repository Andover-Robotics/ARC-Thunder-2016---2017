package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.I2cController;

/**
 * Created by citruseel on 11/2/2016.
 */

@TeleOp(name="ColorSensorTest", group="TeleOp") //currently it is a TeleOp for testing purposes. When you convert to autonomous, just take out the button stuff

public class ColorSensorTest extends OpMode{

    float hsvValues[] = {0F,0F,0F}; //array with (in order) hue, saturation, and value

    // values is a reference to the hsvValues array. Mainly for the background color changing
    final float values[] = hsvValues;

    // get a reference to the RelativeLayout so we can change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

    boolean aButtonPressed = false;

    private DeviceInterfaceModule interfaceModule; //stated interface module

    ColorSensor colorSensor; //stated colorsensor


    public void init(){

        interfaceModule = hardwareMap.deviceInterfaceModule.get("DIM"); //hardware map the device interface module which controls the color sensor
        colorSensor = hardwareMap.colorSensor.get("Color sensor"); //hardware map the color sensor


    }
    public void loop(){

        ChangeSensorState(); //function created and also made in TeleOp to mainly test out what the "colorSensor.enableLed(boolean);" does
        //sensor.color gets either red, green, or blue in values of integers
        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues); //converts the integer values recieved from each color and converts it to the array values

        //hopefully shows on phone what colors are being shown
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
    }

    public void ChangeSensorState(){
        //made it so you don't need to hold down the a button to keep the SensorLED on
        if (aButtonPressed == false && gamepad1.a == true){
            aButtonPressed = true;
        }

        //made it so you don't need to hold down the b button to keep the SensorLED off
        if (aButtonPressed == true && gamepad1.b ==true){
            aButtonPressed = false;
        }

        //turn on the Led that will sense color or turn it off
        if (aButtonPressed == true){
            colorSensor.enableLed(true);
        }
        else {
            colorSensor.enableLed(false);
        }
    }
}
