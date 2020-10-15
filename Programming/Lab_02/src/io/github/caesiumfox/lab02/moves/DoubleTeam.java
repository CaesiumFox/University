package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected String describe() {
        return "увеличивает свою уклоняемость";
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, 1);
    }
}