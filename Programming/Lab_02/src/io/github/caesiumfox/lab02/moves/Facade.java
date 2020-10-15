package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70, 1.);
    }
    @Override
    protected String describe() {
        return "нападает на соперника";
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        if(def.getCondition() == Status.BURN ||
                def.getCondition() == Status.PARALYZE ||
                def.getCondition() == Status.POISON) {
            def.setMod(Stat.HP, (int) Math.round(damage) * 2);
        } else {
            def.setMod(Stat.HP, (int) Math.round(damage));
        }
    }
}