package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class Snarl extends SpecialMove {
    public Snarl() {
        super(Type.DARK, 55, .95);
    }
    @Override
    protected String describe() {
        return "облаивает соперника";
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
}