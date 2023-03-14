package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    final static public double CLAW_OPEN = 0;
    final static public double CLAW_CLOSED = 1;
    final static public double CLAW_FIT = 0.7;

    final static public double CLAW_CLOSE_TIME = 500;

    final static public double PIVOT_UP = 0.6;
    final static public double PIVOT_DOWN = 0.9;

    private static Servo claw_servo;
    private static Servo claw_pivot;

    public static void init(HardwareMap hardwareMap) {
        claw_servo = hardwareMap.servo.get("clawServo");
        claw_pivot = hardwareMap.servo.get("clawPivot");
        claw_pivot.setPosition(PIVOT_DOWN);
        claw_servo.setPosition(CLAW_OPEN);

    }
    public static void setPosition(double position) {
        claw_servo.setPosition(position);
    }
    public static void setPivotUp() {
        claw_pivot.setPosition(PIVOT_UP);
    }
    public static void setPivotDown() {
        claw_pivot.setPosition(PIVOT_DOWN);
    }
    public static void switch_state() {
        claw_pivot.setPosition(PIVOT_DOWN);
    }
}
