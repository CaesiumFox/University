package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class DarkPulse extends SpecialMove {
    public DarkPulse() {
        super(Type.DARK, 80, 1.);
    }
    @Override
    protected String describe() {
        return "испускает ужасную ауру";
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() < 0.2) Effect.flinch(p);
    }
}