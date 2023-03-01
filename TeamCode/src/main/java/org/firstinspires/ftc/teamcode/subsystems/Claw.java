package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    final static public double CLAW_OPEN = 1;
    final static public double CLAW_CLOSED = 0;
    final static public double PIVOT_UP = 1;
    final static public double PIVOT_DOWN = 0;

    private static Servo claw_servo;
    private static Servo claw_pivot;

    

    public static void close()
    {
        claw_servo.setPosition(CLAW_CLOSED);
    }
    public static void open()
    {
        claw_servo.setPosition(CLAW_OPEN);
    }
    public static void setPivotUp()
    {
        claw_pivot.setPosition(PIVOT_UP);
    }
    public static void setPivotDown()
    {
        claw_pivot.setPosition(PIVOT_DOWN);
    }
}
