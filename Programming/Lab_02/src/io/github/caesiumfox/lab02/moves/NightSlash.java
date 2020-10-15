package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class NightSlash extends PhysicalMove {
    public NightSlash() {
        super(Type.DARK, 70, 1.);
    }
    @Override
    protected String describe() {
        return "рубит ночным лезвием";
    }
    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        return (Math.random() < att.getStat(Stat.SPEED) / 256.) ? 2. : 1.;
    }
}