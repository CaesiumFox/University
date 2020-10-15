package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class AquaJet extends PhysicalMove {
    public AquaJet() {
        super(Type.WATER, 40, 1., 1, 1);
    }
    @Override
    protected String describe() {
        return "cтремительно нападает на соперника";
    }
}