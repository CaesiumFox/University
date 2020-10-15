package io.github.caesiumfox.lab02.pokemons;

import io.github.caesiumfox.lab02.moves.BodySlam;

public class Hydreigon extends Zweilous {
    public Hydreigon(String name, int level) {
        super(name, level);
        this.setStats(92, 105, 90, 125, 90, 98);
        this.addMove(new BodySlam());
    }
};