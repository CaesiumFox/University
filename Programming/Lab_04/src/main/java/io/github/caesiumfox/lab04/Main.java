package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.characters.*;
import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.exceptions.AlreadyAcquaintedException;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class Main {
    private static Human svante;
    private static Human krister;
    private static Human gunilla;
    private static Karlsson karlsson;
    private static Dog poodle;
    private static PhysicalObject house;
    private static Street street;

    public static void main(String[] args) {
        try {
            krister.getAcquaintedWith(karlsson);
        } catch(AlreadyAcquaintedException e) {
            System.err.println(e.getMessage());
        }
        try {
            karlsson.getAcquaintedWith(krister);
        } catch(AlreadyAcquaintedException e) {
            System.err.println(e.getMessage());
        }
        try {
            gunilla.getAcquaintedWith(karlsson);
        } catch(AlreadyAcquaintedException e) {
            System.err.println(e.getMessage());
        }
        try {
            karlsson.getAcquaintedWith(gunilla);
        } catch(AlreadyAcquaintedException e) {
            System.err.println(e.getMessage());
        }
        svante.goTo(house);
        krister.goTo(house);
        gunilla.goTo(house);

        svante.cross(street);
        krister.cross(street);
        gunilla.cross(street);

        poodle.goTo(svante);
        poodle.nose.sniff(svante);
        poodle.say();
    }

    static {
        try {
            svante = new Human("Svante", Gender.Male);
            krister = new Human("Krister", Gender.Male);
            gunilla = new Human("Gunilla", Gender.Female);
            karlsson = new Karlsson();
            poodle = new Dog("Ordinary dog", Gender.Male,
                    Size.Small, Breed.Poodle, FurColor.Black);
            street = new Street("Ordinary street");
            house = new PhysicalObject("Ordinary house") {
                public String toString() {
                    return "House \"" + this.getName() + "\"";
                }
            };
        } catch(EmptyNameException e) {
            System.err.println(e.getMessage());
            System.err.println("Quitting");
            System.exit(1);
        }

        class Mediator {
            public void acquaint(Human ... humans) {
                for(int i = 0; i < humans.length; i++) {
                    for(int j = 0; j < humans.length; j++) {
                        if(i != j) {
                            try {
                                humans[i].getAcquaintedWith(humans[j]);
                            }
                            catch(AlreadyAcquaintedException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        Mediator mediator = new Mediator();
        mediator.acquaint(svante, krister, gunilla);
        mediator.acquaint(svante, karlsson);
    }


    static class Street extends PhysicalObject {
        public Street(String name) throws EmptyNameException {
            super(name);
        }
        @Override
        public boolean equals(Object obj) {
            if(obj == null)
                return false;
            if(obj == this)
                return true;
            if(this.getClass() == obj.getClass()) {
                Street human = (Street)obj;
                return (this.getName().equals(human.getName()));
            }
            return false;
        }
        @Override
        public String toString() {
            return "Street " + this.getName();
        }
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + this.getName().hashCode();
            return result;
        }
    }
}
