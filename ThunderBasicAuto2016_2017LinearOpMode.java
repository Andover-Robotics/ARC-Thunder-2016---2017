package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.I2cAddr;

import java.sql.Time;

/**
 * Created by citruseel on 10/26/2016.
 */
@Autonomous(name="Basic Linear Autonomous", group="Autonomous")

//Place robot backwards at atonomous because the beacon pressers are on the back, so we make the driving as if the robot was backwards

public class ThunderBasicAuto2016_2017LinearOpMode extends LinearOpMode {
    /* Note:
     * When you extend OpMode, you must declare the methods init() and loop()
     */

        /** Declaring electronics
         * This can be done with a separate class and can make creating code much easier / simpler. */
        private DcMotorController motorControllerP0;    // Motor Controller in port 0 of Core
        private DcMotorController motorControllerP1;    // Motor Controller in port 1 of Core

        private DcMotor motor1;                         // Motor 1: port 1 in Motor Controller 1
        private DcMotor motor2;                         // Motor 2: port 2 in Motor Controller 1
        private DcMotor motor3;                         // Motor 3: port 1 in Motor Controller 0
        private DcMotor motor4;                         // Motor 4: port 2 in Motor Controller 0

        private DeviceInterfaceModule interfaceModule; //stated interface module

        ColorSensor colorSensor; //stated colorsensor
        ColorSensor beaconSensor;

        //Each color sensor has it's own I2cAddress, they need to have unique addresses so the systeme doesn't get confused.
        public static final I2cAddr COLOR_SENSOR_ORIGINAL_ADDRESS = I2cAddr.create8bit(0x3c);//this is to create our own i2c address for some reason
        public static final I2cAddr COLOR_SENSOR_CHANGED_ADDRESS = I2cAddr.create8bit(0x3a);

    /* Declaring variables */

    public void runOpMode() throws InterruptedException{
        /** Initializing and mapping electronics (motors, motor controllers, servos, etc.) */
        motorControllerP0 = hardwareMap.dcMotorController.get("MCP0");
        motorControllerP1 = hardwareMap.dcMotorController.get("MCP1");

        motor1 = hardwareMap.dcMotor.get("motorFrontR");
        motor2 = hardwareMap.dcMotor.get("motorFrontL");
        motor3 = hardwareMap.dcMotor.get("motorBack1");
        motor4 = hardwareMap.dcMotor.get("motorBack2");

        /**Setting channel modes
         *  When setting channel modes,  use the names that are declared to the motors. */
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor1.setDirection(DcMotorSimple.Direction.REVERSE); //reversed these on the right side because our robot will be traveling backwards with the pokers in the back
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);


        interfaceModule = hardwareMap.deviceInterfaceModule.get("DIM"); //hardware map the device interface module which controls the color sensor

        colorSensor = hardwareMap.colorSensor.get("Color sensor"); //hardware map the color sensor
        colorSensor.setI2cAddress(COLOR_SENSOR_ORIGINAL_ADDRESS); //we made it so this one has to be this address, need seperate program to change this

        beaconSensor = hardwareMap.colorSensor.get("Beacon Color sensor");
        beaconSensor.setI2cAddress(COLOR_SENSOR_CHANGED_ADDRESS); //we made it so this one has to be this address

        float hsvValues[] = {0F,0F,0F}; //array with (in order) hue, saturation, and value
        float hsvValues2[] = {0F,0F,0F}; //array with (in order) hue, saturation, and value


        colorSensor.enableLed(true);
        beaconSensor.enableLed(true);

        //variables for going to the tape and rotating
        boolean seenTape = false;
        long lastTime = System.currentTimeMillis(); //I believe gets time in milliseconds
        long time = System.currentTimeMillis();
        long changeInTime = (time - lastTime);
        double tapeLengthTraveled = 0; //for now until it is changed later in the autonomous
        double tapeWidth = 2; //in inches; actual tape measurements found on http://www.andymark.com/FTC17-p/am-3160.htm
        double speed = 15.5; //robot's speed at 0.5 power in inches/seconds


        waitForStart();

        while(opModeIsActive()){

            Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues); //converts the integer values recieved from each color and converts it to the array values
            Color.RGBToHSV(beaconSensor.red(), beaconSensor.green(), beaconSensor.blue(), hsvValues2); //converts the integer values recieved from each color and converts it to the array values

            //What happens until robot sees the tape
            if (colorSensor.argb() == 0xFFFFFFFF){ //I believe that .argb() is the hue.
               seenTape = true;
            }
            else{
                MoveForward(0.5);
            }

            //What happens when the robot sees the tape
            if (colorSensor.argb() == 0xFFFFFFFF && seenTape == true){
                lastTime = System.currentTimeMillis();
                MoveForward(0.5);
            }
            if (colorSensor.argb() != 0xFFFFFFFF && seenTape == true){
                time = System.currentTimeMillis();
                changeInTime = (time - lastTime) / 1000;//time taken to go across the tape in seconds
                tapeLengthTraveled = changeInTime * speed; //since we know the speed and the time, we can find the distance in inches
                double turningAngle = Math.sin(tapeWidth/tapeLengthTraveled); //finds angle to turn by
                BackUp(0.5, changeInTime/2); //I believe this will make it so the robot moves to the center of the white tape
                rotateLeft(0.5, 10000000);

            }



            //hopefully shows on phone what colors are being shown
            telemetry.addData("Hue", hsvValues[0]);

            telemetry.addData("Time over tape: ", changeInTime);

            telemetry.update();


        }


    }

    public void MoveForward(double power){
        motor1.setPower(power);
        motor2.setPower(power);
        motor3.setPower(power);
        motor4.setPower(power);
    }

    public void BackUp(double power, long time) throws InterruptedException{
        motor1.setPower(-power);
        motor2.setPower(-power);
        motor3.setPower(-power);
        motor4.setPower(-power);

        Thread.sleep(time);
    }

    public void rotateLeft(double power, long time)throws InterruptedException{
        motor1.setPower(-power);
        motor2.setPower(power);
        motor3.setPower(-power);
        motor4.setPower(power);

        Thread.sleep(time);
    }

}

