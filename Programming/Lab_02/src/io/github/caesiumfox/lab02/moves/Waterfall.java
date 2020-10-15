package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class Waterfall extends PhysicalMove {
    public Waterfall() {
        super(Type.WATER, 80, 1.);
    }
    @Override
    protected String describe() {
        return "обливает соперника";
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() < 0.2) Effect.flinch(p);
    }
}