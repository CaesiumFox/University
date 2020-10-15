package io.github.caesiumfox.lab02.pokemons;

import io.github.caesiumfox.lab02.moves.DoubleHit;

public class Zweilous extends Deino {
    public Zweilous(String name, int level) {
        super(name, level);
        this.setStats(72, 85, 70, 65, 70, 58);
        this.addMove(new DoubleHit());
    }
};