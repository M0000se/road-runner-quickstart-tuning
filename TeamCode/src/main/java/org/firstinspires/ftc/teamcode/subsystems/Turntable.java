package org.firstinspires.ftc.teamcode.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.subsystems.Lift.target_position;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turntable {
    public static final int INTAKE_POSITION = 0;
    public static final int LEFT_POSITION = -100;
    public static final int RIGHT_POSITION = 100;
    public static final int FRONT_POSITION = 150;

    private enum Turntable_mode {
            POSITIONAL,
            MANUAL
    }

    private static int target_position;
    private static double speed = 1;

    static DcMotor turntable_motor;

    static Turntable_mode turntable_mode = Turntable_mode.POSITIONAL;

    public static void init(HardwareMap hw){
        turntable_motor = hw.dcMotor.get("turntableMotor");
        turntable_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turntable_motor.setMode(RUN_TO_POSITION);
        turntable_motor.setPower(speed);
        target_position=INTAKE_POSITION;
    }
    public static int getCurrentPosition() {
        return turntable_motor.getCurrentPosition();
    }
    public static int getTargetPosition() {
        return target_position;
    }
    public static void setTargetPosition(int set_point) {
        speed=1;
        target_position = set_point;
    }
    public static void setSpeed(double new_speed) {
        speed=new_speed;
    }
    public static void update() {
        if(turntable_mode == Turntable_mode.POSITIONAL)
        {
            turntable_motor.setPower(speed);
            turntable_motor.setTargetPosition(target_position);
        }
    }
    public static void manual_turn(double encoder_ticks) {
       target_position+=encoder_ticks;
    }
}
