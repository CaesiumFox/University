package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.characters.*;
import io.github.caesiumfox.lab04.enums.*;
import io.github.caesiumfox.lab04.environment.*;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class Main {
    private static Human mother;
    private static Human svante;
    private static Human krister;
    private static Human gunilla;
    private static Karlsson karlsson;
    private static Dog poodle;
    private static Building house;
    private static Building school;
    private static Street street;

    public static void main(String[] args) {


        krister.getAcquaintedWith(karlsson);
        karlsson.getAcquaintedWith(krister);
        gunilla.getAcquaintedWith(karlsson);
        karlsson.getAcquaintedWith(gunilla);

        svante.go(house);
        krister.go(house);
        gunilla.go(house);

        svante.crossStreet(street);
        krister.crossStreet(street);
        gunilla.crossStreet(street);

        poodle.go(svante);
        poodle.sniff(svante);
        poodle.say();
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
            house = new Building("House");
            school = new Building("School");
            street = new Street("Ordinary street");
        } catch (EmptyNameException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        svante.getAcquaintedWith(krister);
        svante.getAcquaintedWith(gunilla);
        krister.getAcquaintedWith(svante);
        krister.getAcquaintedWith(gunilla);
        gunilla.getAcquaintedWith(svante);
        gunilla.getAcquaintedWith(krister);

        svante.getAcquaintedWith(karlsson);
        karlsson.getAcquaintedWith(svante);
    }
}
