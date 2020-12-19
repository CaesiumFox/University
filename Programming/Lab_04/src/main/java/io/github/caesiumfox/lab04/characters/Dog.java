package io.github.caesiumfox.lab04.characters;

import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.Mammal;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;
import io.github.caesiumfox.lab04.interfaces.Sniffable;

public class Dog extends Mammal {
    private Size size;
    private Breed breed;
    private FurColor furColor;
    public Nose nose;

    public class Nose {
        public void sniff(Sniffable object) {
            System.out.println(Dog.this.toString()
                    + " sniffs " + object.toString());
            switch(object.makeScent()) {
                case Good:
                case Neuter:
                    if(object instanceof Human)
                        Dog.this.mood = Mood.Friendly;
                    else
                        Dog.this.mood = Mood.Happy;
                    break;
                case Bad:
                    Dog.this.mood = Mood.Angry;
                    break;
            }
        }
    }

    public Dog(String name, Gender gender, Size size,
            Breed breed, FurColor furColor) throws EmptyNameException {
        super(name, gender);
        this.size = size;
        this.breed = breed;
        this.furColor = furColor;
        this.mood = Mood.Happy;
        nose = new Nose();
    }

    public Size getSize() {
        return size;
    }
    public Breed getBreed() {
        return breed;
    }
    public FurColor getFurColor() {
        return furColor;
    }

    public void say() {
        System.out.print(this.toString());
        switch (mood) {
            case Happy:
                System.out.println(" yaps happily");
                break;
            case Friendly:
                System.out.println(" yaps friendly");
                break;
            case Sad:
                System.out.println(" whines");
                break;
            case Angry:
                System.out.println(" snarls");
                break;
        }
    }
    public Scent makeScent() {
        return Scent.Good;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(this.getClass() == obj.getClass()) {
            Dog dog = (Dog)obj;
            return (this.getName().equals(dog.getName())
                    && this.getGender() == dog.getGender()
                    && this.getMood() == dog.getMood()
                    && this.getSize() == dog.getSize()
                    && this.getBreed() == dog.getBreed()
                    && this.getFurColor() == dog.getFurColor());
        }
        return false;
    }
    @Override
    public String toString() {
        return "Dog named " + this.getName();
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getGender().hashCode();
        result = 31 * result + this.getMood().hashCode();
        result = 31 * result + this.getSize().hashCode();
        result = 31 * result + this.getBreed().hashCode();
        result = 31 * result + this.getFurColor().hashCode();
        return result;
    }
}
