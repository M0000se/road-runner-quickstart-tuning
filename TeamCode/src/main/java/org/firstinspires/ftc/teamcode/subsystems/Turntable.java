package org.firstinspires.ftc.teamcode.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.subsystems.Lift.target_position;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turntable {
    public static final int QUARTER_ROTATION = 650;
    public static final int INTAKE_POSITION = 0;
    public static final int RIGHT_POSITION = QUARTER_ROTATION;
    public static final int FRONT_POSITION = QUARTER_ROTATION * 2;
    public static final int LEFT_POSITION = QUARTER_ROTATION * 3;

    public static final int AUTO_BLUE = QUARTER_ROTATION+QUARTER_ROTATION/2;
    public static final int AUTO_RED = -QUARTER_ROTATION-630/2;

    public static final int POSITION_TOLERANCE = 20;

    private static int target_position;
    static DcMotor turntable_motor;

    public static void init(HardwareMap hw) {
        turntable_motor = hw.dcMotor.get("turntableMotor");
        turntable_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turntable_motor.setMode(RUN_USING_ENCODER);
        turntable_motor.setMode(STOP_AND_RESET_ENCODER);
        turntable_motor.setTargetPosition(INTAKE_POSITION);
        turntable_motor.setMode(RUN_TO_POSITION);
        turntable_motor.setPower(1);
    }
    public static int getCurrentPosition() {
        return turntable_motor.getCurrentPosition();
    }
    public static int getTargetPosition() {
        return turntable_motor.getTargetPosition();
    }
    public static void setTargetPosition(int set_point) {
        target_position = set_point;
        turntable_motor.setTargetPosition(target_position);
    }
    public static boolean atTargetPosition() {
        return Math.abs(turntable_motor.getCurrentPosition() - turntable_motor.getTargetPosition()) < 50;
    }
    public static void setSpeed(double new_speed) {
        turntable_motor.setPower(new_speed);
    }
    public static void manual_turn(double encoder_ticks) {
       target_position+=encoder_ticks;
       turntable_motor.setTargetPosition(target_position);
    }
}
