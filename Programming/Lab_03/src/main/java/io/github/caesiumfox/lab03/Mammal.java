package io.github.caesiumfox.lab03;

import io.github.caesiumfox.lab03.enums.*;
import io.github.caesiumfox.lab03.environment.Street;
import io.github.caesiumfox.lab03.interfaces.*;

public abstract class Mammal extends PhysicalObject
        implements Sniffable, MovingOnGround, Vocal {
    private Gender gender;
    protected Mood mood;

    public Mammal(String name, Gender gender) {
        super(name);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
    public Mood getMood() {
        return mood;
    }

    public void go(PhysicalObject destination) {
        System.out.println(this.toString() + " goes to " + destination.toString());
    }
    public void crossStreet(Street street) {
        System.out.println(this.toString() + " crosses " + street.toString());
    }
}
