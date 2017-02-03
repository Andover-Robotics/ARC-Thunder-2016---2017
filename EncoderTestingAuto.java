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
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    /** For Encoders and specific turn values**/
    int ticksPerRev = 1120;             // This is the specific value for AndyMark motors
    int ticksPer360Turn = 4600;         // The amount of ticks for a 360 degree turn
    int tickRatio = ticksPer360Turn / ticksPerRev;

    double wheelDiameter = 4.0;         // Diameter of the current omniwheels in inches
    double ticksPerInch = (ticksPerRev / (wheelDiameter * 3.14159265));




    public void runOpMode() throws  InterruptedException{
        /** This is the function that executes the code and what the robot should do **/
        // Initializes the electronics
        initElectronics(0);

        telemetry.addData("Phase 1", "Init");
        telemetry.update();

        waitForStart();

        telemetry.addData("Started Robot", "Now");
        telemetry.update();

        runToPositionEncoders();

        rotateDegreesLeft(0.4, 360);
        telemetry.addData("Execute", "360 Left");
        telemetry.update();

        rotateDegreesRight(0.4, 360);
        telemetry.addData("Execute", "360 Right");
        telemetry.update();

        rotateDegreesLeft(0.4, 30);
        telemetry.addData("Execute", "30 Left");
        telemetry.update();

        rotateDegreesRight(0.4, 30);
        telemetry.addData("Execute", "30 Right");
        telemetry.update();

        encoderMove(0.25, 6, 6, 2);
        telemetry.addData("Execute", "move 6 in");
        telemetry.update();


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
            resetEncoders();
            runUsingEncoders();

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

    public void encoderMove(double power,
                            double leftInches, double rightInches,
                            long waitTime) throws InterruptedException {
        /** This program makes the motors move a certain distance **/
        resetEncoders();
        runToPositionEncoders();

        // Sets the power range
        power = Range.clip(power, -1, 1);

        // Setting the target positions
        motorFrontL.setTargetPosition((int)(leftInches * ticksPerInch));
        motorFrontR.setTargetPosition((int)(rightInches * ticksPerInch));
        motorBackL.setTargetPosition((int)(leftInches * ticksPerInch));
        motorBackR.setTargetPosition((int)(rightInches * ticksPerInch));

        // Sets the motors' position
        motorFrontL.setPower(power);
        motorFrontR.setPower(power);
        motorBackL.setPower(power);
        motorBackR.setPower(power);

        while(motorFrontL.isBusy() && motorFrontR.isBusy() &&
                motorBackL.isBusy() && motorBackR.isBusy() && opModeIsActive()){

            // Adds telemetry of the drive motors
            telemetry.addData("MotorFrontL", motorFrontL.getCurrentPosition());
            telemetry.addData("MotorFrontR", motorFrontR.getCurrentPosition());
            telemetry.addData("MotorBackL", motorBackL.getCurrentPosition());
            telemetry.addData("MotorBackR", motorBackL.getCurrentPosition());

            // Updates the telemetry
            telemetry.update();
        }

        // Stops the motors
        stopMotion();

        runUsingEncoders();

    }

    public void turnLeft(double leftRotation, long time) throws InterruptedException {

    }

    public void rotateDegreesLeft(double power, int robotDegrees) throws  InterruptedException {
        /** Robot requires values of...
         *  360 degrees =~ 4600
         *  180 degrees =~ 2300   **/
        resetEncoders();
        runToPositionEncoders();

        // Sets the power range
        power = Range.clip(power, -1, 1);

        // Setting the target positions
        motorFrontL.setTargetPosition(robotDegrees * tickRatio);
        motorFrontR.setTargetPosition(robotDegrees * -tickRatio);
        motorBackL.setTargetPosition(robotDegrees * tickRatio);
        motorBackR.setTargetPosition(robotDegrees * -tickRatio);

        // Sets the motors' positions
        motorFrontL.setPower(power);
        motorFrontR.setPower(power);
        motorBackL.setPower(power);
        motorBackR.setPower(power);

        while(motorFrontL.isBusy() && motorFrontR.isBusy() &&
                motorBackL.isBusy() && motorBackR.isBusy() && opModeIsActive()){

            // Adds telemetry of the drive motors
            telemetry.addData("MotorFrontL", motorFrontL.getCurrentPosition());
            telemetry.addData("MotorFrontR", motorFrontR.getCurrentPosition());
            telemetry.addData("MotorBackL", motorBackL.getCurrentPosition());
            telemetry.addData("MotorBackR", motorBackL.getCurrentPosition());

            // Updates the telemetry
            telemetry.update();
        }

        // Stops the motors
        stopMotion();

        runUsingEncoders();
    }
    public void rotateDegreesRight(double power, int robotDegrees) throws  InterruptedException {
        /** Robot requires values of...
         *  360 degrees =~ 4600
         *  180 degrees =~ 2300   **/
        resetEncoders();
        runToPositionEncoders();

        // Sets the power range
        power = Range.clip(power, -1, 1);

        // Setting the target positions
        motorFrontL.setTargetPosition(robotDegrees * -tickRatio);
        motorFrontR.setTargetPosition(robotDegrees * tickRatio);
        motorBackL.setTargetPosition(robotDegrees * -tickRatio);
        motorBackR.setTargetPosition(robotDegrees * tickRatio);

        // Sets the motors' powers
        motorFrontL.setPower(power);
        motorFrontR.setPower(power);
        motorBackL.setPower(power);
        motorBackR.setPower(power);

        while(motorFrontL.isBusy() && motorFrontR.isBusy() &&
                motorBackL.isBusy() && motorBackR.isBusy() && opModeIsActive()){

            // Adds telemetry of the drive motors
            telemetry.addData("MotorFrontL", motorFrontL.getCurrentPosition());
            telemetry.addData("MotorFrontR", motorFrontR.getCurrentPosition());
            telemetry.addData("MotorBackL", motorBackL.getCurrentPosition());
            telemetry.addData("MotorBackR", motorBackL.getCurrentPosition());

            // Updates the telemetry
            telemetry.update();
        }

        // Stops the motors
        stopMotion();

        runUsingEncoders();
    }

    public void resetEncoders() {
        /** Resets the encoder values on each of the motors **/
        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //motorFrontR.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }
    public void runToPositionEncoders() {
        /** Toggles the encoders to run to position mode **/
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void runUsingEncoders() {
        /** Sets the encoders to RUN_USING_POSITION **/
        motorFrontL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void addTelemetryData(String string1, String string2) {
        telemetry.addData(string1, string2);
        telemetry.update();
    }
    public void stopMotion() {
        /** Stops all motor motion**/
        motorFrontL.setPower(0);
        motorFrontR.setPower(0);
        motorBackL.setPower(0);
        motorBackR.setPower(0);
    }
}
