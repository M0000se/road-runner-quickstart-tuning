package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

public class General
{
    final static public double CLAW_OPEN = 1;
    final static public double CLAW_CLOSED = 0;

    // Creates a PIDFController with gains kP, kI, kD, and kF
    Servo claw;

    static void init()
    {

    }
}
