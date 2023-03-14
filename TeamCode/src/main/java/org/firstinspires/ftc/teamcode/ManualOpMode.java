package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State.GOING_INTAKE;
import static org.firstinspires.ftc.teamcode.TheBestTeleopKnownToMankind.State.INTAKE;

import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Turntable;

@TeleOp
public class ManualOpMode extends OpMode {
    static final private int CLAW_UP_TIME = 1000;
    boolean changed_trigger = true;
    int direction=0;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public enum State {
        INTAKE,
        GOING_OUT,
        GOING_INTAKE
    }

    DcMotor turntable_motor;
    TheBestTeleopKnownToMankind.State state = TheBestTeleopKnownToMankind.State.INTAKE;
    public void init()
    {
        turntable_motor = hardwareMap.dcMotor.get("turntableMotor");
        Extendo.init(hardwareMap);
        Drivetrain.init(hardwareMap);
        Claw.init(hardwareMap);
        PhotonCore.enable();
        Lift.init(hardwareMap);
        //Robot.initAll(hardwareMap);
        //Turntable.setSpeed(0.1);
    }
    public void loop()
    {
        if(gamepad2.right_trigger>=0.5) Drivetrain.setSpeed(0.4);
        else Drivetrain.setSpeed(1);

        //if(gamepad1.dpad_right) Lift.setSetPoint(Lift.getTargetPosition()+6);
        //if(gamepad1.dpad_down && Lift.getTargetPosition()-6>=0) Lift.setSetPoint(Lift.getTargetPosition()-6);

        if(gamepad1.left_trigger >= 0.5) {
            Extendo.setPosition(Extendo.FULL_EXTEND_POSITION);
        }
        else Extendo.setPosition(Extendo.INTAKE_POSITION);

        if(gamepad1.right_trigger >= 0.5) {
            Claw.setPosition(Claw.CLAW_OPEN);
        }
        else Claw.setPosition(Claw.CLAW_CLOSED);

        if(gamepad1.right_bumper) {
            Claw.setPivotUp();
        }
        else Claw.setPivotDown();
        //if(gamepad1.a) Lift.setTargetPosition(Lift.INTAKE_POSITION);
        if(gamepad1.y) Lift.setTargetPosition(Lift.HIGH_POSITION);
        if(gamepad1.x) Lift.setTargetPosition(Lift.MEDIUM_POSITION);
        if(gamepad1.a) Lift.setTargetPosition(Lift.LOW_POSITION);
        if(gamepad1.b) Lift.setTargetPosition(Lift.INTAKE_POSITION);
        Lift.update();

        if(gamepad1.dpad_up) Lift.setTargetPosition(Lift.getTargetPosition()+5);
        if(gamepad1.dpad_down && Lift.getTargetPosition()-5>=0) Lift.setTargetPosition(Lift.getTargetPosition()-5);

        if(gamepad1.left_stick_x>0.1 || gamepad1.left_stick_x<(-0.1))
            turntable_motor.setPower(gamepad1.left_stick_x*0.3);
        else turntable_motor.setPower(0);
        //if(gamepad1.dpad_right) {tu}
        //if(gamepad1.dpad_left) {targetPosition-=0.10;}

        //telemetry.addData("set:", a);
        // toggle for the trigger

        //lift_top.setPower(1);
        Drivetrain.update(gamepad2.left_stick_y, gamepad2.left_stick_x, gamepad2.right_stick_x);

        telemetry.addData("lift_current:", Lift.getCurrentPositionTop());
        telemetry.addData("lift_target:", Lift.getTargetPosition());
        //telemetry.addData("tunrtable current:", Turntable.getCurrentPosition());
        //telemetry.addData("tunrtable target:", Turntable.getTargetPosition());
        //telemetry.addData("lift_top_current:", Lift.getCurrentPositionTop());
        //telemetry.addData("claw_current:", claw.getPosition());
        //telemetry.addData("top_motor_power:", lift_top.getPower());
        //telemetry.addData("bottom_motor_power:", lift_bottom.getPower());
        // Turntable.update();
        telemetry.update();
    }

}
