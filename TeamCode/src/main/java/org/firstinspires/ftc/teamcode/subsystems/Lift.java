package org.firstinspires.ftc.teamcode.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//asynchronous
@Config
public class Lift
{
    public static final int HIGH_POSITION = 2300;
    public static final int MEDIUM_POSITION = 1700;
    public static final int LOW_POSITION = 960;
    public static final int INTAKE_POSITION = 0;

    public static final int STACK_START_POSITION = 100;
    public static final int STACK_POSITION_DECREMENT = 20;

    public static int target_position;

    //private static final int LIFT_FREE_SPEED = 1;

    private static final int NUM_CONES = 2;

    public static double kP = 0.025;

    public static double TOLERANCE = 100;

    private static Motor lift_top;
    private static Motor lift_bottom;
    private static MotorGroup lift;

    public static void init(HardwareMap hardwareMap)
    {
        lift_top = new Motor(hardwareMap, "liftMotorTop");
        lift_bottom =  new Motor(hardwareMap, "liftMotorBottom");
        lift = new MotorGroup(lift_top, lift_bottom);

        lift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift.setRunMode(Motor.RunMode.PositionControl);
        lift.setPositionCoefficient(kP);
        lift.setTargetPosition(INTAKE_POSITION);
        lift.resetEncoder(); // IMPORTANT
        target_position = INTAKE_POSITION;
        lift.setPositionTolerance(TOLERANCE);
    }

    /*static void goUp()
    {
        lift_bottom.setPower();
    }*/
    public static int getCurrentPositionBottom() {
        return lift_bottom.getCurrentPosition();
    }
    public static int getCurrentPositionTop() {
        return lift_top.getCurrentPosition();
    }
    public static int getTargetPosition() {
        return target_position;
    }
    public static boolean atTargetPosition() {return lift.atTargetPosition();}
    public static void setTargetPosition(int set_point) {
        lift.setTargetPosition(set_point);
        target_position=set_point;
    }
    public static void update()
    {
        lift.set(1);
    }
}
