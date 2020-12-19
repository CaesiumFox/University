package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;
import io.github.caesiumfox.lab04.interfaces.*;

public abstract class Mammal extends PhysicalObject
        implements Sniffable, MovingOnGround, Vocal {
    private Gender gender;
    protected Mood mood;

    public Mammal(String name, Gender gender) throws EmptyNameException {
        super(name);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
    public Mood getMood() {
        return mood;
    }

    public void goTo(PhysicalObject destination) {
        System.out.println(this.toString() + " goes to " + destination.toString());
    }
    public void cross(PhysicalObject longObject) {
        System.out.println(this.toString() + " crosses " + longObject.toString());
    }
}
