package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class BodySlam extends PhysicalMove {
    public BodySlam() {
        super(Type.NORMAL, 85, 1.);
    }
    @Override
    protected String describe() {
        return "наваливается на соперника";
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() < 0.3) Effect.flinch(p);
    }
}