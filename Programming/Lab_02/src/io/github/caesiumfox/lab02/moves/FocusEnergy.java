package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class FocusEnergy extends StatusMove {
    private boolean isAlreadyUsed;
    public FocusEnergy() {
        super(Type.NORMAL, 0, 0);
        isAlreadyUsed = false;
    }
    @Override
    protected String describe() {
        return "фокусируется";
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        if(!isAlreadyUsed) {
            p.setMod(Stat.SPEED, 2);
            isAlreadyUsed = true;
        }
    }
}