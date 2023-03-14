package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AsyncRRAutoRed.AutoState.*;
import static org.firstinspires.ftc.teamcode.AutoGeneral.*;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Turntable;

/**
 * This opmode explains how you follow multiple trajectories in succession, asynchronously. This
 * allows you to run your own logic beside the drive.update() command. This enables one to run
 * their own loops in the background such as a PID controller for a lift. We can also continuously
 * write our pose to PoseStorage.
 * <p>
 * The use of a State enum and a currentState field constitutes a "finite state machine."
 * You should understand the basics of what a state machine is prior to reading this opmode. A good
 * explanation can be found here:
 * https://www.youtube.com/watch?v=Pu7PMN5NGkQ (A finite state machine introduction tailored to FTC)
 * or here:
 * https://gm0.org/en/stable/docs/software/finite-state-machines.html (gm0's article on FSM's)
 * <p>
 * You can expand upon the FSM concept and take advantage of command based programming, subsystems,
 * state charts (for cyclical and strongly enforced states), etc. There is still a lot to do
 * to supercharge your code. This can be much cleaner by abstracting many of these things. This
 * opmode only serves as an initial starting point.
 */
@Autonomous(group = "advanced")
public class AsyncRRAutoRed extends LinearOpMode {

    // This enum defines our "state"
    // This is essentially just defines the possible steps our program will take
    enum AutoState {
        INTAKE,
        LIFT_UP,
        TURNTABLE,
        DROP,
        CLAW_UP_TURN_BACK
        // Our bot will enter the IDLE state when done
    }

    /*// We define the current state we're on
    // Default to IDLE
    State currentState = State.IDLE;

    // Define our start pose
    // This assumes we start at x: 15, y: 10, heading: 180 degrees
    Pose2d startPose = new Pose2d(36, -55, Math.toRadians(90));*/

    AutoState state = AutoState.INTAKE;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize SampleMecanumDrive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.initAll(hardwareMap);
        Turntable.setSpeed(0.4);

        // Set inital pose
        drive.setPoseEstimate(startPose);

        // define our trajectories
        Trajectory upto =
                drive.trajectoryBuilder(startPose)
                        .lineToLinearHeading(uptoPose)
                        .build();
        drive.followTrajectory(upto);

        int cycles = 0;
        int stack_position = Lift.STACK_START_POSITION;

        //TODO: vision

        waitForStart();
        while(cycles < CONE_CYCLES && opModeIsActive() && !isStopRequested())
        {
            switch (state)
            {
                case INTAKE: {
                    Extendo.setPosition(Extendo.FULL_EXTEND_POSITION); // extend
                    if(time.time()>INTAKE_EXTEND_TIME) { // wait until we fully extended
                        Claw.setPosition(Claw.CLAW_CLOSED); // close claw
                        state = AutoState.LIFT_UP; // switch state
                        time.reset(); //reset timer
                    }
                } break;
                case LIFT_UP: {
                    if (time.time() > Claw.CLAW_CLOSE_TIME) { // wait for the claw to close
                        Claw.setPivotUp(); // lift up the claw
                        Lift.setTargetPosition(Lift.HIGH_POSITION); // move the lift to the highest position
                        state = AutoState.TURNTABLE; // switch state
                        time.reset();
                    }
                } break;
                case TURNTABLE: {
                    if (time.time() > CLAW_CLEARS_DRIVETRAIN) { // lift is still going to position but the moment claw pivot and lift raise the claw beyond
                        Turntable.setTargetPosition(Turntable.AUTO_RED);
                        state = AutoState.DROP;
                        time.reset();
                    }
                } break;
                case DROP: {
                    if(Turntable.atTargetPosition() && Lift.atTargetPosition()) {
                        Claw.setPivotDown();
                        Claw.setPosition(Claw.CLAW_OPEN);
                        state = CLAW_UP_TURN_BACK;
                        time.reset();
                    }
                } break;
                case CLAW_UP_TURN_BACK: {
                    if(time.time()>DROP_TIME) {
                        Claw.setPivotUp();
                        Turntable.setTargetPosition(Turntable.INTAKE_POSITION);
                        Extendo.setPosition(Extendo.AUTO_CONE_INTAKE_POSITION);

                        stack_position -= Lift.STACK_POSITION_DECREMENT;
                        Lift.setTargetPosition(Lift.INTAKE_POSITION);

                        cycles++;
                        state = INTAKE;
                        time.reset();
                    }
                } break;
            }
        }
    }
}