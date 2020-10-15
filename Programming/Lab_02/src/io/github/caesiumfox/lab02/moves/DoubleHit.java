package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class DoubleHit extends PhysicalMove {
    public DoubleHit() {
        super(Type.NORMAL, 35, .9);
    }
    @Override
    protected String describe() {
        return "хлещет соперника";
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
}