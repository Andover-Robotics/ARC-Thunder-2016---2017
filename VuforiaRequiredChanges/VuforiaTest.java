package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FTCVuforia;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.util.HashMap;

/**
 * Created by citruseel on 12/7/2016.
 */


/*  <uses-permission android:name="android.permission.CAMERA"
android:required = "true" /> in the AndroidManifest.xml to activate camera */

public class VuforiaTest extends OpMode {

    FTCVuforia vuforia; //required to do some stuff in FtcRobotControllerActivity and need to import FTCVuforia.jar

    public void init(){
        vuforia = FtcRobotControllerActivity.getVuforia();
        vuforia.addTrackables("exactNameOfTrackable");
        vuforia.initVuforia();

    }

    public void loop(){

        HashMap<String, double[]> data = vuforia.getVuforiaData();

        if (data.containsKey("exactNameOfTrackable")){
            telemetry.addData("Name of trackable: ", data.get("exactNameOfTrackable")[0]); // tells the rotation off of the x-axis in the camera
        }
    }

    //what happens when you hit the stop button
    public void stop(){
        super.stop();
        try {
            vuforia.destroy();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
