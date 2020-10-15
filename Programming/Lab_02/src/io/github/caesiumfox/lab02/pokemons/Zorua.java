package io.github.caesiumfox.lab02.pokemons;

import ru.ifmo.se.pokemon.*;
import io.github.caesiumfox.lab02.moves.Facade;
import io.github.caesiumfox.lab02.moves.Snarl;
import io.github.caesiumfox.lab02.moves.DarkPulse;

public class Zorua extends Pokemon {
    public Zorua(String name, int level) {
        super(name, level);
        this.addType(Type.DARK);
        this.setStats(40, 65, 40, 80, 40, 65);
        this.addMove(new Facade());
        this.addMove(new Snarl());
        this.addMove(new DarkPulse());
    }
};