package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State.GOING_INTAKE;
import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State.INTAKE;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Turntable;

@TeleOp
public class Test extends OpMode {
    static final private int CLAW_UP_TIME = 1000;
    boolean changed_trigger = true;
    int direction=0;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public enum State {
        INTAKE,
        GOING_OUT,
        GOING_INTAKE
    }

    //Dc turntable_motor;
    TheBestTeleopKnownToMankind.State state = TheBestTeleopKnownToMankind.State.INTAKE;
    public void init()
    {
        Robot.initAll(hardwareMap);
        Turntable.setSpeed(0.1);
        //turntable_motor = hardwareMap.dcMotor.get("turntableMotor");
    }
    public void loop()
    {
        if(gamepad1.right_bumper) Drivetrain.setSpeed(0.4);
        else Drivetrain.setSpeed(1);

        if(gamepad1.left_stick_button)Claw.setPivotUp();
        if(gamepad1.right_stick_button)Claw.setPivotUp();
        if(gamepad1.dpad_right) Turntable.setTargetPosition(Turntable.getTargetPosition()+1);
        if(gamepad1.dpad_left) Turntable.setTargetPosition(Turntable.getTargetPosition()-1);
        //if(gamepad1.dpad_down && Lift.getTargetPosition()-6>=0) Lift.setSetPoint(Lift.getTargetPosition()-6);

        //if(gamepad1.a) Lift.setTargetPosition(Lift.INTAKE_POSITION);
        if(gamepad1.y) Lift.setTargetPosition(Lift.HIGH_POSITION);
        if(gamepad1.x) Lift.setTargetPosition(Lift.MEDIUM_POSITION);
        if(gamepad1.b) Lift.setTargetPosition(Lift.LOW_POSITION);

        if(gamepad1.dpad_up) Lift.setTargetPosition(Lift.getTargetPosition()+5);
        if(gamepad1.dpad_down) Lift.setTargetPosition(Lift.getTargetPosition()-5);


        //if(gamepad1.dpad_right) {targetPosition+=0.10; sleep(100);};
        //if(gamepad1.dpad_left) {targetPosition-=0.10; sleep(100);};

        //telemetry.addData("set:", a);
        // toggle for the trigger

        //lift_top.setPower(1);
        Drivetrain.update(gamepad2.left_stick_y, gamepad2.left_stick_x, gamepad2.right_stick_x);

        telemetry.addData("lift_current:", Lift.getCurrentPositionTop());
        telemetry.addData("lift_target:", Lift.getTargetPosition());
        telemetry.addData("tunrtable current:", Turntable.getCurrentPosition());
        telemetry.addData("tunrtable target:", Turntable.getTargetPosition());
        //telemetry.addData("lift_top_current:", Lift.getCurrentPositionTop());
        //telemetry.addData("claw_current:", claw.getPosition());
        //telemetry.addData("top_motor_power:", lift_top.getPower());
        //telemetry.addData("bottom_motor_power:", lift_bottom.getPower());
        // Turntable.update();
        telemetry.update();

    }

}