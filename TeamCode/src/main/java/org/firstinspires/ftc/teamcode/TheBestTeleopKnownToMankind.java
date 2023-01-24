package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.GenericArrayType;

@TeleOp
public class TheBestTeleopKnownToMankind extends OpMode
{
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    DcMotor lift;

    Servo claw;

    private int targetPosition = 0;
    private int HIGH_POSITION = General.HIGH_POSITION;
    private int MEDIUM_POSITION = General.MEDIUM_POSITION;
    private int INTAKE_POSITION = General.INTAKE_POSITION;
    int manual_position = targetPosition;

    double CLAW_OPEN = General.CLAW_OPEN;
    double CLAW_CLOSED = General.CLAW_CLOSED;
    boolean changed_trigger;
    public void init()
    {
        // Declare our motors
        // Make sure your ID's match your configuration

        //RobotHardwareMap hM = new RobotHardwareMap();

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests

        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        lift = hardwareMap.dcMotor.get("liftMotor");

        lift.setTargetPosition(INTAKE_POSITION);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //double a = 0;
        claw = hardwareMap.get(Servo.class, "clawMotor");
        //claw.setDirection(Servo.Direction.REVERSE);

        targetPosition = INTAKE_POSITION;

        // Declare our motors
        // Make sure your ID's match your configuration
        //claw.setDirection(Servo.Direction.REVERSE);

        changed_trigger = false; //Outside of loop()
    }
    public void loop()
    {

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double max_speed = gamepad1.right_bumper?0.4:0.9;
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            double frontLeftPower = (y + x + rx) / denominator * max_speed;
            double backLeftPower = (y - x + rx) / denominator * max_speed;
            double frontRightPower = (y - x - rx) / denominator * max_speed;
            double backRightPower = (y + x - rx) / denominator * max_speed;

            if(gamepad1.dpad_up) targetPosition+=2;
            if(gamepad1.dpad_down && targetPosition-2>=0) targetPosition-=2;

            if(gamepad1.a) //bottom
            {
                claw.setPosition(CLAW_OPEN);
                targetPosition = INTAKE_POSITION;
            }

            if(gamepad1.y) // up
            {
                claw.setPosition(CLAW_CLOSED); //close claw
                targetPosition = HIGH_POSITION;
            }

            if(gamepad1.x) // med
            {
                claw.setPosition(CLAW_CLOSED); //close claw
                targetPosition = MEDIUM_POSITION;
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

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            lift.setTargetPosition(targetPosition);

            telemetry.addData("lift_target:", targetPosition);
            telemetry.addData("lift_current:", lift.getCurrentPosition());
            telemetry.addData("claw_current:", claw.getPosition());
            telemetry.update();

            //motorBackLeft.setMode(DcMotor.RunMode.);
            //motorBackLeft.get

            //hM.leftFront.setPower(frontLeftPower);
            //hM.leftRear.setPower(backLeftPower);
            //hM.rightFront.setPower(frontRightPower);
            //hM.rightRear.setPower(backRightPower);

    }

}