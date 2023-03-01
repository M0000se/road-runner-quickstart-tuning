package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Turntable;

import java.lang.reflect.GenericArrayType;

@TeleOp
public class TheBestTeleopKnownToMankind extends OpMode
{
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    DcMotor lift_top;
    DcMotor lift_bottom;

    DcMotor turntable;

    Servo claw;

    double CLAW_OPEN = General.CLAW_OPEN;
    double CLAW_CLOSED = General.CLAW_CLOSED;
    boolean changed_trigger;
    public void init()
    {
        //General.init();

        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        lift_top = hardwareMap.dcMotor.get("liftMotorTop");
        lift_bottom = hardwareMap.dcMotor.get("liftMotorBottom");

        //  turntable = hardwareMap.dcMotor.get("turntableMotor");

        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //double a = 0;
        claw = hardwareMap.get(Servo.class, "clawMotor");
        //claw.setDirection(Servo.Direction.REVERSE);
        //  telemetry.addData("done", 0 );
        // Declare our motors
        // Make sure your ID's match your configuration
        //claw.setDirection(Servo.Direction.REVERSE);

        changed_trigger = true; //Outside of loop()
        Lift.init(hardwareMap);
        Turntable.init(hardwareMap);
        //lift_top.setPower(1);
    }
    public void loop()
    {


        double drivetrain_speed = gamepad1.right_bumper?0.4:1;


        if(gamepad1.dpad_up) Lift.setSetPoint(Lift.getTargetPosition()+6);
        if(gamepad1.dpad_down && Lift.getTargetPosition()-6>=0) Lift.setSetPoint(Lift.getTargetPosition()-6);

        Turntable.turn(gamepad1.right_stick_x * Math.max(1-gamepad1.left_trigger, 0.2));

        if(gamepad1.a) //intake
        {
            claw.setPosition(CLAW_OPEN);
            Lift.setSetPoint(Lift.INTAKE_POSITION);
        }

        if(gamepad1.y) // up
        {
            claw.setPosition(CLAW_CLOSED); //close claw
            Lift.setSetPoint(Lift.HIGH_POSITION);
        }

        if(gamepad1.x) // med
        {
            claw.setPosition(CLAW_CLOSED); //close claw
            Lift.setSetPoint(Lift.MEDIUM_POSITION);
        }

        if(gamepad1.a) // med
        {
            claw.setPosition(CLAW_CLOSED); //close claw
            Lift.setSetPoint(Lift.MEDIUM_POSITION);
        }


        //if(gamepad1.dpad_right) {targetPosition+=0.10; sleep(100);};
        //if(gamepad1.dpad_left) {targetPosition-=0.10; sleep(100);};

        //telemetry.addData("set:", a);
        // toggle for the trigger
        if((gamepad1.right_trigger >= 0.7) && !changed_trigger)
        {
            if(claw.getPosition() <= CLAW_CLOSED+0.1) claw.setPosition(CLAW_OPEN);
            else claw.setPosition(CLAW_CLOSED);
            changed_trigger = true;
        } else if(!(gamepad1.right_trigger >= 0.7)) changed_trigger = false;


            //lift_top.setPower(1);

        telemetry.addData("lift_current:", Lift.getCurrentPositionTop());
        telemetry.addData("lift_target:", Lift.getTargetPosition());
        //telemetry.addData("lift_top_current:", Lift.getCurrentPositionTop());
        //telemetry.addData("claw_current:", claw.getPosition());
        telemetry.addData("top_motor_power:", lift_top.getPower());
        telemetry.addData("bottom_motor_power:", lift_bottom.getPower());
           // Turntable.update();
        telemetry.update();

        Lift.update();
    }

}