package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public static void initAll(HardwareMap hardwareMap) {
        Lift.init(hardwareMap);
        Turntable.init(hardwareMap);
        Extendo.init(hardwareMap);
        Drivetrain.init(hardwareMap);
        Claw.init(hardwareMap);
        PhotonCore.enable();
    }
}
