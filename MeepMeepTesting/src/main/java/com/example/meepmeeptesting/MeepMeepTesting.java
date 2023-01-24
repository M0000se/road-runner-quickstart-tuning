package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(35, 35, Math.toRadians(150), Math.toRadians(150), 15)
                .setDimensions(2.82*2+9, 13.877)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, -55, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(36, -12.5, 0))
                                //first trajectory
                                .lineToLinearHeading(new Pose2d(60, -12.5, 0))
                                // second trajectory

                                //action
                                    //close claw
                                    //(while) lift up

                                .lineToLinearHeading(new Pose2d(23.5, -8.0, Math.toRadians(90)))

                                //action
                                    //open claw
                                    //(while) lift down

                                .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}