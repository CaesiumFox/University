package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class Swift extends SpecialMove {
    public Swift() {
        super(Type.NORMAL, 60, 0);
    }
    @Override
    protected String describe() {
        return "ударяет на соперника";
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return true;
    }
}