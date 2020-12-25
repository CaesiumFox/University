package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.characters.*;
import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.environment.*;
import io.github.caesiumfox.lab04.exceptions.*;

public class Main {
    private static Human mother;
    private static Human svante;
    private static Human krister;
    private static Human gunilla;
    private static Karlsson karlsson;
    private static Dog poodle;
    private static Building house;
    private static Building school;
    private static Street[] streets;

    public static void main(String[] args) {
        class Narrator {
            private int act;
            public Narrator() {
                act = 1;
            }
            public void announce() {
                System.out.println("\n================\nAct " +
                        String.valueOf(act++) + "\n================\n\n");
            }
            public void printMood(Mammal mammal) {
                System.out.println(mammal.toString() + " is " + mammal.getMood().toString());
            }
            public void finish() {
                System.out.println("\n================\nThe End\n================\n\n");
                act = 1;
            }
        }
        Narrator narrator = new Narrator();

        narrator.announce();
        mother.breathe();
        try {
            svante.say();
        } catch(NoThoughtException e) {
            System.err.println(e.getMessage());
        }
        mother.setMood(Mood.Angry);
        narrator.printMood(mother);

        narrator.announce();
        svante.setMood(Mood.Happy);
        narrator.printMood(svante);
        svante.go(school);
        Mediator.acquaint(krister, karlsson);
        Mediator.acquaint(gunilla, karlsson);

        narrator.announce();
        svante.go(house);
        krister.go(house);
        gunilla.go(house);

        svante.crossStreet(streets[0]);
        krister.crossStreet(streets[0]);
        gunilla.crossStreet(streets[0]);

        poodle.go(svante);
        poodle.nose.sniff(svante);
        poodle.say();
        poodle.follow(svante);

        for(int i = 1; i < 5; i++) {
            svante.crossStreet(streets[i]);
            krister.crossStreet(streets[i]);
            gunilla.crossStreet(streets[i]);
            poodle.crossStreet(streets[i]);
        }

        narrator.finish();
    }

    static {
        try {
            mother = new Human("Mrs Svantesson", Gender.Female);
            svante = new Human("Svante", Gender.Male);
            krister = new Human("Krister", Gender.Male);
            gunilla = new Human("Gunilla", Gender.Female);
            karlsson = new Karlsson();
            poodle = new Dog("Poodle", Gender.Male,
                    Size.Small, Breed.Poodle, FurColor.Black);
            house = new Building("An ordinary house") {
                public String toString() {
                    return "House " + this.getName();
                }
            };
            school = new Building("An ordinary school") {
                public String toString() {
                    return "School " + this.getName();
                }
            };
            streets = new Street[5];
            streets[0] = new Street("Ordinary street");
            for(int i = 1; i < 5; i++) {
                streets[i] = new RoadIntersection("Ordinary intersection #" + String.valueOf(i));
            }
        } catch (EmptyNameException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        Mediator.acquaint(svante, krister, gunilla);
        Mediator.acquaint(svante, karlsson);
        svante.setThought("a dog");
        svante.setMood(Mood.Thoughtful);
    }

    private static class Mediator {
        public static void acquaint(Human... humans) {
            for(int i = 0; i < humans.length; i++) {
                for(int j = 0; j < humans.length; j++) {
                    if(i != j) {
                        try {
                            humans[i].getAcquaintedWith(humans[j]);
                        } catch(AcquaintanceException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
