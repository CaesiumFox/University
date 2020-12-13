package io.github.caesiumfox.lab03;

import io.github.caesiumfox.lab03.characters.*;
import io.github.caesiumfox.lab03.enums.*;
import io.github.caesiumfox.lab03.environment.*;

public class Main {
    private static Human svante;
    private static Human krister;
    private static Human gunilla;
    private static Karlsson karlsson;
    private static Dog poodle;
    private static House house;
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
        svante = new Human("Svante", Gender.Male);
        krister = new Human("Krister", Gender.Male);
        gunilla = new Human("Gunilla", Gender.Female);
        karlsson = new Karlsson();
        poodle = new Dog("Ordinary dog", Gender.Male,
            Size.Small, Breed.Poodle, FurColor.Black);
        house = new House("Ordinary house");
        street = new Street("Ordinary street");

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
