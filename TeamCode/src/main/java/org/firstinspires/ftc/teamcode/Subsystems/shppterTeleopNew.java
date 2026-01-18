package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "ShooterTeleopNew")

public class shppterTeleopNew extends NextFTCOpMode {

    public shppterTeleopNew() {
        addComponents(
                new SubsystemComponent(Shooter.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    public void onInit() {
        Shooter.INSTANCE.init(hardwareMap);
    }

    public void onStartButtonPressed(){
        CommandManager.INSTANCE.scheduleCommand(Shooter.INSTANCE.ppp());
    }

}
