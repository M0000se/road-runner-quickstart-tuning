package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State;
import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State.*;

import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Turntable;

import java.lang.reflect.GenericArrayType;

@TeleOp
public class TheBestTeleopKnownToMankind extends OpMode
{
    static final private int CLAW_UP_TIME = 1000;
    boolean changed_trigger = false;
    int direction=0;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public enum State {
        INTAKE,
        GOING_OUT,
        GOING_INTAKE
    }
    State state = State.INTAKE;
    public void init()
    {
        Robot.initAll(hardwareMap);
        Turntable.setSpeed(0.7);
    }
    public void loop()
    {
        if(gamepad1.right_trigger>0.5) Drivetrain.setSpeed(0.4);
        else Drivetrain.setSpeed(1);
        switch(state) {
            case INTAKE:
            {
                if (gamepad1.dpad_right) direction = 1;
                if (gamepad1.dpad_left) direction = 2;
                if (gamepad1.dpad_up) direction = 3;

                if (gamepad1.dpad_right || gamepad1.dpad_left || gamepad1.dpad_up)
                {
                    state = State.GOING_OUT;
                    time.reset();
                }

                if(gamepad1.left_trigger >= 0.5) {
                    Extendo.setPosition(Extendo.FULL_EXTEND_POSITION);
                }
                else Extendo.setPosition(Extendo.INTAKE_POSITION);

                if(gamepad1.right_trigger >= 0.5) {
                    Claw.setPosition(Claw.CLAW_OPEN);
                }
                else Claw.setPosition(Claw.CLAW_CLOSED);
            } break;
            case GOING_OUT: // go out and choose position and then release and go to intake state
            {
                boolean changed_trigger = false;
                Claw.setPivotUp();
                if(time.time()>CLAW_UP_TIME) {
                    telemetry.addData("COOL: ", direction);
                    if (direction == 1) Turntable.setTargetPosition(Turntable.RIGHT_POSITION);
                    if (direction == 2) Turntable.setTargetPosition(Turntable.LEFT_POSITION);
                    if (direction == 3) Turntable.setTargetPosition(Turntable.FRONT_POSITION);
                    Extendo.setPosition(Extendo.JUNCTION_EXTEND_POSITION);

                    if(gamepad1.right_trigger >= 0.5) {
                        Claw.setPosition(Claw.CLAW_OPEN);
                        changed_trigger = true;
                    }
                    if(changed_trigger && (gamepad1.right_trigger>=0.5)) {
                        state = GOING_INTAKE;
                    }
                }
            } break;
            case GOING_INTAKE:
            {
                Turntable.setTargetPosition(Turntable.INTAKE_POSITION);
                Extendo.setPosition(Extendo.INTAKE_POSITION);
                Lift.setTargetPosition(Lift.INTAKE_POSITION);
                Claw.setPosition(Claw.CLAW_FIT);
                if(Turntable.atTargetPosition()) {
                    Claw.setPivotDown();
                    state = INTAKE;
                }
            } break;
        }

        //if(gamepad1.dpad_right) Lift.setSetPoint(Lift.getTargetPosition()+6);
        //if(gamepad1.dpad_down && Lift.getTargetPosition()-6>=0) Lift.setSetPoint(Lift.getTargetPosition()-6);

        //if(gamepad1.a) Lift.setTargetPosition(Lift.INTAKE_POSITION);
        if(gamepad1.y) Lift.setTargetPosition(Lift.HIGH_POSITION);
        if(gamepad1.x) Lift.setTargetPosition(Lift.MEDIUM_POSITION);
        if(gamepad1.b) Lift.setTargetPosition(Lift.LOW_POSITION);

        //if(gamepad1.dpad_up) Lift.setTargetPosition(Lift.getTargetPosition()+10);
        //if(gamepad1.dpad_down) Lift.setTargetPosition(Lift.getTargetPosition()-10);

        if(gamepad1.left_stick_x>0.1)    Turntable.setTargetPosition(Turntable.getTargetPosition()+2);
        if(gamepad1.left_stick_x<-0.1)   Turntable.setTargetPosition(Turntable.getTargetPosition()-2);

        //telemetry.addData("set:", a);
        // toggle for the trigger

            //lift_top.setPower(1);
        Drivetrain.update(gamepad2.left_stick_y, gamepad2.left_stick_x, gamepad2.right_stick_x);
        Lift.update();

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