package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 *Created by Joshua Krinsky a modification of Lake Yin's code on 11/18/2016.
 */

@TeleOp(name="Encoder", group="TeleOp")

public class SweeperLauncherTest extends OpMode {

    private DcMotor launcherMotor;
    
    @override
    public void init ()
    {
     launcherMotor = hardwareMap.dcMotor.get("motorLauncher"); //hardwaremapping the motor for the launcher
     launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        puvlic void LauncherEncoder()
     double launcherPower = gamepad2.A; //sets the launcher power equal to press A button on the second controller
        
    launcherPower = Range.clip(launcherPower, -1, 1);//range of launcher motor values is between 0 and 1
            }
     
        if(gamepad2.A==true){
        LauncherMotor.setPower(1);
            
       }
    else{
    LauncherMotor.setPower(0);
    }
    while (initIsActive())
    {
    telemetry.addData("text","run at speed");
    telemerty.addData("power",LauncherMotor.getPower());
    telemerty.addData("position". LauncherMotor.getCurrentPosition());
    }
        
 
}

