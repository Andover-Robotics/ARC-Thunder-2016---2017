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

@TeleOp(name="Thunder w2016-2017 TeleOp", group="TeleOp")
public class ThunderBasicTeleOp2016_2017EditedConfigEdition extends OpMode {

    private DcMotorController motorControllerL;    // left motor controllers
    private DcMotorController motorControllerR;    // right motor controllers
    private DcMotorController motorControllerA1;   // Scoring motor controller
    private DcMotorController motorControllerA2;   // Scoring motor controller
    private ServoController servoController;

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    private DcMotor sweeperMotor;
    private DcMotor launcherMotor;
    private DcMotor motor5;

    private Servo servo;

    private double servoposition = 0;


    @Override
    public void init() {
        /* Initializing and mapping electronics*/
        motorControllerL = hardwareMap.dcMotorController.get("MC_L");
        motorControllerR = hardwareMap.dcMotorController.get("MC_R");
        motorControllerA1 = hardwareMap.dcMotorController.get("MC_A1");
        motorControllerA2 = hardwareMap.dcMotorController.get("MC_A2");
        servoController = hardwareMap.servoController.get("SC");


        motor1 = hardwareMap.dcMotor.get("motorFrontL");        //P0
        motor2 = hardwareMap.dcMotor.get("motorFrontR");        //P1
        motor3 = hardwareMap.dcMotor.get("motorBackL");         //P0
        motor4 = hardwareMap.dcMotor.get("motorBackR");         //P1

        servo = hardwareMap.servo.get("servo");

        launcherMotor = hardwareMap.dcMotor.get("motorLauncher"); //P0
        sweeperMotor = hardwareMap.dcMotor.get("motorSweeper"); //P1

        motor5 = hardwareMap.dcMotor.get("motorStrafe");//P0 A2

        /*Setting channel modes*/
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sweeperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor5.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor4.setDirection(DcMotorSimple.Direction.REVERSE);




    }

    @Override
    public void loop() {                                          //constant loop that rechecks about every 20ms

        double acceleration = gamepad1.right_trigger > 0 ? 0.07: gamepad1.right_bumper ? 0.7 : 0.25;    //We geared up from 80 teeth to 40 teeth: this is needed so that we don't go too fast)

        double leftpower = gamepad1.left_stick_y * acceleration;     //set's a value for power equal to the value of the joysticks for the left stick of the 1st controller
        double rightpower = gamepad1.right_stick_y * acceleration;   //set's a value for power equal to the value of the joysticks for the right stick of the 2nd controller

        // value for the triggers is either 0.0 or 1.0
        double sweeperPower = gamepad1.left_bumper ? 1: gamepad1.left_trigger > 0 ? -1: gamepad2.left_stick_y; //sets the sweeper power equal to the value of the joysticks for the left stick of the 2nd controller
        double launcherPower = gamepad2.y ? 1 : gamepad2.a ? -1 : 0; //sets the power to the boolean next to it
        // "?" = if "thing before ?" then "thing after ?", else "after colon"

        double strafePower = gamepad1.x ? 1 : gamepad1.b ? -1 : 0;

        servoposition = gamepad2.right_trigger > 0 ? 1: 0;

        leftpower = Range.clip(leftpower, -1, 1);        //gamepad controllers have a value of 1 when you push it to its maximum foward
        rightpower = Range.clip(rightpower, -1, 1);      //range of power, min first then max

        sweeperPower = Range.clip(sweeperPower, -1, 1);//range of sweeper motor values is between 0 and 1
        launcherPower = Range.clip(launcherPower, -1, 1);//range of launcher motor values is between 0 and 1

        strafePower = Range.clip(strafePower, -1, 1);//range of launcher motor values is between 0 and 1

        servoposition = Range.clip(servoposition, 0, 1);

        motor1.setPower(leftpower);                    //connects the value for power to the actual power of the motors
        motor2.setPower(rightpower);
        motor3.setPower(leftpower);
        motor4.setPower(rightpower);

        sweeperMotor.setPower(sweeperPower);
        launcherMotor.setPower(launcherPower);

        motor5.setPower(strafePower);

        servo.setPosition(servoposition);


        telemetry.addData("Acceleration:", acceleration);
        telemetry.addData("Left Motor Power: ", leftpower);           //shows the data or text stated onto phone telemetry
        telemetry.addData("Right Motor Power:", rightpower);
        telemetry.addData("Sweeper Power: ", sweeperPower);
        telemetry.addData("Launcher Power: ", launcherPower);
        telemetry.addData("Strafe Power: ", strafePower);
        telemetry.addData("Servo Position: ", servoposition);
    }

}
