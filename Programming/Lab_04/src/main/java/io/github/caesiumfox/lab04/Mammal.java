package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.environment.Way;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;
import io.github.caesiumfox.lab04.interfaces.*;

public abstract class Mammal extends PhysicalObject
        implements Sniffable, MovingOnGround, Vocal {
    private Gender gender;
    protected Mood mood;
    protected String thought;


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
    public void setMood(Mood mood) {
        this.mood = mood;
    }
    public String getThought() {
        return thought;
    }
    public void setThought(String thought) {
        this.thought = thought;
    }

    public void breathe() {
        System.out.println(this.toString() + " breathes");
    }

    public void go(PhysicalObject destination) {
        System.out.println(this.toString() + " goes to " + destination.toString());
    }
    public void crossStreet(Way way) {
        System.out.println(this.toString() + " crosses " + way.toString());
    }
    public void follow(PhysicalObject followee) {
        System.out.println(this.toString() + " follows " + followee.toString());
    }
}
