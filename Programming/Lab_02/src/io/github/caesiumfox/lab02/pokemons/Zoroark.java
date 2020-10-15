package io.github.caesiumfox.lab02.pokemons;

import io.github.caesiumfox.lab02.moves.NightSlash;

public class Zoroark extends Zorua {
    public Zoroark(String name, int level) {
        super(name, level);
        this.setStats(60, 105, 60, 120, 60, 105);
        this.addMove(new NightSlash());
    }
};