package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() {}
    public DcMotorEx Master;
    public DcMotorEx Slave;
    public double highvelocity = 1500;
    public double lowvelocity = 900;

    double curTargetvelocity = highvelocity;
    double P = 0;
    double F = 0;

    double[] stepsizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;

        public void init(HardwareMap hardwareMap) {
            Master = hardwareMap.get(DcMotorEx.class, "Master");
            Slave = hardwareMap.get(DcMotorEx.class, "Slave");

            Master.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Master.setDirection(DcMotorSimple.Direction.REVERSE);
            Slave.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Slave.setDirection(DcMotorSimple.Direction.FORWARD);

            PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
            Master.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
            Slave.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        }
        public Command ppp(){
            return new InstantCommand(()-> Master.setVelocity(1)).and
                    (new InstantCommand(()-> Slave.setVelocity(1)));
    }
        public Command shoot(){
                return new InstantCommand(()-> Master.setVelocityPIDFCoefficients(1,0,0,0))
                        .and(new InstantCommand(()-> Slave.setVelocityPIDFCoefficients(1,0,0,0)));
        }
        public void loop(){
            Master.setPower(Slave.getPower());
        }
}