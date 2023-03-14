package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AutoGeneral.park;
import static java.lang.Math.abs;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@Autonomous
public class BlueFiveConeAuto extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        //Lift.setTargetPosition(Lift.STACK_START_POSITION); // go to stack level
        Extendo.init(hardwareMap);
        Drivetrain.init(hardwareMap);
        Claw.init(hardwareMap);
        PhotonCore.enable();
        Lift.init(hardwareMap);

        waitForStart();

        Claw.setPosition(Claw.CLAW_CLOSED);
        Lift.setTargetPosition(Lift.LOW_POSITION);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = AutoGeneral.startPose;

        drive.setPoseEstimate(startPose);

        while(time.time()<10000) {}

        Trajectory upto =
                drive.trajectoryBuilder(startPose)
                        .lineToLinearHeading(park)
                        .build();
        drive.followTrajectory(upto);
        Lift.setTargetPosition(Lift.INTAKE_POSITION);
    }
}
