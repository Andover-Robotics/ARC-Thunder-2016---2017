package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by citruseel on 10/14/2016.
 */

@TeleOp(name="Config Special TeleOp", group="TeleOp")

public class ThunderBasicTeleOp2016_2017EditedConfigEdition extends OpMode {

    private DcMotorController motorControllerP0;    // Motor Controller in port 0 of Core
    private DcMotorController motorControllerP1;    // Motor Controller in port 1 of Core
    private DcMotorController motorControllerP4;    // Motor Controller in port 4 of Core

    private DcMotor motor1;                         // Motor 1: port 1 in Motor Controller 1
    private DcMotor motor2;                         // Motor 2: port 2 in Motor Controller 1
    private DcMotor motor3;                         // Motor 3: port 1 in Motor Controller 0
    private DcMotor motor4;                         // Motor 4: port 2 in Motor Controller 0
    private DcMotor sweeperMotor;                   // Sweeper motor: port 1 in Motor Controller 4
    private DcMotor launcherMotor;                  // Launcher motor: port 2 in Motor Controller 4

    private ServoController servoController;

    private Servo colorSensorServo;

    private double servoposition = 0;//start position


    @Override
    public void init() {
        /* Initializing and mapping electronics*/
        motorControllerP0 = hardwareMap.dcMotorController.get("MCP0");
        motorControllerP1 = hardwareMap.dcMotorController.get("MCP1");
        motorControllerP4 = hardwareMap.dcMotorController.get("MCP4");


        motor1 = hardwareMap.dcMotor.get("motor1");        //MCP4
        motor2 = hardwareMap.dcMotor.get("motor2");        //MCP4
        motor3 = hardwareMap.dcMotor.get("motor3");         //MCP1          Back of motor 1
        motor4 = hardwareMap.dcMotor.get("motor4");         //MCP1          Back of motor 2

        launcherMotor = hardwareMap.dcMotor.get("motorLauncher"); //hardwaremapping the motor for the launcher MCP4 motor 1
        sweeperMotor = hardwareMap.dcMotor.get("motorSweeper"); //hardwaremapping the motor for the sweeper MCP4 motor 2

        /*Setting channel modes*/
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sweeperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor4.setDirection(DcMotorSimple.Direction.REVERSE);
        launcherMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        servoController = hardwareMap.servoController.get("SCP2"); //hardwaremapping the servo controller

        colorSensorServo = hardwareMap.servo.get("servo"); //hardwaremapping the servo



    }

    @Override
    public void loop() {                                          //constant loop that rechecks about every 20ms
        double GearRatio = 0.25;                                  //We geared up from 80 teeth to 40 teeth: this is needed so that we don't go too fast)

        double leftpower = gamepad1.left_stick_y * GearRatio;     //set's a value for power equal to the value of the joysticks for the left stick of the 1st controller
        double rightpower = gamepad1.right_stick_y * GearRatio;   //set's a value for power equal to the value of the joysticks for the right stick of the 2nd controller

        // value for the triggers is either 0.0 or 1.0
        double sweeperPower = gamepad2.left_stick_y; //sets the sweeper power equal to the value of the joysticks for the left stick of the 2nd controller
        double launcherPower = gamepad2.right_trigger; //sets the launcher power equal to the state of the right trigger on the second controller

        leftpower = Range.clip(leftpower, -1, 1);        //gamepad controllers have a value of 1 when you push it to its maximum foward
        rightpower = Range.clip(rightpower, -1, 1);      //range of power, min first then max

        sweeperPower = Range.clip(sweeperPower, -1, 1);//range of sweeper motor values is between 0 and 1
        launcherPower = Range.clip(launcherPower, -1, 1);//range of launcher motor values is between 0 and 1

        motor1.setPower(rightpower);                    //connects the value for power to the actual power of the motors
        motor2.setPower(leftpower);
        motor3.setPower(leftpower);
        motor4.setPower(rightpower);

        sweeperMotor.setPower(sweeperPower);
        launcherMotor.setPower(launcherPower);


        servoposition=Range.clip(servoposition, 0, 1);//range of servo values is between 0 and 1

        if(gamepad2.x == true && gamepad2.b == false) {
            servoposition = 0.05;
        }
        if (gamepad2.x == false) {
            if (gamepad2.b == true) {
                servoposition = 0.95;
            }
            if (gamepad2.b == false) {
                servoposition = 0.5;
            }
        }

        colorSensorServo.setPosition(servoposition); //constantly updates the servo's position every 20ms

        telemetry.addData("LeftMotors", "Left Motor Power:" + leftpower);           //shows the data or text stated onto phone telemetry
        telemetry.addData("RightMotors", "Right Motor Power:" + rightpower);
        telemetry.addData("SweeperLauncherTest", "Sweeper Power: " + sweeperPower);
        telemetry.addData("SweeperLauncherTest", "Launcher Power: " + launcherPower);
        telemetry.addData("ServoTest", "Servo Postition: " + servoposition);
    }

}
