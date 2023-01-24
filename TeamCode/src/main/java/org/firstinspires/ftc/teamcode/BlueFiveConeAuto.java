package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.General.CLAW_CLOSED;
import static org.firstinspires.ftc.teamcode.General.CLAW_OPEN;
import static org.firstinspires.ftc.teamcode.General.HIGH_POSITION;
import static org.firstinspires.ftc.teamcode.General.INTAKE_POSITION;
import static org.firstinspires.ftc.teamcode.General.NUM_CONES;
import static org.firstinspires.ftc.teamcode.General.STACK_DECREMENT;
import static org.firstinspires.ftc.teamcode.General.STACK_START_POSITION;
import static java.lang.Math.abs;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class BlueFiveConeAuto extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor lift = hardwareMap.dcMotor.get("liftMotor");

        lift.setTargetPosition(STACK_START_POSITION); // go to stack level
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Servo claw = hardwareMap.get(Servo.class, "clawMotor");

        claw.setPosition(CLAW_OPEN);



        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(36, abs(-55), Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        Trajectory upto =
                drive.trajectoryBuilder(startPose)
                        .lineToLinearHeading(new Pose2d(36, abs(-10.0), 0))
                        .build();
        drive.followTrajectory(upto);

        Trajectory to_cone =
                drive.trajectoryBuilder(upto.end())
                        .lineToLinearHeading(new Pose2d(60, abs(-10.0), 0))
                        .build();
        drive.followTrajectory(to_cone);

/////
            claw.setPosition(CLAW_CLOSED);
            while(lift.getCurrentPosition()!=HIGH_POSITION) // go up
            {
                lift.setTargetPosition(HIGH_POSITION);
            }
/////



        Trajectory to_pole =
                drive.trajectoryBuilder(to_cone.end())
                        .lineToLinearHeading(new Pose2d(23.5, abs(-8.0+2.5), Math.toRadians(270)))
                        .build();
        drive.followTrajectory(to_pole);

        claw.setPosition(CLAW_OPEN);
        while(lift.getCurrentPosition()!=STACK_START_POSITION)
        {
            lift.setTargetPosition(STACK_START_POSITION);
        }
        STACK_START_POSITION -= STACK_DECREMENT;

        for(int i = 0; i < NUM_CONES; i ++)
        {
            Trajectory to_cone_cycle =
                    drive.trajectoryBuilder(to_pole.end())
                            .lineToLinearHeading(new Pose2d(60, abs(-12.5+2.5), 0))
                            .build();
            drive.followTrajectory(to_cone_cycle);

/////
            claw.setPosition(CLAW_CLOSED);
            while(lift.getCurrentPosition()!=HIGH_POSITION)
            {
                lift.setTargetPosition(HIGH_POSITION);
            }
/////



            Trajectory to_pole_cycle =
                    drive.trajectoryBuilder(to_cone_cycle.end())
                            .lineToLinearHeading(new Pose2d(23.5, abs(-8.0+2.5), Math.toRadians(270)))
                            .build();
            drive.followTrajectory(to_pole_cycle);

            claw.setPosition(CLAW_OPEN);
            while(lift.getCurrentPosition()!=STACK_START_POSITION)
            {
                lift.setTargetPosition(STACK_START_POSITION);
            }
            STACK_START_POSITION -= STACK_DECREMENT;
        }

        Trajectory to_middle =
                drive.trajectoryBuilder(to_pole.end())
                        .lineToLinearHeading(upto.end())
                        .build();
        drive.followTrajectory(to_middle);

        Trajectory back =
                drive.trajectoryBuilder(to_middle.end())
                        .lineToLinearHeading(startPose)
                        .build();
        drive.followTrajectory(back);

        Trajectory terminal =
                drive.trajectoryBuilder(back.end())
                        .lineToLinearHeading(new Pose2d(0, startPose.getY(), Math.toRadians(270)))
                        .build();
        drive.followTrajectory(terminal);
    }
}
