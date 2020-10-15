package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class DragonBreath extends SpecialMove {
    public DragonBreath() {
        super(Type.DARK, 60, 1.);
    }
    @Override
    protected String describe() {
        return "дышит на соперника";
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setCondition(new Effect().chance(0.3).condition(Status.PARALYZE));
    }
}