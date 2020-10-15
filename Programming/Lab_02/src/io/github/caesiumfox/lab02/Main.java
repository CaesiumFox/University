package io.github.caesiumfox.lab02;

import ru.ifmo.se.pokemon.*;
import io.github.caesiumfox.lab02.pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();

        Pokemon[] allies = {
            new Basculin("Bob", 30),
            new Zorua("Zen", 20),
            new Zoroark("Zinc", 40),
            new Deino("Doodle", 20),
            new Zweilous("Double", 40),
            new Hydreigon("Hydra", 70)
        };
        Pokemon[] foes = {
            new Basculin("Ben", 30),
            new Zorua("Zoom", 20),
            new Zoroark("Zetta", 40),
            new Deino("Debby", 20),
            new Zweilous("Doom", 40),
            new Hydreigon("Howl", 70)
        };

        for(Pokemon ally : allies)
            battle.addAlly(ally);
        for(Pokemon foe : foes)
            battle.addFoe(foe);
        battle.go();
    }
}
