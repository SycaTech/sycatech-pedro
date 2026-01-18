
        package org.firstinspires.ftc.teamcode.Subsystems;


import static dev.nextftc.bindings.Bindings.button;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

import dev.nextftc.bindings.Button;
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

   public DcMotorEx Master;
    public DcMotorEx Slave;
    double lowVelocity;
    double highVelocity;
    double curTargetVelocity;
    double F;
    double P;
    double[] stepSizes;
    int stepIndex;

    @Override

    public void onInit() {
        Shooter.INSTANCE.init(hardwareMap);

        Master = Shooter.INSTANCE.Master;
        highVelocity = Shooter.INSTANCE.highvelocity;
        lowVelocity = Shooter.INSTANCE.lowvelocity;
        curTargetVelocity = Shooter.INSTANCE.curTargetvelocity;
        F = Shooter.INSTANCE.F;
        P = Shooter.INSTANCE.P;
        stepSizes = Shooter.INSTANCE.stepsizes;
        stepIndex = Shooter.INSTANCE.stepIndex;
        Slave = Shooter.INSTANCE.Slave;
        highVelocity = Shooter.INSTANCE.highvelocity;
        lowVelocity = Shooter.INSTANCE.lowvelocity;
        curTargetVelocity = Shooter.INSTANCE.curTargetvelocity;
        F = Shooter.INSTANCE.F;
        P = Shooter.INSTANCE.P;
        stepSizes = Shooter.INSTANCE.stepsizes;
        stepIndex = Shooter.INSTANCE.stepIndex;

        telemetry.addLine("Init complete");
    }

    @Override
    public void onUpdate() {
        Button a = new Button(()-> gamepad1.a);
        a.whenTrue(Shooter.INSTANCE.ppp());

//        if (gamepad1.a) {
//            if (curTargetVelocity == highVelocity) {
//                curTargetVelocity = lowVelocity;
//            } else {curTargetVelocity = highVelocity;}
//        }
//
//        if (gamepad1.a) {
//            stepIndex = (stepIndex + 1) % stepSizes.length;
//        }
//
//
//
//        if (gamepad1.a) {
//            P += stepSizes[stepIndex];
//        }
//
//        if (gamepad1.a) {
//            P += stepSizes[stepIndex];
//        }
//
//
//

        final double TICKS_PER_REVOLUTION = 1200;

        double velocityTPS = Master.getVelocity();

        double velocityRPM = (velocityTPS / TICKS_PER_REVOLUTION) * 60.0;

        // set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        Master.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        Slave.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        //set velocity
        Master.setVelocity(curTargetVelocity);
        Slave.setVelocity(curTargetVelocity);

        double curVelocity = Master.getVelocity ();
        double curVelocty = Slave.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("RPM", velocityRPM);
        telemetry.addLine("----------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
        telemetry.update(); } }
