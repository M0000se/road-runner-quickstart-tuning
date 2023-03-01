package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turntable {


    private enum Turntable_mode {
            AUTOALIGN,
            MANUAL
    }

    private static final double INTAKE_POSITION = 0;

    static DcMotor turntable_motor;

    static Turntable_mode turntable_mode = Turntable_mode.MANUAL;

    public static void init(HardwareMap hw){
        turntable_motor = hw.dcMotor.get("turntableMotor");
        turntable_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /*static void goUp()
    {
        lift_bottom.setPower();
    }*/
    /*public static int getCurrentPositionBottom() {
        return lift_bottom.getCurrentPosition();
    }
    public static int getCurrentPositionTop() {
        return lift_top.getCurrentPosition();
    }
    public static double getSetPoint() {
        return lift_pidf.getSetPoint();
    }
    public static boolean atSetPoint() {
        return lift_pidf.atSetPoint();
    }
    public static void setSetPoint(double set_point) {
        lift_pidf.setSetPoint(set_point);
    }
    public static void update() {
        /*if(turntable_mode == Turntable_mode.AUTOALIGN)
        {
           double output = turntable_pidf.calculate(turntable_motor.getCurrentPosition());
                lift_bottom.setPower(output);
                lift_top.setPower(output);
        }
        /*if(turntable_mode == Turntable_mode.MANUAL) {
            double output = turntable_pidf.calculate(turntable_motor.getCurrentPosition());
            turntable_motor.setPower(output);
        }
    }*/
    public static void turn(double speed) {
        turntable_motor.setPower(speed);
    }
}
