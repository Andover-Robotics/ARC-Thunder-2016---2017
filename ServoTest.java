package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by citruseel on 11/2/2016.
 */

@TeleOp(name="ServoTest", group="TeleOp")

public class ServoTest extends OpMode {

    private Servo testing;
    private double servoposition = 0;//start position

    @Override
    public void init() {

        testing = hardwareMap.servo.get("servo"); //hardwaremapping the servo
    }

    public void loop(){ //50 loops = 1 second

        servoposition=Range.clip(servoposition, -1, 1);//range of servo values is between 0 and 1

        if(gamepad2.left_stick_x > 0) {
           servoposition = servoposition + 0.008; //1 second adds 2/5 to the servo position...... 2.5 seconds to do a full movement from left max to right max
        }
        if(gamepad2.left_stick_x < 0) {
            servoposition = servoposition - 0.008;
        }

        testing.setPosition(servoposition); //constantly updates the servo's position every 20ms

        telemetry.addData("ServoTest", "Servo Postition " + servoposition); //shows data after comma in phone

    }

}
