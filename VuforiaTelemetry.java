package org.firstinspires.ftc.teamcode;

/**
 * Created by Alex on 2/14/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Vuforia Telemetry", group = "Vuforia")
public class VuforiaTelemetry extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters((R.id.cameraMonitorViewId));
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfL9DST/////AAAAGcC5dR2bzE/YhAg3ZKntUZN6JDz2Eg0EbS08RJF9d+TNT/sGuL+ZdPv+y/iAkGeuUOyTRhE3X8cd/Z1mvp4RqiFyxNF4uE/zShcGZuMqXjiIsWAu/q76vLt4Qq/7V765NUtW+wb1dD6S8uSiSoDDU/wmhP5AiUw6/tofUpBuqgRr1WfuKTBO/SClC8ed2sWm8wO1ePhuGEG/roGN/CGZFlBqvdafPnKvV2iSeGR5uYfn1Y0NQ+J/fV152LMlF1LIYT8E3mIliK93cC37GrgyHx+BmGIWLOKdW78yJG8OWHgkBqFeKsVLl/Ki0jczkd60O0gl8vZjgW4bSZexrTzErsI5cMcSc6MiWwrCsXwdV+oY";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint((HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS), 4);

        /** Importing all of the vision targets **/
        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();

        while ((opModeIsActive())) {
            for(VuforiaTrackable beac : beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose != null) {
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + "-Translation", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(0), translation.get(2)));
                        /* Translation.get() should have an index of 0 if the phone is landscape and
                           one of 1 if it is portrait
                         */

                    telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);
                }
            }
            telemetry.update();
        }
    }
}
