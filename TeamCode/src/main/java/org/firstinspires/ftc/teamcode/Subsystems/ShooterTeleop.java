package org.firstinspires.ftc.teamcode.Subsystems;


import android.widget.Button;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "ShooterTeleop")



public class ShooterTeleop extends NextFTCOpMode {

    public ShooterTeleop() {
        addComponents(
                new SubsystemComponent(Shooter.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    public DcMotorEx shootermotor;
    double highVelocity;
    double lowVelocity;
    double curTargetVelocity;
    double F;
    double P;
    double[] stepSizes;
    int stepIndex;

    @Override

    public void onInit() {
        Shooter.INSTANCE.init(hardwareMap);

        shootermotor = Shooter.INSTANCE.shootermotor;
        highVelocity = Shooter.INSTANCE.highvelocity;
        lowVelocity = Shooter.INSTANCE.lowvelocity;
        curTargetVelocity = Shooter.INSTANCE.highvelocity;
        F = Shooter.INSTANCE.F;
        P = Shooter.INSTANCE.P;
        stepSizes = Shooter.INSTANCE.stepsizes;
        stepIndex = Shooter.INSTANCE.stepIndex;

        telemetry.addLine("Init complete");
    }

    @Override
    public void onUpdate() {

        Gamepads.gamepad1().dpadLeft()
                .whenBecomesTrue(() -> F -= stepSizes[stepIndex]);

        Gamepads.gamepad1().dpadRight()
                .whenBecomesTrue(() -> F += stepSizes[stepIndex]);

        Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(() -> P += stepSizes[stepIndex]);

        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(() -> P -= stepSizes[stepIndex]);
        Gamepads.gamepad1().a()
                .whenBecomesTrue(() -> shootermotor.setPower(0));

        final double TICKS_PER_REVOLUTION = 103.6;

        double velocityTPS = shootermotor.getVelocity();

        double velocityRPM = (velocityTPS / TICKS_PER_REVOLUTION) * 60.0;

        // set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shootermotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        //set velocity
        shootermotor.setVelocity(curTargetVelocity);

        double curVelocity = shootermotor.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("RPM", velocityRPM);
        telemetry.addLine("----------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
        telemetry.update();
    }

}


