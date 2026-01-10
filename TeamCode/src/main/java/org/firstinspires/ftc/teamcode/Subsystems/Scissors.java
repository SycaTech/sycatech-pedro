package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Scissors implements Subsystem {
    public static final Scissors INSTANCE = new Scissors();
    private Scissors() {}

    ServoEx servo;

    public void init(HardwareMap hwMap) {
        servo = hwMap.get(ServoEx.class, "Shooter");
        servo.setPosition(-360);
    }


    public void open() {
        servo.setPosition(360);
    }

    public void close(){
        servo.setPosition(-360);
    }

}