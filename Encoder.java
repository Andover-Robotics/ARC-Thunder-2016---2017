package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 *Created by Joshua Krinsky on 11/18/2016.
 */

@TeleOp(name="SweeperLauncherTest", group="TeleOp")

public class SweeperLauncherTest extends OpMode {

    private DcMotorController motorControllerP4;
    private DcMotor launcherMotor;
    
        @Override
    public void init() {
        motorControllerP4 = hardwareMap.dcMotorController.get("MCP4"); //hardwaremapping the motor controller
        launcherMotor = hardwareMap.dcMotor.get("motorLauncher"); //hardwaremapping the motor for the launcher


        launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sweeperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

