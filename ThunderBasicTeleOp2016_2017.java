package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by citruseel on 10/14/2016.
 */

@TeleOp(name="Basic TeleOp", group="TeleOp")

public class ThunderBasicTeleOp2016_2017 extends OpMode {

    private DcMotorController motorControllerP0;    // Motor Controller in port 0 of Core
    private DcMotorController motorControllerP1;    // Motor Controller in port 1 of Core

    private DcMotor motor1;                         // Motor 1: port 1 in Motor Controller 1
    private DcMotor motor2;                         // Motor 2: port 2 in Motor Controller 1
    private DcMotor motor3;                         // Motor 3: port 1 in Motor Controller 0
    private DcMotor motor4;                         // Motor 4: port 2 in Motor Controller 0


    @Override
    public void init() {
        /* Initializing and mapping electronics*/
        motorControllerP0 = hardwareMap.dcMotorController.get("MCP0");
        motorControllerP1 = hardwareMap.dcMotorController.get("MCP1");


        motor1 = hardwareMap.dcMotor.get("motorFrontR");        //MCP1
        motor2 = hardwareMap.dcMotor.get("motorFrontL");        //MCP1
        motor3 = hardwareMap.dcMotor.get("motorBack1");         //MCP0          Back of motor 1
        motor4 = hardwareMap.dcMotor.get("motorBack2");         //MCP0          Back of motor 2

        /*Setting channel modes*/
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);



    }

    @Override
    public void loop() {                                          //constant loop that rechecks about every 20ms
        double GearRatio = 0.25;                                  //We geared up from 80 teeth to 40 teeth: this is needed so that we don't go too fast)
        double leftpower = gamepad1.left_stick_y * GearRatio;     //set's a value for power equal to the opposite of the value of the joysticks for the left
        double rightpower =gamepad1.right_stick_y * GearRatio;    //set's a value for power equal to the value of the joysticks for the right

        leftpower = Range.clip(leftpower, -1, 1);        //gamepad controllers have a value of 1 when you push it to its maximum foward
        rightpower = Range.clip(rightpower, -1, 1);      //range of power, min first then max
        motor1.setPower(rightpower);                    //connects the value for power to the actual power of the motors
        motor2.setPower(leftpower);
        motor3.setPower(leftpower);
        motor4.setPower(rightpower);

        telemetry.addData("LeftMotors", "Left Motor Power:" + leftpower);           //shows the data or text stated onto phone telemetry
        telemetry.addData("RightMotors", "Right Motor Power:" + rightpower);
    }

}
