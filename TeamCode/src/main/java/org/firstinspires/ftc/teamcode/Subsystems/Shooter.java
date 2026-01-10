package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

import dev.nextftc.core.subsystems.Subsystem;


public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() {}
    public DcMotorEx shootermotor;
    public double highvelocity = 1500;
    public double lowvelocity = 900;

    double curTargetvelocity = highvelocity;
    double P = 0;
    double F = 0;

    double[] stepsizes = {10.0, 1.0, 0.1, 0.01, 0.0001};
    int stepIndex = 1;

        public void init(HardwareMap hardwareMap) {
            shootermotor = hardwareMap.get(DcMotorEx.class, "shoot");


        shootermotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shootermotor.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shootermotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);

        }
}