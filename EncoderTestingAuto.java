package org.firstinspires.ftc.teamcode;

/**
 * Created by Alex on 2/2/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@Autonomous(name="Encoder Testing Autonomous", group="Auto")
public class EncoderTestingAuto extends LinearOpMode {



    /** Declaring the motor variables **/
    private DcMotorController motorControllerL;         // Left motor controllers
    private DcMotorController motorControllerR;         // Right motor controllers
    private DcMotorController motorControllerA1;        // Auxiliary motor controller 1
    private DcMotorController motorControllerA2;        // Auxiliary motor controller 2
    private ServoController servoController;            // Servo Controller

    private DcMotor motorFrontL;                        // Front Left Motor
    private DcMotor motorFrontR;                        // Front Right Motor
    private DcMotor motorBackL;                         // Back Left Motor
    private DcMotor motorBackR;                         // Back Right Motor
    private DcMotor sweeperMotor;                       // Sweeper Motor
    private DcMotor motorLauncher;                      // Launcher Arm Motor
    private DcMotor motorStrafe;                        // Sideways Strafe Motor

    private Servo servo;                                // Gate Motor on the Robot



    public void runOpMode() throws InterruptedException {
        // Initializes the electronics
        initElectronics(0);

        waitForStart();

        rotateDegreesLeft(360);
        rotateDegreesRight(360);
        rotateDegreesLeft(30);


    }

    public void initElectronics(int mode) throws InterruptedException {
        // To make the initialization of electronics much easier and nicer to read
        /** Initializing and mapping electronics **/
        if (mode == 0) {
            motorControllerL = hardwareMap.dcMotorController.get("MC_L");
            motorControllerR = hardwareMap.dcMotorController.get("MC_R");
            motorControllerA1 = hardwareMap.dcMotorController.get("MC_A1");
            motorControllerA2 = hardwareMap.dcMotorController.get("MC_A2");
            servoController = hardwareMap.servoController.get("SC");

            motorFrontL = hardwareMap.dcMotor.get("motorFrontL");        //P0 is actually the right
            motorFrontR = hardwareMap.dcMotor.get("motorFrontR");        //P1 is actually the left
            motorBackL = hardwareMap.dcMotor.get("motorBackL");          //P0
            motorBackR = hardwareMap.dcMotor.get("motorBackR");          //P1

            servo = hardwareMap.servo.get("servo");

            motorLauncher = hardwareMap.dcMotor.get("motorLauncher");   //P0
            sweeperMotor = hardwareMap.dcMotor.get("motorSweeper");     //P1

            motorStrafe = hardwareMap.dcMotor.get("motorStrafe");       //P0 A2

            /*Setting channel modes*/
            motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motorLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sweeperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            motorStrafe.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            motorFrontL.setDirection(DcMotorSimple.Direction.REVERSE);
            motorBackL.setDirection(DcMotorSimple.Direction.REVERSE);
            motorLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else if (mode == 1) {

        }

    }

    public void move(double power, long time) throws InterruptedException {
        motorFrontL.setPower(power);
        motorFrontR.setPower(power);
        motorBackL.setPower(power);
        motorBackR.setPower(power);

        Thread.sleep(time);
    }

    public void turnLeft(double leftRotation, long time) throws InterruptedException {

    }

    public void rotateDegreesLeft(int robotDegrees) throws  InterruptedException {
        /** Robot requires values of...
         *  360 degrees =~ 4600
         *  180 degrees =~ 2300   **/
        resetEncoders();

        motorFrontL.setTargetPosition(robotDegrees * (115 / 9));
        motorFrontR.setTargetPosition(robotDegrees * -(115 / 9));
        motorBackL.setTargetPosition(robotDegrees * (115 / 9));
        motorBackR.setTargetPosition(robotDegrees * -(115 / 9));
    }
    public void rotateDegreesRight(int robotDegrees) throws  InterruptedException {
        /** Robot requires values of...
         *  360 degrees =~ 4600
         *  180 degrees =~ 2300   **/
        resetEncoders();

        motorFrontL.setTargetPosition(robotDegrees * -(115 / 9));
        motorFrontR.setTargetPosition(robotDegrees * (115 / 9));
        motorBackL.setTargetPosition(robotDegrees * -(115 / 9));
        motorBackR.setTargetPosition(robotDegrees * (115 / 9));
    }
    public void resetEncoders() {
        /** Resets the encoder values on each of the motors **/
        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
