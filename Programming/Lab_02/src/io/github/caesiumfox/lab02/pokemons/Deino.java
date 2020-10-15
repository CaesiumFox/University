package io.github.caesiumfox.lab02.pokemons;

import ru.ifmo.se.pokemon.*;
import io.github.caesiumfox.lab02.moves.FocusEnergy;
import io.github.caesiumfox.lab02.moves.DragonBreath;

public class Deino extends Pokemon {
    public Deino(String name, int level) {
        super(name, level);
        this.addType(Type.DARK);
        this.addType(Type.DRAGON);
        this.setStats(52, 65, 50, 45, 50, 38);
        this.addMove(new FocusEnergy());
        this.addMove(new DragonBreath());
    }
};