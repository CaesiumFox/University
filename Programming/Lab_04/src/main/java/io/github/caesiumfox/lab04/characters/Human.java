package io.github.caesiumfox.lab04.characters;

import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.Mammal;
import io.github.caesiumfox.lab04.exceptions.AlreadyAcquaintedException;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

import java.util.ArrayList;

public class Human extends Mammal {
    protected ArrayList<Human> acquaintances;

    public Human(String name, Gender gender) throws EmptyNameException {
        super(name, gender);
        this.mood = Mood.Happy;
        acquaintances = new ArrayList<>();
    }

    public void getAcquaintedWith(Human human)
            throws AlreadyAcquaintedException {
        if(acquaintances.contains(human)) {
            throw new AlreadyAcquaintedException(this, human);
        }
        acquaintances.add(human);
        System.out.println(this.toString() + " is now acquainted with " + human.toString());
    }
    public void say() {
        System.out.print(this.toString() + " says, \"");
        switch(mood) {
            case Happy:
                System.out.print("I'm happy!");
                break;
            case Friendly:
                System.out.print("Hello, friend!");
                break;
            case Sad:
                System.out.print("I'm sad!");
                break;
            case Angry:
                System.out.print("I'm angry!");
                break;
        }
        System.out.println("\"");
    }
    public Scent makeScent() {
        switch(mood) {
            case Happy:
            case Friendly:
                return Scent.Good;
            case Sad:
                return Scent.Neuter;
            case Angry:
                return Scent.Bad;
        }
        return Scent.Neuter;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(this.getClass() == obj.getClass()) {
            Human human = (Human)obj;
            return (this.getName().equals(human.getName())
                    && this.getGender() == human.getGender()
                    && this.getMood() == human.getMood());
        }
        return false;
    }
    @Override
    public String toString() {
        return "Human " + this.getName();
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getGender().hashCode();
        result = 31 * result + this.getMood().hashCode();
        return result;
    }
}
