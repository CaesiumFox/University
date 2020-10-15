package io.github.caesiumfox.lab02.pokemons;

import ru.ifmo.se.pokemon.*;
import io.github.caesiumfox.lab02.moves.AquaJet;
import io.github.caesiumfox.lab02.moves.Swift;
import io.github.caesiumfox.lab02.moves.Waterfall;
import io.github.caesiumfox.lab02.moves.DoubleTeam;

public class Basculin extends Pokemon {
    public Basculin(String name, int level) {
        super(name, level);
        this.addType(Type.WATER);
        this.setStats(70, 92, 65, 80, 55, 98);
        this.addMove(new AquaJet());
        this.addMove(new Swift());
        this.addMove(new Waterfall());
        this.addMove(new DoubleTeam());
    }
};