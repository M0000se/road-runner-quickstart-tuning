package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Extendo {
    public static final double INTAKE_POSITION = 1;
    public static final double AUTO_CONE_INTAKE_POSITION = 0.2;
    public static final double FULL_EXTEND_POSITION = 0;
    public static final double JUNCTION_EXTEND_POSITION = 0.5;

    private static Servo linkage_servo;

    private static double target_position;

    public static void init(HardwareMap hardwareMap) {
        target_position = INTAKE_POSITION;
        linkage_servo = hardwareMap.servo.get("extendoServo");
    }
    public static void changePosition(double increment) {
        if(!(target_position+increment>1 || target_position+increment<0)) {
            linkage_servo.setPosition(target_position + increment);
            target_position = linkage_servo.getPosition();
        }
    }
    public static void setPosition(double position) {
        linkage_servo.setPosition(position);
        target_position = linkage_servo.getPosition();
    }
}
